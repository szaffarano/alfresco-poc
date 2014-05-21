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

		String targetPath = "/Sitios/prueba/documentLibrary/Certificados";
		ArrayList<String> aspects = new ArrayList<String>();

		props.put(PropertyIds.OBJECT_TYPE_ID, "D:tecno:form1900");
		
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.HOUR, 24);
		
		props.put("tecno:areaResponsable", "Comunicaciones");
		props.put("tecno:direccion", "DITEC");
		props.put("tecno:domicilio", "Paseo Colón 123");
		props.put("tecno:expediente", "99/1234");
		props.put("tecno:ordenCompra", 44);
		props.put("tecno:fechaInicio", today);
		props.put("tecno:fechaVencimiento", tomorrow);
		props.put("tecno:objetoServicio", "No se");
//		props.put("tecno:pdf", "");
//		props.put("tecno:items", "");
		
		aspects.add("P:tecno:incumplido");
		
		publisher.publish(targetPath, "Prueba #1", "Prueba número 1".getBytes(), props, aspects);

	}

}
