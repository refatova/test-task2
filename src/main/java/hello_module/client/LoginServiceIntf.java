package hello_module.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



/**
 * Created by Saniye on 21.09.16.
 */
@RemoteServiceRelativePath("login")
public interface LoginServiceIntf extends RemoteService {
    String login(String username,String password,String locale) throws Exception;
}
