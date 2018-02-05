package by.tr.web.service.factory;

import by.tr.web.service.MovieService;
import by.tr.web.service.ShowBuildService;
import by.tr.web.service.ShowService;
import by.tr.web.service.TableService;
import by.tr.web.service.TvShowService;
import by.tr.web.service.UserService;
import by.tr.web.service.impl.MovieServiceImpl;
import by.tr.web.service.impl.ShowBuildServiceImpl;
import by.tr.web.service.impl.ShowServiceImpl;
import by.tr.web.service.impl.TableServiceImpl;
import by.tr.web.service.impl.TvShowServiceImpl;
import by.tr.web.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private MovieService movieService = new MovieServiceImpl();
    private UserService userService = new UserServiceImpl();
    private ShowService showService = new ShowServiceImpl();
    private TableService tableService =  new TableServiceImpl();
    private TvShowService tvShowService = new TvShowServiceImpl();
    private ShowBuildService showBuildService = new ShowBuildServiceImpl();
    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public ShowService getShowService() {
        return showService;
    }

    public TableService getTableService() {
        return tableService;
    }

    public TvShowService getTvShowService() {
        return tvShowService;
    }

    public ShowBuildService getShowBuildService() {
        return showBuildService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
