package hello_module.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.logging.Logger;


/**
 * Created by Saniye on 23.09.16.
 */
public class LoginPageBinder extends Composite {
    interface LoginBinderUiBinder extends UiBinder<HTMLPanel, LoginPageBinder> {
    }

    private static LoginBinderUiBinder ourUiBinder = GWT.create(LoginBinderUiBinder.class);
    private final LoginServiceIntfAsync loginService = GWT.create(LoginServiceIntf.class);
    private GWTHelloConstants constants = GWT.create(GWTHelloConstants.class);
    private static Logger logger = Logger.getLogger(LoginPageBinder.class.getName());
    private static final String LOGIN_PAGE = "LoginPage: ";
    private static final String CONTENT_DIV="content";

    @UiField
    Button loginButton;
    @UiField
    TextBox nameField;
    @UiField
    PasswordTextBox passwordTextBox;
    @UiField
    Label labelName;
    @UiField
    Label labelPassword;
    @UiField
    Label errorMessage;

    public LoginPageBinder() {
        initWidget(ourUiBinder.createAndBindUi(this));
        labelName.setText(constants.username());
        labelPassword.setText(constants.password());
        loginButton.setText(constants.login());
        logger.info(LOGIN_PAGE + "Login page loaded");
        logger.info(LOGIN_PAGE + "Browser locale is " + LocaleInfo.getCurrentLocale().getLocaleName());
        loginButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String loginToServer = nameField.getText();
                String passwordToServer = passwordTextBox.getText();
                if (!loginToServer.isEmpty() && !passwordToServer.isEmpty()) {
                    loginButton.setEnabled(false);
                    sendUserToServer(loginToServer, passwordToServer);
                    logger.info(LOGIN_PAGE +"Login "+ loginToServer + " "+ " were sent to server");
                } else {
                    logger.info(LOGIN_PAGE + "Error message \"Login and password fields are empty\"");
                    errorMessage.setText(constants.validationError());
                }
            }
        });


    }

    private void sendUserToServer(String login, String password) {
        loginService.login(login, password, LocaleInfo.getCurrentLocale().getLocaleName(),
                new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        errorMessage.setText(constants.serverError());
                        logger.info(LOGIN_PAGE + "Error message \"Incorrect username or password\"");
                        loginButton.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(String result) {
                        RootPanel.get(CONTENT_DIV).clear();
                        HomePageBinder binder = new HomePageBinder(result);
                        RootPanel.get(CONTENT_DIV).add(binder);
                        binder.returnButton.setEnabled(true);
                    }
                });
    }


}