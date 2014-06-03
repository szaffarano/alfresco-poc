package afip.tecno.alfresco.fop;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import afip.tecno.alfresco.action.ComprobanteConformidadAction;

public class TestPDF {
	private Logger logger = LoggerFactory.getLogger("pdf");

	@Test
	public void testGeneratePDF() {
		logger.info("Testeando generación de pdf");

		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("f19002fo.xsl");
			logger.info("Levantando xsl de resource: " + is);
			ComprobanteConformidadAction action = new ComprobanteConformidadAction();
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("clave1", "valor1");
			values.put("clave2", "valor2");
			values.put("clave3", "valor3");
			values.put("clave4", "valor4");
			byte[] pdf = action.pdf(values, is);
			Assert.assertNotNull(pdf);
			Assert.assertFalse(pdf.length < 20);
		} catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
