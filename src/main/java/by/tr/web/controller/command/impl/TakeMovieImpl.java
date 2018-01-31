package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.Movie;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.ShowService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeMovieImpl implements Command {

    private final static Logger logger = Logger.getLogger(TakeMovieImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String movieID = request.getParameter(JspAttribute.SHOW_ID);

        String lang = RequestUtil.getLanguage(request);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        ShowService showService = serviceFactory.getShowService();
        try {


            Movie movie = movieService.takeMovie(movieID, lang);
            double movieRating = showService.takeShowRating(movieID);
            movie.setUserRating(movieRating);

            int numberOfReviews = showService.countShowReviews(movieID);

            if (numberOfReviews != 0) {
                List<UserReview> userReviews = RequestUtil.takeReviewListForShow(request, numberOfReviews, movie.getShowID());
                movie.setReviewList(userReviews);
            }
            request.setAttribute(JspAttribute.REVIEWS_NUMBER, numberOfReviews);
            request.setAttribute(JspAttribute.SHOW, movie);

            RequestUtil.updateUserReviewList(request);

            request.getRequestDispatcher(JspPagePath.MOVIE_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while taking movie", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
