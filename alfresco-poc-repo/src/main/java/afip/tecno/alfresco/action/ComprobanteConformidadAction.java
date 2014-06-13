package afip.tecno.alfresco.action;

import java.util.List;

import org.alfresco.repo.action.executer.ActionExecuter;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import afip.tecno.alfresco.writer.NodeToPDF;

public class ComprobanteConformidadAction extends ActionExecuterAbstractBase {
	public static final String NAME = "comprobante-conformidad";
	public static final String PARAM_DESTINATION_FOLDER = "destination-folder";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private NodeToPDF nodeToPDF;

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
	}

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.info("Ejecutando acción de comprobante de conformidad");

		action.setParameterValue(ActionExecuter.PARAM_RESULT, nodeToPDF.convert(actionedUponNodeRef));
	}

	public void setNodeToPDF(NodeToPDF nodeToPDF) {
		this.nodeToPDF = nodeToPDF;
	}
}
