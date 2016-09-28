package hello_module.server;

/**
 * Created by Saniye on 27.09.16.
 */

import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MyRemoteLoggingServlet extends RemoteServiceServlet implements RemoteLoggingService {

    private static final long serialVersionUID = 1L;
    private static Logger rootLogger = LogManager.getLogger("remote_logger");

    @Override
    public String logOnServer(LogRecord record) {
        Level level = record.getLevel();
        String message = record.getMessage();
        if (Level.INFO.equals(level)) {
            rootLogger.info(message);
        } else if (Level.SEVERE.equals(level)) {
            rootLogger.error(message);
        } else if (Level.WARNING.equals(level)) {
            rootLogger.warn(message);
        } else if (Level.FINE.equals(level)) {
            rootLogger.debug(message);
        }
        return null;
    }

}
