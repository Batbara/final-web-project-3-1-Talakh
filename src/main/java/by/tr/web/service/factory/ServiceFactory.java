package by.tr.web.service.factory;

import by.tr.web.service.MovieService;
import by.tr.web.service.UserService;
import by.tr.web.service.impl.MovieServiceImpl;
import by.tr.web.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private MovieService movieService = new MovieServiceImpl();
    private UserService userService = new UserServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
