package afip.tecno.alfresco.writer;

import org.xml.sax.InputSource;

/**
 * This class is a special InputSource decendant for using ProjectTeam
 * instances as XML sources.
 */
public class Formulario1900InputSource extends InputSource {

    private Formulario1900 formulario1900;

    /**
     * Constructor for the ProjectTeamInputSource
     * @param projectTeam The ProjectTeam object to use
     */
    public Formulario1900InputSource(Formulario1900 formulario1900) {
        this.formulario1900 = formulario1900;
    }

    /**
     * Returns the projectTeam.
     * @return ProjectTeam
     */
    public Formulario1900 getFormulario1900() {
        return formulario1900;
    }

    /**
     * Sets the projectTeam.
     * @param projectTeam The projectTeam to set
     */
    public void setProjectTeam(Formulario1900 formulario1900) {
        this.formulario1900 = formulario1900;
    }

}
