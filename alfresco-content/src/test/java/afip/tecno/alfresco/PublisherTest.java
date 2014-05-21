package afip.tecno.alfresco;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
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
		// props.put("tecno:pdf", "");
		// props.put("tecno:items", "");

		aspects.add("P:tecno:incumplido");

		Document parent = publisher.publish(targetPath, "Prueba #1", toByteArray("form.jpg"), "image/png", props,
				aspects);

		for (int i = 0; i < 3; i++) {
			String name = "Item " + i;

			Map<String, Object> childProps = new HashMap<String, Object>();
			childProps.put(PropertyIds.OBJECT_TYPE_ID, "D:tecno:itemPliego");
			childProps.put("tecno:renglon", i);
			childProps.put("tecno:item", name);
			childProps.put("tecno:cantidadServicios", 10);
			Document child = publisher.publish(targetPath, name, "".getBytes(), "text/plain", childProps,
					new ArrayList<String>());
			publisher.connect(parent.getId(), child.getId(), "R:tecno:items");
		}

	}

	private static byte[] toByteArray(String imagen) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(imagen);
		try {
			while (is.available() > 0) {
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				baos.write(buffer);
			}
		} catch (IOException e) {
		}
		byte[] content = baos.toByteArray();
		return content;
	}
}
