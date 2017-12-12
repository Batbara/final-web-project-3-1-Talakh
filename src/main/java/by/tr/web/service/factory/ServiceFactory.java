package by.tr.web.service.factory;

import by.tr.web.service.ServletService;
import by.tr.web.service.UserService;
import by.tr.web.service.impl.ServletServiceImpl;
import by.tr.web.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();


    private UserService userService = new UserServiceImpl();
    private ServletService servletService = new ServletServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public ServletService getServletService() {
        return servletService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
