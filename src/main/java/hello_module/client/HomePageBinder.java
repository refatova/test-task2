package hello_module.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.logging.Logger;

/**
 * Created by Saniye on 23.09.16.
 */
public class HomePageBinder extends Composite {
    interface MyBinderUiBinder extends UiBinder<HTMLPanel, HomePageBinder> {
    }

    private static MyBinderUiBinder ourUiBinder = GWT.create(MyBinderUiBinder.class);
    private GWTHelloConstants constants = GWT.create(GWTHelloConstants.class);
    private static Logger logger = Logger.getLogger(HomePageBinder.class.toString());
    private static final String HOME_PAGE = "HomePage: ";
    private static final String CONTENT_DIV="content";
    @UiField
    Label greetingMessage;
    @UiField
    Button returnButton;

    public HomePageBinder(String greeting) {
        initWidget(ourUiBinder.createAndBindUi(this));
        greetingMessage.setText(greeting);
        logger.info(HOME_PAGE + "Display message: " + greeting);
        returnButton.setText(constants.quit());

        returnButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                logger.info(HOME_PAGE + "Return button pushed");
                RootPanel.get(CONTENT_DIV).clear();
                LoginPageBinder loginPageBinder = new LoginPageBinder();
                loginPageBinder.nameField.setText("");
                loginPageBinder.passwordTextBox.setText("");
                RootPanel.get(CONTENT_DIV).add(loginPageBinder);
                loginPageBinder.loginButton.setEnabled(true);
            }
        });
    }
}