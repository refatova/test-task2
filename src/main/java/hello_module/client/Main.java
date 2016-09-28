package hello_module.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import java.util.logging.Logger;


/**
 * Created by Saniye on 21.09.16.
 */
public class Main implements EntryPoint {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public void onModuleLoad() {
        logger.info("Module Main has been loaded");
        LoginPageBinder loginPageBinder = new LoginPageBinder();
        RootPanel.get("content").add(loginPageBinder);
    }
}
