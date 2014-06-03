package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import afip.tecno.alfresco.model.TecnoModel;

public class VerificarTipoListener extends ListenerUtil implements ExecutionListener {
	private static final long serialVersionUID = -4457168659869063162L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		NodeRef form = getInitiatorNode(execution.getVariable("bpm_package"));
		NodeService nodeService = getServiceRegistry().getNodeService();
		if (form != null) {
			QName nodeType = nodeService.getType(form);
			if (!nodeType.equals(TecnoModel.TYPE_TECNO_FORM)) {
				Serializable name = nodeService.getProperty(form, ContentModel.PROP_NAME);
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Cambiando tipo a nodo iniciador.  Tipo original: %s, nuevo tipo: %s",
							nodeType, TecnoModel.TYPE_TECNO_FORM));
				}
				nodeService.setType(form, TecnoModel.TYPE_TECNO_FORM);
				nodeService.setProperty(form, ContentModel.PROP_NAME, name);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("El nodo iniciador ya es de tipo form1900");
				}
			}
		}

		logger.info("Proceso iniciado, seteando property de finalización en false");
		nodeService.setProperty(form, TecnoModel.PROP_WORKFLOW_FINISHESD, Boolean.FALSE);
	}
}
