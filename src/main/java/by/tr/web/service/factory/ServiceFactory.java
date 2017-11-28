package by.tr.web.service.factory;

import by.tr.web.service.AppService;
import by.tr.web.service.UserService;
import by.tr.web.service.impl.AppServiceImpl;
import by.tr.web.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();


    private UserService userService = new UserServiceImpl();
    private AppService appService = new AppServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public AppService getAppService() {
        return appService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
