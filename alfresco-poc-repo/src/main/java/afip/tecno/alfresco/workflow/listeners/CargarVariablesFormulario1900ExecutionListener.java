package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import afip.tecno.alfresco.model.TecnoModel;

public class CargarVariablesFormulario1900ExecutionListener extends ListenerUtil implements TaskListener {
	private static final long serialVersionUID = 3497545356806546176L;

	@Override
	public void notify(DelegateTask task) {
		NodeRef form = getInitiatorNode(task.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();
		if (form == null) {
			throw new RuntimeException(
					"No soportado iniciar workflow sin un archivo adjunto representando formulario 1900");
		}

		logger.info("Imprimiendo variables");
		Map<QName, Serializable> nodeProperties = nodeService.getProperties(form);

		for (String[] p : FORM1900_PROPERTIES) {
			QName propQName = TecnoModel.qnameFor(p[1]);
			logger.info("Seteando variable '{}' en proceso", p[0]);
			if (nodeProperties.containsKey(propQName)) {
				task.setVariable(p[0], nodeProperties.get(propQName));
			}
		}
	}
}
