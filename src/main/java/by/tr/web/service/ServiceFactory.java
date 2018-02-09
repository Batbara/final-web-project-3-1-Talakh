package by.tr.web.service;

import by.tr.web.service.movie.MovieService;
import by.tr.web.service.movie.MovieServiceImpl;
import by.tr.web.service.show.ShowBuildService;
import by.tr.web.service.show.ShowBuildServiceImpl;
import by.tr.web.service.show.ShowService;
import by.tr.web.service.show.ShowServiceImpl;
import by.tr.web.service.show.review.ReviewBuildService;
import by.tr.web.service.show.review.ReviewBuildServiceImpl;
import by.tr.web.service.show.review.ReviewService;
import by.tr.web.service.show.review.ReviewServiceImpl;
import by.tr.web.service.table.TableService;
import by.tr.web.service.table.TableServiceImpl;
import by.tr.web.service.tv_show.TvShowService;
import by.tr.web.service.tv_show.TvShowServiceImpl;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private MovieService movieService = new MovieServiceImpl();
    private UserService userService = new UserServiceImpl();
    private ShowService showService = new ShowServiceImpl();
    private TableService tableService =  new TableServiceImpl();
    private TvShowService tvShowService = new TvShowServiceImpl();
    private ShowBuildService showBuildService = new ShowBuildServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();
    private ReviewBuildService reviewBuildService = new ReviewBuildServiceImpl();
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

    public ReviewService getReviewService() {
        return reviewService;
    }

    public ReviewBuildService getReviewBuildService() {
        return reviewBuildService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
