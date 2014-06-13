package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

public class CargarVariablesFormulario1900ExecutionListener extends ListenerUtil implements TaskListener {
	private static final long serialVersionUID = 3497545356806546176L;

	@Override
	public void notify(DelegateTask task) {
		NodeRef factura = getInitiatorNode(task.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();

		Map<QName, Serializable> nodeProperties = nodeService.getProperties(factura);
		for (Map.Entry<String, QName> e : FORM1900_PROPERTIES.entrySet()) {
			logger.info("Seteando variable '{}' en proceso", e.getKey());
			if (nodeProperties.containsKey(e.getValue())) {
				task.setVariable(e.getKey(), nodeProperties.get(e.getValue()));
			}
		}
	}
}
