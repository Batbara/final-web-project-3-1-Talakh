package by.tr.web.listener;

import by.tr.web.controller.command.command_provider.CommandProvider;
import by.tr.web.controller.command.command_provider.CommandProviderError;
import by.tr.web.controller.command.command_provider.CommandProviderException;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.exception.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ServletContextInitializer implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(ServletContextInitializer.class);
    public ServletContextInitializer() {
    }

    public void contextInitialized(ServletContextEvent contextEvent) {

        initializeConnectionPool();
        initializeCommandProvider();
        initializeLogger(contextEvent);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        destroyConnectionPool();
    }

    private void initializeLogger(ServletContextEvent contextEvent){
        ServletContext context = contextEvent.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);
    }
    private void initializeConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, "Failed to init connection pool", e);
            throw new ConnectionPoolSevereError("Failed to init connection pool", e);
        }
    }

    private void initializeCommandProvider() {
        CommandProvider commandProvider = CommandProvider.getInstance();
        try {
            commandProvider.initCommandProvider();
        } catch (CommandProviderException e) {
            logger.log(Level.FATAL, "Failed to init command provider", e);
            throw new CommandProviderError("Failed to init command provider", e);
        }
    }
    private void destroyConnectionPool(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
    }

}
