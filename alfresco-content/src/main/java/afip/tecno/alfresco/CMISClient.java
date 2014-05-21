package afip.tecno.alfresco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

public class CMISClient {
	private final String user;
	private final String password;
	private final String serviceUrl = "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom";
	private Session session = null;

	public CMISClient(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public Session getSession() {
		if (session == null) {
			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map<String, String> parameter = new HashMap<String, String>();

			parameter.put(SessionParameter.USER, user);
			parameter.put(SessionParameter.PASSWORD, password);

			parameter.put(SessionParameter.ATOMPUB_URL, serviceUrl);
			parameter.put(SessionParameter.BINDING_TYPE,
					BindingType.ATOMPUB.value());

			List<Repository> repositories = factory.getRepositories(parameter);

			this.session = repositories.get(0).createSession();
		}
		return this.session;
	}
}
