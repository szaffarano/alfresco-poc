package afip.tecno.alfresco;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;

public class CMISPublisher {
	private CMISClient client;

	public CMISPublisher() {
		client = new CMISClient("admin", "admin");
	}

	public Document publish(String targetPath, String name, byte[] content, Map<String, Object> properties,
			List<String> aspects) {
		Session session = client.getSession();
		
		if (!properties.containsKey(PropertyIds.OBJECT_TYPE_ID)) {
			throw new RuntimeException("No se puede crear un document sin tipo");
		}
		
		ObjectType type = session.getTypeDefinition(properties.get(PropertyIds.OBJECT_TYPE_ID).toString());
		PropertyDefinition<?> objectIdPropDef = type.getPropertyDefinitions().get(PropertyIds.OBJECT_ID);
		String objectIdQueryName = objectIdPropDef.getQueryName();

		String queryString = "SELECT " + objectIdQueryName + " FROM " + type.getQueryName();
		
		// execute query
		ItemIterable<QueryResult> results = session.query(queryString, false);

		for (QueryResult qResult : results) {
		   String objectId = qResult.getPropertyValueByQueryName(objectIdQueryName);
		   Document doc = (Document) session.getObject(session.createObjectId(objectId));
		   if (doc.getName().equals(name)) {
			   name = "Copia de " + name;
		   }
		}
		
		Folder folder = Folder.class.cast(session.getObjectByPath(targetPath));

		if (!properties.containsKey(PropertyIds.NAME)) {
			properties.put(PropertyIds.NAME, name);
		}

		InputStream stream = new ByteArrayInputStream(content);
		ContentStream contentStream = session.getObjectFactory().createContentStream(name,
				Long.valueOf(content.length), "text/plain", stream);

		Document doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);

		List<Object> currentAspects = doc.getProperty("cmis:secondaryObjectTypeIds").getValues();
		for (String aspect : aspects) {
			if (!currentAspects.contains(aspect)) {
				currentAspects.add(aspect);
			}
		}

		if (!currentAspects.isEmpty()) {
			HashMap<String, Object> aspectProps = new HashMap<String, Object>();
			aspectProps.put("cmis:secondaryObjectTypeIds", currentAspects);
			doc.updateProperties(aspectProps);
		}
		return doc;
	}
}
