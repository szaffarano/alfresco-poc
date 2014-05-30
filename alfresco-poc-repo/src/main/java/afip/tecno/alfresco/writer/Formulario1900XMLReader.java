package afip.tecno.alfresco.writer;

//Java
import java.io.IOException;
import java.util.Iterator;

//SAX
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import afip.tecno.alfresco.writer.AbstractObjectReader;

/**
 * XMLReader implementation for the ProjectTeam class. This class is used to
 * generate SAX events from the ProjectTeam class.
 */
public class Formulario1900XMLReader extends AbstractObjectReader {

    /**
     * @see org.xml.sax.XMLReader#parse(InputSource)
     */
    public void parse(InputSource input) throws IOException, SAXException {
        if (input instanceof Formulario1900InputSource) {
            parse(((Formulario1900InputSource)input).getFormulario1900());
        } else {
            throw new SAXException("Unsupported InputSource specified. Must be a ProjectTeamInputSource");
        }
    }

    /**
     * Starts parsing the ProjectTeam object.
     * @param projectTeam The object to parse
     * @throws SAXException In case of a problem during SAX event generation
     */
    public void parse(Formulario1900 formulario1900) throws SAXException {
        if (formulario1900 == null) {
            throw new NullPointerException("Parameter formulario1900 must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }

        //Start the document
        handler.startDocument();

        //Generate SAX events for the ProjectTeam
        generateFor(formulario1900);

        //End the document
        handler.endDocument();
    }


    /**
     * Generates SAX events for a ProjectTeam object.
     * @param projectTeam ProjectTeam object to use
     * @throws SAXException In case of a problem during SAX event generation
     */
    protected void generateFor(Formulario1900 formulario1900) throws SAXException {
        if (formulario1900 == null) {
            throw new NullPointerException("Parameter projectTeam must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }

        handler.startElement("formulario");
        handler.element("fields", formulario1900.getName());
        Iterator<FormularioField> i = formulario1900.getFields().iterator();
        while (i.hasNext()) {
        	FormularioField field = (FormularioField)i.next();
            generateFor(field);
        }
        handler.endElement("fields");
    }

    /**
     * Generates SAX events for a ProjectMember object.
     * @param projectMember ProjectMember object to use
     * @throws SAXException In case of a problem during SAX event generation
     */
    protected void generateFor(FormularioField formularioField) throws SAXException {
        if (formularioField == null) {
            throw new NullPointerException("Parameter formularioField must not be null");
        }
        if (handler == null) {
            throw new IllegalStateException("ContentHandler not set");
        }

        handler.startElement("field");
        handler.element("key", formularioField.getKey());
        handler.element("value", formularioField.getValue());
        handler.endElement("field");
    }

}
