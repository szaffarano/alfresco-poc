package afip.tecno.alfresco.workflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.alfresco.repo.jscript.ScriptNode;

public class VerificarTipoListener extends ListenerUtil implements ExecutionListener {
	private static final long serialVersionUID = -4457168659869063162L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
//		ScriptNode form = getInitiatorNode(ScriptNode.class.cast(execution.getVariable("bpm_package")));
//		logger.info("Nodo iniciador: " + form != null ? form.getId() : "<desconocido>");

		logger.info("Imprimiendo variables");
		for (String v : execution.getVariableNames()) {
			logger.info("\t" + v);
		}
	}
}
