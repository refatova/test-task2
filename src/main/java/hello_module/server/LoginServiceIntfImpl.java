package hello_module.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import hello_module.client.LoginServiceIntf;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Created by Saniye on 21.09.16.
 */
public class LoginServiceIntfImpl extends RemoteServiceServlet implements LoginServiceIntf {
    static final String MORNING_MESSAGE = "morningMessage";
    static final String DAY_MESSAGE = "dayMessage";
    static final String EVENING_MESSAGE = "eveningMessage";
    static final String NIGHT_MESSAGE = "nightMessage";
    static final String LOCALISATION_BUNDLE = "bundle.MyBundle";
    final static Logger log = LogManager.getLogger(LoginServiceIntfImpl.class.getName());

    @Override
    public String login(String login, String password, String locale)  {
        log.info("Server received login: " + login);
        Locale browserLocal = new Locale(locale);
        Locale.setDefault(browserLocal);
        log.info("Server received locale: " + browserLocal);
        ResourceBundle labels = ResourceBundle.getBundle(LOCALISATION_BUNDLE, browserLocal);
        String username = findUser(login,password);
        if (username==null) throw new UserNotFoundException();
        else {
            String messageCode = getMessageForCurrentTime();
            return labels.getString(messageCode) + " " + username;
        }
    }

    protected String findUser(String login, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Account where login= :login and password= HASH('SHA256', STRINGTOUTF8(CONCAT(:password,sault)), 1000)";
        Query query = session.createQuery(hql);
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<Account> list = query.list();
        if (list.isEmpty()) {
            log.info("User is not found");
            return null ;
        } else {
            String personName = list.get(0).getName();
            log.info("User with name {} was found", personName);
            return personName;
        }

    }

    protected static String choseGreetingMessage(int hours) {
        log.info("Current time is {}", hours);
        String str;
        if (hours >= 6 && hours < 9) {
            str = MORNING_MESSAGE;
        } else if (hours >= 9 && hours < 19) {
            str = DAY_MESSAGE;
        } else if (hours >= 19 && hours < 23) {
            str = EVENING_MESSAGE;
        } else {
            str = NIGHT_MESSAGE;
        }
        log.log(Level.TRACE,"Message \"{}\" will be printed", str);
        return str;
    }

    protected String getMessageForCurrentTime() {
        LocalDateTime today = LocalDateTime.now();
        int hour = today.getHour();
        return choseGreetingMessage(hour);
    }


}