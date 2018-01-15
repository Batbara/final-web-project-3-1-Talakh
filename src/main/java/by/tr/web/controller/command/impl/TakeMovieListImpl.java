package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JSPPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.controller.constant.Util;
import by.tr.web.domain.Movie;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.movie.CountingMoviesException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ServiceFactory;
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

        Integer currentPage = Integer.parseInt(request.getParameter(TableParameter.PAGE));
        String orderType = request.getParameter(TableParameter.ORDER);
        Integer recordsOnPage = Integer.parseInt(request.getParameter(TableParameter.RECORDS_ON_PAGE));

        String lang = Util.getLanguage(request);

        int startRecordNum = (currentPage - 1) * recordsOnPage;

        MovieService movieService = ServiceFactory.getInstance().getMovieService();
        int numberOfRecords;
        try {
            numberOfRecords = movieService.countMovies();

            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            int recordsToTake = Util.calcTableRecordsToTake(recordsOnPage, currentPage, numberOfRecords);

            List<Movie> movies = movieService.takeOrderedMovieList(startRecordNum, recordsToTake, orderType, lang);

            request.setAttribute("order", orderType);
            request.setAttribute("page", currentPage);
            request.setAttribute("onPage", recordsOnPage);
            request.setAttribute("movies", movies);
            request.setAttribute("numOfPages", numOfPages);

            request.getRequestDispatcher(JSPPagePath.MOVIE_LIST_PAGE).forward(request, response);
        } catch (CountingMoviesException e) {
            logger.error("Error while counting movies in DB", e);
            request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while getting movie list", e);
            request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }

    }

}
