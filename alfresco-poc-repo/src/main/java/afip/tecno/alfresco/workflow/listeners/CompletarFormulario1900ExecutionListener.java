package afip.tecno.alfresco.workflow.listeners;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class CompletarFormulario1900ExecutionListener extends ListenerUtil implements TaskListener {
	private static final long serialVersionUID = 3497545356806546176L;

	@Override
	public void notify(DelegateTask task) {
//		ScriptNode form = getInitiatorNode(ScriptNode.class.cast(task.getVariable("bpm_package")));
//		logger.info("Nodo iniciador: " + form.getId());
		logger.info("Imprimiendo variables");
		for (String v : task.getVariableNames()) {
			logger.info("\t" + v);
		}
	}

}
