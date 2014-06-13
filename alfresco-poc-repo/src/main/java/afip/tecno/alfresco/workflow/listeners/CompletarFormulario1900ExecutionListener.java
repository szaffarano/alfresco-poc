package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

public class CompletarFormulario1900ExecutionListener extends ListenerUtil implements TaskListener {
	private static final long serialVersionUID = 3497545356806546176L;

	@Override
	public void notify(DelegateTask task) {

		NodeRef form = getInitiatorNode(task.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();

		for (Map.Entry<String, QName> e : FORM1900_PROPERTIES.entrySet()) {

			if (task.hasVariable(e.getKey())) {
				Serializable value = Serializable.class.cast(task.getVariable(e.getKey()));
				logger.info("Seteando variable '{}' en Node: {}", e.getKey(), e.getValue());
				nodeService.setProperty(form, e.getValue(), value);
			} else {
				logger.error("Variable {} desconocida", e.getKey());
			}
		}
	}
}