package afip.tecno.alfresco.workflow.listeners;

import java.io.Serializable;
import java.util.HashMap;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentService;
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
		ContentService contentService = getServiceRegistry().getContentService();

		if (form != null) {
			QName nodeType = nodeService.getType(form);
			if (!nodeType.equals(TecnoModel.TYPE_TECNO_FORM1900)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Cambiando tipo a nodo iniciador.  Tipo original: %s, nuevo tipo: %s",
							nodeType, TecnoModel.TYPE_TECNO_FORM1900));
				}
				nodeService.setType(form, TecnoModel.TYPE_TECNO_FORM1900);
				nodeService.addAspect(form, ContentModel.ASPECT_VERSIONABLE, new HashMap<QName, Serializable>());
				contentService.getWriter(form, ContentModel.PROP_CONTENT, true)
						.putContent("Sin Datos");
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("El nodo iniciador ya es de tipo form1900");
				}
			}
		}

		logger.info("Proceso iniciado, seteando property de finalización en false");
	}
}
