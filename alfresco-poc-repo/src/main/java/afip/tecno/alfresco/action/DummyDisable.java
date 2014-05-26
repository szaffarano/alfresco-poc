package afip.tecno.alfresco.action;

import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.repository.NodeRef;

public class DummyDisable extends DummyActionExecuter {

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.error("Ejecutando acción DummyEnable...");

		action.setParameterValue(PARAM_ENABLED, Boolean.FALSE);
		
		super.executeImpl(action, actionedUponNodeRef);
	}

}
