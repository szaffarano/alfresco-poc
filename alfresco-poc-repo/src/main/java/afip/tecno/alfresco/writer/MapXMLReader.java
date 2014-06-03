package afip.tecno.alfresco.writer;

import java.io.IOException;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MapXMLReader extends AbstractObjectReader {

	public void parse(InputSource input) throws IOException, SAXException {
		if (MapInputSource.class.isInstance(input)) {
			parse(MapInputSource.class.cast(input).getValues());
		} else {
			throw new SAXException("Unsupported InputSource specified. Must be a Map");
		}
	}

	public void parse(Map<String, Object> values) throws SAXException {
		if (values == null) {
			throw new NullPointerException("Parameter formulario1900 must not be null");
		}
		if (handler == null) {
			throw new IllegalStateException("ContentHandler not set");
		}

		handler.startDocument();

		generateFor(values);

		handler.endDocument();
	}

	protected void generateFor(Map<String, Object> values) throws SAXException {
		if (values == null) {
			throw new NullPointerException("Parameter values must not be null");
		}
		if (handler == null) {
			throw new IllegalStateException("ContentHandler not set");
		}

		handler.startElement("map");
		for (Map.Entry<String, Object> e : values.entrySet()) {
			generateFor(e);
		}
		handler.endElement("map");
	}

	protected void generateFor(Map.Entry<String, Object> entry) throws SAXException {
		if (handler == null) {
			throw new IllegalStateException("ContentHandler not set");
		}

		handler.startElement("entry");
		handler.element("key", entry.getKey());
		handler.element("value", (entry.getValue() != null ? entry.getValue().toString() : "<null>"));
		handler.endElement("entry");
	}

}
