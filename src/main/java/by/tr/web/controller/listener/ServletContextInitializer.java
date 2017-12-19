package by.tr.web.controller.listener;

import by.tr.web.controller.command.CommandProvider;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.exception.controller.CommandProviderError;
import by.tr.web.exception.controller.CommandProviderException;
import by.tr.web.exception.controller.ConnectionPoolSevereError;
import by.tr.web.exception.dao.ConnectionPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextInitializer implements ServletContextListener {

    public ServletContextInitializer() {
    }

    public void contextInitialized(ServletContextEvent sce) {

        initializeConnectionPool();
        initializeCommandProvider();
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void initializeConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new ConnectionPoolSevereError("Failed to init connection pool", e);
        }
    }

    private void initializeCommandProvider() {
        CommandProvider commandProvider = CommandProvider.getInstance();
        try {
            commandProvider.initCommandProvider();
        } catch (CommandProviderException e) {
            throw new CommandProviderError("Failed to init command provider", e);
        }
    }

}
