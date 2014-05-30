package afip.tecno.alfresco.writer;

import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

public class Formulario1900 {

    private String name;
    private List<FormularioField> fields = new java.util.ArrayList<FormularioField>();

    public List<FormularioField> getFields() {
        return this.fields;
    }

    public void addField(FormularioField field) {
        this.fields.add(field);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Source getSourceForFormulario1900() {
    	return new SAXSource(new Formulario1900XMLReader(), new Formulario1900InputSource(this));
    }

}
