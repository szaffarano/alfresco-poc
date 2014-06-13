package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;
import java.util.HashMap;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import afip.tecno.alfresco.model.TecnoModel;

public class AgregarAspectoIncumplimientoExecutionListener extends ListenerUtil implements TaskListener {
	private static final long serialVersionUID = 3497545356806546176L;

	@Override
	public void notify(DelegateTask task) {
		NodeRef form = getInitiatorNode(task.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();
		nodeService.addAspect(form, TecnoModel.ASPECT_TECNO_INCUMPLIDO, new HashMap<QName, Serializable>());
	}
}
