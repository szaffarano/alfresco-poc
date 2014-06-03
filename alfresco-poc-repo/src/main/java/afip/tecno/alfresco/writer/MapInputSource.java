package afip.tecno.alfresco.writer;

import java.util.Map;

import org.xml.sax.InputSource;

public class MapInputSource extends InputSource {

	private Map<String, Object> values;

	public MapInputSource(Map<String, Object> values) {
		this.values = values;
	}

	public Map<String, Object> getValues() {
		return values;
	}

}
