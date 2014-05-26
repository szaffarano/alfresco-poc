package afip.tecno.alfresco.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import afip.tecno.alfresco.model.TecnoModel;

public class DummyActionExecuter extends ActionExecuterAbstractBase {
	public static final String NAME = "dummy";
	public static final String PARAM_ENABLED = "enabled";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected NodeService nodeService;

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(new ParameterDefinitionImpl(PARAM_ENABLED, DataTypeDefinition.BOOLEAN, false,
				getParamDisplayLabel(PARAM_ENABLED)));

	}

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.error("Ejecutando acción de dummy...");

		Boolean enabled = (Boolean) action.getParameterValue(PARAM_ENABLED);

		if (enabled == null)
			enabled = Boolean.TRUE;

		Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
		properties.put(QName.createQName(TecnoModel.NAMESPACE_TECNO_CONTENT_MODEL, TecnoModel.PROP_ENABLED), enabled);

		nodeService.setProperties(actionedUponNodeRef, properties);
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}
