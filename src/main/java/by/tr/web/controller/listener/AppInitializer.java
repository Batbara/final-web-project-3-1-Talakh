package by.tr.web.controller.listener;

import by.tr.web.exception.service.ApplicationServiceException;
import by.tr.web.service.AppService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppInitializer implements ServletContextListener {

    public AppInitializer() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AppService appService = serviceFactory.getAppService();
        try {
            appService.initResources();
        } catch (ApplicationServiceException e) {
           System.err.println("Failed to get connection pool");
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
