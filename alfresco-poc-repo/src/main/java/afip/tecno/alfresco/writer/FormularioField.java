package afip.tecno.alfresco.writer;

/**
 * This bean represents a formulario field.
 */
public class FormularioField {

    private String key;
    private String value;

    public FormularioField() {
    }

    public FormularioField(String key, String value) {
        setKey(key);
        setValue(value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
