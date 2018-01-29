package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.domain.Movie;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.ShowService;
import by.tr.web.service.TableService;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class TakeMovieImpl implements Command {

    private final static Logger logger = Logger.getLogger(TakeMovieImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String movieID = request.getParameter(JspAttribute.SHOW_ID);

        String lang = Util.getLanguage(request);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        ShowService showService = serviceFactory.getShowService();
        try {


            Movie movie = movieService.takeMovie(movieID, lang);
            double movieRating = showService.takeShowRating(movieID);
            movie.setUserRating(movieRating);

            int numberOfReviews = showService.countReviews(movieID);

            if (numberOfReviews != 0) {
                addReviewList(request, numberOfReviews, movie);
            }
            request.setAttribute(JspAttribute.SHOW_REVIEWS_NUMBER, numberOfReviews);
            request.setAttribute(JspAttribute.SHOW, movie);

            updateUserReviewList(request);

            request.getRequestDispatcher("/m").forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while taking movie", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private void addReviewList(HttpServletRequest request, int numberOfReviews, Movie movie)
            throws ServiceException {
        ShowService showService = ServiceFactory.getInstance().getShowService();
        TableService tableService = ServiceFactory.getInstance().getTableService();

        int currentReviewPage = tableService.takeCurrentPage(request);
        request.setAttribute(TableParameter.PAGE, currentReviewPage);

        int recordsOnPage = 5;
        int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentReviewPage, numberOfReviews);

        String reviewStatus = request.getParameter(JspAttribute.REVIEW_STATUS);

        int startRecordNum = (currentReviewPage - 1) * recordsOnPage;

        List<UserReview> movieReviews = showService.takeReviewList(startRecordNum, recordsToTake,
                reviewStatus, movie.getShowID());

        movie.setReviewList(movieReviews);
        int numOfPages = (int) Math.ceil((double) numberOfReviews / recordsOnPage);
        request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);
    }

    private void updateUserReviewList(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        if (session.getAttribute(JspAttribute.USER) == null) {
            return;
        }
        User user = (User) session.getAttribute(JspAttribute.USER);

        UserService userService = ServiceFactory.getInstance().getUserService();
        User updatedUser = userService.updateReviewList(user);

        session.setAttribute(JspAttribute.USER, updatedUser);
    }
}
