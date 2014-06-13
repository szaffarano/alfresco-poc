package afip.tecno.alfresco.workflow.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiConstants;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import afip.tecno.alfresco.model.TecnoModel;
import afip.tecno.alfresco.writer.NodeToPDF;

public abstract class ListenerUtil {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected static final Map<String, QName> FORM1900_PROPERTIES = new HashMap<String, QName>() {
		private static final long serialVersionUID = -4019020608362231644L;
		{
			put("tecno_areaResponsable", TecnoModel.PROP_AREA_RESPONSABLE);
			put("tecno_direccion", TecnoModel.PROP_DIRECCION);
			put("tecno_domicilio", TecnoModel.PROP_DOMICILIO);

			put("tecno_expediente", TecnoModel.PROP_EXPEDIENTE);
			put("tecno_ordenCompra", TecnoModel.PROP_ORDEN_COMPRA);

			put("tecno_fechaInicio", TecnoModel.PROP_FECHA_INICIO);
			put("tecno_fechaVencimiento", TecnoModel.PROP_FECHA_VENCIMIENTO);

			put("tecno_objetoServicio", TecnoModel.PROP_OBJETO_SERVICIO);
			put("tecno_tipoServicio", TecnoModel.PROP_TIPO_SERVICIO);
			put("tecno_otroTipoServicio", TecnoModel.PROP_OTRO_TIPO_SERVICIO);
			put("tecno_periodicidad", TecnoModel.PROP_PERIODICIDAD);

			put("tecno_periodoDesde", TecnoModel.PROP_PERIODO_DESDE);
			put("tecno_periodoHasta", TecnoModel.PROP_PERIODO_HASTA);

			put("tecno_proveedor", TecnoModel.PROP_PROVEEDOR);
			put("tecno_cuitProveedor", TecnoModel.PROP_CUIT_PROVEEDOR);

			put("tecno_remitoNumero", TecnoModel.PROP_REMITO_NUMERO);
			put("tecno_facturaNumero", TecnoModel.PROP_FACTURA_NUMERO);

			put("tecno_regimenContratacion", TecnoModel.PROP_REGIMEN_CONTRATACION);

			put("tecno_notaCredito", TecnoModel.PROP_NOTA_CREDITO);
			put("tecno_notaDebito", TecnoModel.PROP_NOTA_DEBITO);

			put("tecno_items", TecnoModel.PROP_ITEMS);

			put("tecnowf_conforme", TecnoModel.PROP_CONFORME);
			put("tecnowf_aprobado", TecnoModel.PROP_APROBADO);

			put("tecno_numero", TecnoModel.PROP_NUMERO);
			put("tecno_motivo", TecnoModel.PROP_MOTIVO);
			put("tecno_importe", TecnoModel.PROP_IMPORTE);
		}
	};

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
		if (initiator == null) {
			throw new RuntimeException(
					"No soportado iniciar workflow sin un archivo adjunto representando una factura.");
		}

		return initiator;
	}

	protected NodeToPDF getNodeToPDF() {
		ProcessEngineConfigurationImpl config = Context.getProcessEngineConfiguration();
		if (config != null) {
			return NodeToPDF.class.cast(getServiceRegistry().getService(QName.createQName("NodeToPDFService")));
		}
		throw new IllegalStateException("No ProcessEngineConfiguration found in active context");
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
