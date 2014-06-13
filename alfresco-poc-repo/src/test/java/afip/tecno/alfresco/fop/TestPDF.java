package afip.tecno.alfresco.fop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import afip.tecno.alfresco.writer.NodeToPDF;

public class TestPDF {
	private Logger logger = LoggerFactory.getLogger("pdf");

	@Test
	public void testGeneratePDF() {
		logger.info("Testeando generación de pdf");

		InputStream is = null;
		FileOutputStream fos = null;
		try {
			logger.info("Levantando xsl de resource: " + is);
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("clave1", "valor1");
			values.put("clave2", "valor2");
			values.put("clave3", "valor3");
			values.put("clave4", "valor4");
			byte[] pdf = new NodeToPDF().convert(values);
			Assert.assertNotNull(pdf);
			Assert.assertFalse(pdf.length < 20);
			fos = new FileOutputStream("/tmp/test.pdf");
			fos.write(pdf);
		} catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
