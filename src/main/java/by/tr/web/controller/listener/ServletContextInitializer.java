package by.tr.web.controller.listener;

import by.tr.web.exception.controller.ConnectionPoolSevereError;
import by.tr.web.exception.service.user.ServletServiceException;
import by.tr.web.service.ServletService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextInitializer implements ServletContextListener {// опять беда с названием

    public ServletContextInitializer() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ServletService servletService = serviceFactory.getServletService();
        try {
            servletService.initResources();
        } catch (ServletServiceException e) {
            throw new ConnectionPoolSevereError("Failed to init connection pool");
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
