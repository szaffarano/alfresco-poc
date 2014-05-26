package afip.tecno.alfresco.action;

import static org.junit.Assert.assertNotNull;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
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
public class IncumplirActionTest {

	private static final String ADMIN_USER_NAME = "admin";

	static Logger log = Logger.getLogger(IncumplirActionTest.class);

	@Autowired
	@Qualifier("ActionService")
	protected ActionService actionService;

	@Test
	public void testGetAction() {
		AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
		Action action = actionService.createAction(IncumplirActionExecuter.NAME);
		assertNotNull(action);
	}
}