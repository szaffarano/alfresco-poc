package afip.tecno.alfresco.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
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

import afip.tecno.alfresco.writer.Formulario1900;
import afip.tecno.alfresco.writer.FormularioField;

public class WriteMetaDataActionExecuter extends ActionExecuterAbstractBase {
	public static final String NAME = "write-meta-data";
	public static final String PARAM_DESTINATION_FOLDER = "destination-folder";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected NodeService nodeService;
	private FopFactory fopFactory = FopFactory.newInstance();

	public void init() {
		logger.info("initializing WriteMetaDataActionExecuter...");
		// TODO: some initialization
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		logger.error("addParameterDefinitions");
		// TODO: set parameters
	}

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.error("Ejecutando acción de write-meta-data");

		Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
		for (Map.Entry<QName, Serializable> entry : properties.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null) {
				logger.error("Property [" + entry.getKey().toString() + ", " + entry.getValue().toString() + "]");
			}
		}

		// Crear el pdf con los metadatos y grabarlo.
		writeMetaData(properties);
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	private void writeMetaData(Map<QName, Serializable> properties) {
		try {
			logger.error("FOP ExampleObj2PDF\n");
			logger.error("Preparing...");

			File pdffile = new File(System.getProperty("java.io.tmpdir"), "Formulario1900.pdf");

			InputStream is = null;
			try {
				is = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("f19002fo.xsl");
				logger.info("Levantando xsl de resource: " + is);
				convertFormulario19002PDF(createSampleFormulario1900(properties), is, pdffile);
			} finally {
				if (is != null) {
					is.close();
				}
			}
			logger.error("Success!");
		} catch (Exception e) {
			logger.error(e.toString(), e);

		}
	}

	public void convertFormulario19002PDF(Formulario1900 formulario1900, InputStream xslt, File pdf)
			throws IOException, FOPException, TransformerException {

		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		// configure foUserAgent as desired

		// Setup output
		OutputStream out = new java.io.FileOutputStream(pdf);
		out = new java.io.BufferedOutputStream(out);
		try {
			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xslt));

			// Setup input for XSLT transformation
			Source src = formulario1900.getSourceForFormulario1900();

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);
		} finally {
			out.close();
		}
	}

	private Formulario1900 createSampleFormulario1900(Map<QName, Serializable> properties) {
		Formulario1900 formulario1900 = new Formulario1900();
		formulario1900.setName("Soy un formulario 1900");

		for (Map.Entry<QName, Serializable> entry : properties.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null) {
				// logger.error("Property [" + entry.getKey().toString() + ", "
				// + entry.getValue().toString() + "]");
				formulario1900.addField(new FormularioField(entry.getKey().toString(), entry.getValue().toString()));
			}
		}

		return formulario1900;
	}

}
