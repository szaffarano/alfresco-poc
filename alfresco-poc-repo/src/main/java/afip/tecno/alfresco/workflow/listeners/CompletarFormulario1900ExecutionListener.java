package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import afip.tecno.alfresco.model.TecnoModel;

public class CompletarFormulario1900ExecutionListener extends ListenerUtil implements TaskListener {
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
		Map<QName, Serializable> nodeProperties = new HashMap<QName, Serializable>();
		for (String[] p : FORM1900_PROPERTIES) {
			if (task.hasVariable(p[0])) {
				logger.info("Seteando variable '{}' en Node: {}", p[0], p[1]);
				nodeProperties.put(TecnoModel.qnameFor(p[1]), Serializable.class.cast(task.getVariable(p[0])));
			} else {
				logger.info("Variable {} desconocida", p[0]);
			}
		}
		nodeProperties.put(ContentModel.PROP_NAME, nodeService.getProperty(form, ContentModel.PROP_NAME));
		nodeService.setProperties(form, nodeProperties);
	}
}
