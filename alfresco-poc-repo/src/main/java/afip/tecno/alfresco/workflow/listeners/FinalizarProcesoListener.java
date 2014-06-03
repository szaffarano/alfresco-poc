package afip.tecno.alfresco.workflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import afip.tecno.alfresco.model.TecnoModel;

public class FinalizarProcesoListener extends ListenerUtil implements ExecutionListener {
	private static final long serialVersionUID = -4457168659869063162L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		NodeRef form = getInitiatorNode(execution.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();
		
		logger.info("Proceso finalizado, seteando property de finalización en true");
		nodeService.setProperty(form, TecnoModel.PROP_WORKFLOW_FINISHESD, Boolean.TRUE);
	}
}
