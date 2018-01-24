package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.cookie.CookieManager;
import by.tr.web.cookie.CookieName;
import by.tr.web.domain.Movie;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.CountingServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.TableService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeMovieListImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeMovieListImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        MovieService movieService = ServiceFactory.getInstance().getMovieService();
        TableService tableService = ServiceFactory.getInstance().getTableService();

        try {

            int numberOfRecords = movieService.countMovie();

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.MOVIE_CURRENT_PAGE);
            int currentPage = tableService.takeCurrentPage(request);
            request.setAttribute(TableParameter.PAGE, currentPage);

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.RECORDS_ON_MOVIE_PAGE);
            request.setAttribute(FrontControllerParameter.DEFAULT_RECORDS_ON_PAGE,5);
            int recordsOnPage = tableService.takeRecordsOnPage(request);
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.MOVIE_ORDER);
            String orderType = tableService.takeMovieOrderType(request);
            request.setAttribute(TableParameter.ORDER, orderType);

            int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentPage, numberOfRecords);

            int startRecordNum = (currentPage - 1) * recordsOnPage;
            String lang = Util.getLanguage(request);
            List<Movie> movies = movieService.takeOrderedMovieList(startRecordNum, recordsToTake, orderType, lang);
            request.setAttribute(JspAttribute.MOVIES, movies);

            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            setCookies(request,response, orderType, currentPage, recordsOnPage);

            request.removeAttribute(FrontControllerParameter.COOKIE_NAME);
            request.getRequestDispatcher(JspPagePath.MOVIE_LIST_PAGE).forward(request, response);

        } catch (CountingServiceException e) {
            logger.error("Error while counting movies in DB", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           // request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while getting movie list", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }

    }

    private void setCookies(HttpServletRequest request, HttpServletResponse response,
                            String orderType, int currentPage, int recordsOnPage) {

        CookieManager cookieManager = new CookieManager(request);
        response.addCookie(cookieManager.makeCookie(CookieName.MOVIE_ORDER, orderType));

        String currentPageString = Integer.toString(currentPage);
        response.addCookie(cookieManager.makeCookie(CookieName.MOVIE_CURRENT_PAGE, currentPageString));

        String recordsOnPageString = Integer.toString(recordsOnPage);
        response.addCookie(cookieManager.makeCookie(CookieName.RECORDS_ON_MOVIE_PAGE, recordsOnPageString));
    }

}
