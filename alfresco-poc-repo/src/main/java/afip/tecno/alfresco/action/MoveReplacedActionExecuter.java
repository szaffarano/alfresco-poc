package afip.tecno.alfresco.action;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveReplacedActionExecuter extends ActionExecuterAbstractBase {
	public static final String NAME = "move-replaced";
	public static final String PARAM_DESTINATION_FOLDER = "destination-folder";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(new ParameterDefinitionImpl(PARAM_DESTINATION_FOLDER, DataTypeDefinition.NODE_REF, true,
				getParamDisplayLabel(PARAM_DESTINATION_FOLDER)));
	}

	@Override
	public void executeImpl(Action ruleAction, NodeRef actionedUponNodeRef) {
		logger.error("Ejecutando MoveReplacedActionExecuter");
	}

}
