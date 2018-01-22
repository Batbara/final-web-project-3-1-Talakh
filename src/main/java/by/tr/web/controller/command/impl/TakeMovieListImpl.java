package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.TakeListCommand;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.controller.cookie.CookieManager;
import by.tr.web.controller.cookie.CookieName;
import by.tr.web.controller.util.Util;
import by.tr.web.domain.Movie;
import by.tr.web.exception.controller.CookieNotFoundException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.movie.CountingMoviesException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.validation.MovieValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeMovieListImpl implements TakeListCommand {
    private static final Logger logger = Logger.getLogger(TakeMovieListImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CookieManager cookieManager = new CookieManager(request);
        int defaultRecordsOnPage = 5;

        int currentPage;
        String orderType;
        int recordsOnPage;

        try {
            currentPage = getCurrentPage(cookieManager, request, CookieName.MOVIE_CURRENT_PAGE);
            orderType = getOrderType(cookieManager, request);
            recordsOnPage = getRecordsOnPage(cookieManager, request, defaultRecordsOnPage, CookieName.RECORDS_ON_MOVIE_PAGE);
        } catch (CookieNotFoundException e) {
            logger.error("Cannot find cookie in request", e);
            currentPage = 1;
            orderType = MovieValidator.OrderType.TITLE.toString().toLowerCase();
            recordsOnPage = defaultRecordsOnPage;
        }

        String lang = Util.getLanguage(request);

        int startRecordNum = (currentPage - 1) * recordsOnPage;

        MovieService movieService = ServiceFactory.getInstance().getMovieService();
        int numberOfRecords;

        try {
            numberOfRecords = movieService.countShow();

            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            int recordsToTake = Util.calcTableRecordsToTake(recordsOnPage, currentPage, numberOfRecords);

            List<Movie> movies = movieService.takeOrderedMovieList(startRecordNum, recordsToTake, orderType, lang);

            request.setAttribute(TableParameter.ORDER, orderType);
            request.setAttribute(TableParameter.PAGE, currentPage);
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);
            request.setAttribute(JspAttribute.MOVIES, movies);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            setCookies(response, cookieManager, orderType, currentPage, recordsOnPage);
            request.getRequestDispatcher(JspPagePath.MOVIE_LIST_PAGE).forward(request, response);

        } catch (CountingMoviesException e) {
            logger.error("Error while counting movies in DB", e);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while getting movie list", e);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }

    }

    private void setCookies(HttpServletResponse response, CookieManager cookieManager,
                            String orderType, int currentPage, int recordsOnPage) {
        response.addCookie(cookieManager.makeCookie(CookieName.MOVIE_ORDER, orderType));

        String currentPageString = Integer.toString(currentPage);
        response.addCookie(cookieManager.makeCookie(CookieName.MOVIE_CURRENT_PAGE, currentPageString));

        String recordsOnPageString = Integer.toString(recordsOnPage);
        response.addCookie(cookieManager.makeCookie(CookieName.RECORDS_ON_MOVIE_PAGE, recordsOnPageString));
    }

    private String getOrderType(CookieManager cookieManager, HttpServletRequest request) throws CookieNotFoundException {

        String orderType = request.getParameter(TableParameter.ORDER);

        if (orderType != null) {
            return orderType;
        }

        if (cookieManager.isCookieInRequest(CookieName.MOVIE_ORDER)) {
            return cookieManager.takeCookieValue(CookieName.MOVIE_ORDER);
        }

        return MovieValidator.OrderType.TITLE.toString().toLowerCase();
    }

}
