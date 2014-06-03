package afip.tecno.alfresco.workflow.listeners;

import java.util.Collection;
import java.util.Map;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiConstants;
import org.alfresco.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ListenerUtil {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected ScriptNode getInitiatorNode(ScriptNode node) {
		ScriptNode form = null;
		if (!node.getChildAssocs().isEmpty()) {
			Map<String, Object> childs = node.getChildAssociations();
			if (!childs.isEmpty()) {
				@SuppressWarnings("unchecked")
				Collection<ScriptNode> col = (Collection<ScriptNode>) childs.values().iterator().next();
				if (col.isEmpty()) {
					form = col.iterator().next();
				}
			}
		}
		return form;
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
