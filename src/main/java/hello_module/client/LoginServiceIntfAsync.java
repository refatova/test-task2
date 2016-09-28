package hello_module.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Created by Saniye on 21.09.16.
 */
public interface LoginServiceIntfAsync {
    void login(String username, String password,String locale, AsyncCallback<String> async);

}
