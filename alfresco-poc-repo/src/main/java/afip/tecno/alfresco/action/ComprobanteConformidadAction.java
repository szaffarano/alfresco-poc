package afip.tecno.alfresco.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.alfresco.repo.action.executer.ActionExecuter;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import afip.tecno.alfresco.writer.MapInputSource;
import afip.tecno.alfresco.writer.MapXMLReader;

import com.sun.star.uno.RuntimeException;

public class ComprobanteConformidadAction extends ActionExecuterAbstractBase {
	public static final String NAME = "comprobante-conformidad";
	public static final String PARAM_DESTINATION_FOLDER = "destination-folder";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected NodeService nodeService;
	private FopFactory fopFactory = FopFactory.newInstance();
	private DictionaryService dictionaryService;

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		logger.info("addParameterDefinitions");
	}

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.info("Ejecutando acción de comprobante de conformidad");

		Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
		Map<String, Object> values = new HashMap<String, Object>();
		for (Map.Entry<QName, Serializable> entry : properties.entrySet()) {
			QName qname = entry.getKey();
			PropertyDefinition propDef = dictionaryService.getProperty(qname);

			String title = propDef.getTitle(dictionaryService);
			Object value = entry.getValue();

			values.put(title, value);

			logger.info(String.format("Property [%s = %s]", title, value));
		}

		byte[] pdf = writeMetaData(values);

		action.setParameterValue(ActionExecuter.PARAM_RESULT, pdf);
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	// @TODO: de acá para abajo refactoringgggggggggggg

	private byte[] writeMetaData(Map<String, Object> values) {
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("f19002fo.xsl");
			logger.info("Levantando xsl de resource: " + is);
			return pdf(values, is);
		} catch (Exception e) {
			throw new RuntimeException("Error generando PDF", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public byte[] pdf(Map<String, Object> values, InputStream xslt) throws IOException, FOPException,
			TransformerException {

		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, baos);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xslt));

			Source src = new SAXSource(new MapXMLReader(), new MapInputSource(values));

			Result res = new SAXResult(fop.getDefaultHandler());

			transformer.transform(src, res);
			return baos.toByteArray();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}
}
