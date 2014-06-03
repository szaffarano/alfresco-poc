package afip.tecno.alfresco.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;

@RunWith(RemoteTestRunner.class)
@Remote(runnerClass = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class ComprobanteConformidadActionTest {

	private static final String ADMIN_USER_NAME = "admin";

	static Logger log = Logger.getLogger(ComprobanteConformidadActionTest.class);

	@Autowired
	@Qualifier("NodeService")
	protected NodeService nodeService;

	@Autowired
	@Qualifier("ActionService")
	protected ActionService actionService;

	@Autowired
	@Qualifier("nodeLocatorService")
	protected NodeLocatorService nodeLocatorService;

	@Test
	public void testGetAction() {
		AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
		NodeRef companyHome = nodeLocatorService.getNode(CompanyHomeNodeLocator.NAME, null, null);
		
		String name = "Contenido de prueba (" + System.currentTimeMillis() + ")";
		Map<QName, Serializable> contentProps = new HashMap<QName, Serializable>();
		contentProps.put(ContentModel.PROP_NAME, name);

		// create content node
		ChildAssociationRef association = nodeService
				.createNode(companyHome, ContentModel.ASSOC_CONTAINS,
						QName.createQName(NamespaceService.CONTENT_MODEL_PREFIX, name), ContentModel.TYPE_CONTENT,
						contentProps);

		NodeRef content = association.getChildRef();

		Action action = actionService.createAction(ComprobanteConformidadAction.NAME);

		actionService.executeAction(action, content);
		nodeService.deleteNode(content);
	}

}
