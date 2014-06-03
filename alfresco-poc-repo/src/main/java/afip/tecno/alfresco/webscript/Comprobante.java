package afip.tecno.alfresco.webscript;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.alfresco.repo.action.executer.ActionExecuter;
import org.alfresco.repo.web.scripts.content.StreamContent;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import afip.tecno.alfresco.action.ComprobanteConformidadAction;

public class Comprobante extends StreamContent {
	private NodeService nodeService;
	private ActionService actionService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
		logger.info("Ejecutando webscript...");
		String msg = "se esperaba parametro nodeRef";
		if (req.getParameter("nodeRef") == null) {
			sendError(req, res, msg);
			return;
		}

		NodeRef nodeRef = new NodeRef(req.getParameter("nodeRef"));
		if (!nodeService.exists(nodeRef)) {
			sendError(req, res, "Nodo inexistente: " + nodeRef);
			return;
		}

		Action action = actionService.createAction(ComprobanteConformidadAction.NAME);
		actionService.executeAction(action, nodeRef);
		Serializable result = action.getParameterValue(ActionExecuter.PARAM_RESULT);
		if (result == null || !(result instanceof byte[])) {
			sendError(req, res, "Se esperaba un pdf, se obtuvo: " + result);
			return;
		}

		File f = new File(System.getProperty("java.io.tmpdir"), "comprobante.pdf");
		try {
			FileOutputStream fos = new FileOutputStream(f);
			fos.write((byte[]) result);
			fos.close();

			res.setContentType("application/json");
			res.setHeader("content-disposition", "filename=comprobante.pdf");

			streamContent(req, res, f, true, f.getName(), new HashMap<String, Object>());
		} finally {
			f.delete();
		}
	}

	private void sendError(WebScriptRequest req, WebScriptResponse res, String msg) throws IOException {
		File f = new File(System.getProperty("java.io.tmpdir"), "error.json");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(String.format("{errorMessage: '%s'}", msg));
			fw.close();

			res.setContentType("application/json");
			res.setHeader("content-disposition", "filename=error.json");

			streamContent(req, res, f, true, f.getName(), new HashMap<String, Object>());
		} finally {
			f.delete();
		}
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}
}
