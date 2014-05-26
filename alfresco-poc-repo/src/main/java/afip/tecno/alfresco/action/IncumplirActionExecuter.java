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

public class IncumplirActionExecuter extends ActionExecuterAbstractBase {
	public static final String NAME = "incumplir";
	public static final String PARAM_DATE = "fecha";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(new ParameterDefinitionImpl(PARAM_DATE, DataTypeDefinition.DATE, true,
				getParamDisplayLabel(PARAM_DATE)));

	}

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.error("Ejecutando acción de incumplir...");
	}

}
