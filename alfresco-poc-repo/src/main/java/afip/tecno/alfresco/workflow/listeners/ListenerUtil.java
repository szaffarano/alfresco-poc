package afip.tecno.alfresco.workflow.listeners;

import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiConstants;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ListenerUtil {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected static final String[][] FORM1900_PROPERTIES = {
		{ "tecno_fechaVencimiento", "fechaVencimiento" },
		{ "tecno_expediente", "expediente" },
		{ "tecno_items", "items" },
		{ "tecno_objetoServicio", "objetoServicio" },
		{ "tecno_enabled", "enabled" },
		{ "tecno_ordenCompra", "ordenCompra" },
		{ "tecno_fechaInicio", "fechaInicio" },
		{ "tecno_direccion", "direccion" },
		{ "tecno_areaResponsable", "areaResponsable" },
		{ "tecno_domicilio", "domicilio" } };
	
	protected NodeRef getInitiatorNode(Object pkg) {
		NodeRef initiator = null;
		if (ScriptNode.class.isInstance(pkg)) {
			ScriptNode node = ScriptNode.class.cast(pkg);
			NodeService nodeService = getServiceRegistry().getNodeService();
			List<ChildAssociationRef> childAssocs = nodeService.getChildAssocs(node.getNodeRef());
			if (!childAssocs.isEmpty()) {
				// solucion de compromiso, tomo el primer adjunto...
				initiator = childAssocs.get(0).getChildRef();
			}
		}
		return initiator;
	}

	protected ServiceRegistry getServiceRegistry() {
		ProcessEngineConfigurationImpl config = Context.getProcessEngineConfiguration();
		if (config != null) {
			// Fetch the registry that is injected in the activiti
			// spring-configuration
			ServiceRegistry registry = (ServiceRegistry) config.getBeans().get(
					ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);
			if (registry == null) {
				throw new RuntimeException(
						"Service-registry not present in ProcessEngineConfiguration beans, expected ServiceRegistry with key"
								+ ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);
			}
			return registry;
		}
		throw new IllegalStateException("No ProcessEngineConfiguration found in active context");
	}
}
