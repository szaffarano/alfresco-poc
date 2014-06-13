package afip.tecno.alfresco.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.uno.RuntimeException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class NodeToPDF {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private FopFactory fopFactory = FopFactory.newInstance();

	private DictionaryService dictionaryService;
	private NodeService nodeService;
	private String template = "f19002fo.xsl";

	public byte[] convert(NodeRef nodeRef) {
		logger.info("Ejecutando acción de comprobante de conformidad");

		Map<QName, Serializable> properties = nodeService.getProperties(nodeRef);
		Map<String, Object> values = new HashMap<String, Object>();
		for (Map.Entry<QName, Serializable> entry : properties.entrySet()) {
			QName qname = entry.getKey();
			PropertyDefinition propDef = dictionaryService.getProperty(qname);

			String title = propDef.getTitle(dictionaryService);
			Object value = entry.getValue();

			values.put(title, value);

			logger.info(String.format("Property [%s = %s]", title, value));
		}

		return convert(values);
	}

	public byte[] convert(Map<String, Object> values) {
		InputStream xslt = Thread.currentThread().getContextClassLoader().getResourceAsStream(template);

		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new MapEntryConverter());
		xStream.alias("map", java.util.Map.class);
		String xml = xStream.toXML(values);

		logger.debug("Generando pdf en base a: " + xml);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, baos);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xslt));

			Source src = new StreamSource(new StringReader(xml));
			Result res = new SAXResult(fop.getDefaultHandler());

			transformer.transform(src, res);
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public static class MapEntryConverter implements Converter {

		public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
			return AbstractMap.class.isAssignableFrom(clazz);
		}

		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

			AbstractMap<?, ?> map = (AbstractMap<?, ?>) value;
			for (Object obj : map.entrySet()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) obj;

				writer.startNode("entry");

				writer.startNode("key");
				writer.setValue(entry.getKey() != null ? entry.getKey().toString() : "");
				writer.endNode();

				writer.startNode("value");
				writer.setValue(entry.getValue() != null ? entry.getValue().toString() : "");
				writer.endNode();

				writer.endNode();
			}

		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

			Map<String, String> map = new HashMap<String, String>();

			while (reader.hasMoreChildren()) {
				reader.moveDown();
				String key = reader.getAttribute("name");
				String value = reader.getValue();
				map.put(key, value);
				reader.moveUp();
			}

			return map;
		}

	}
}
