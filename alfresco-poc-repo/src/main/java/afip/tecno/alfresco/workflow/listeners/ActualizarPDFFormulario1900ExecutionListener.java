package afip.tecno.alfresco.workflow.listeners;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import afip.tecno.alfresco.writer.NodeToPDF;

public class ActualizarPDFFormulario1900ExecutionListener extends ListenerUtil implements TaskListener {
	private static final long serialVersionUID = 3497545356806546176L;

	@Override
	public void notify(DelegateTask task) {

		NodeRef form = getInitiatorNode(task.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();
		DictionaryService dictionaryService = getServiceRegistry().getDictionaryService();
		ContentService contentService = getServiceRegistry().getContentService();
		NodeToPDF nodeToPDF = getNodeToPDF();

		Map<QName, Serializable> properties = nodeService.getProperties(form);
		StringBuilder sb = new StringBuilder();

		Map<String, Object> values = new HashMap<String, Object>();
		for (Map.Entry<QName, Serializable> entry : properties.entrySet()) {
			QName qname = entry.getKey();
			if (FORM1900_PROPERTIES.values().contains(qname)) {
				PropertyDefinition propDef = dictionaryService.getProperty(qname);

				String title = propDef.getTitle(dictionaryService);
				Object value = entry.getValue();

				sb.append(String.format("%s = %s\n", title, value));
				values.put(title, value);
			}
		}
		ContentWriter writer = contentService.getWriter(form, ContentModel.PROP_CONTENT, true);
		writer.putContent(new ByteArrayInputStream(nodeToPDF.convert(values)));
		
		ContentData cd = ContentData.class.cast(nodeService.getProperty(form, ContentModel.PROP_CONTENT));
		ContentData pdfCD = ContentData.setMimetype(cd, MimetypeMap.MIMETYPE_PDF);

		nodeService.setProperty(form, ContentModel.PROP_CONTENT, pdfCD);
	}
}
