package afip.tecno.alfresco;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.PropertyIds;

public class PublisherTest {
	public static void main(String[] args) {
		CMISPublisher publisher = new CMISPublisher();
		Map<String, Object> props = new HashMap<String, Object>();

		String targetPath = "/Sitios/prueba/documentLibrary/Adquisiciones";
		ArrayList<String> aspects = new ArrayList<String>();

		props.put(PropertyIds.OBJECT_TYPE_ID, "D:tecno:adquisicion");
		
		props.put("tecno:responsable", "Pepe");
		props.put("tecno:fechaAlta", new GregorianCalendar());

		aspects.add("P:tecno:revisable");
		
		publisher.publish(targetPath, "Hola Mundo", "Hola mundo!!".getBytes(), props, aspects);

	}

}
