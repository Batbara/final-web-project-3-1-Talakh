package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JSPPagePath;
import by.tr.web.controller.constant.MovieParameter;
import by.tr.web.domain.Movie;
import by.tr.web.exception.service.movie.CountingMoviesException;
import by.tr.web.exception.service.movie.MovieServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class TakeMovieListImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeMovieListImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer currentPage = Integer.parseInt(request.getParameter(MovieParameter.PAGE));
        String orderType = request.getParameter(MovieParameter.ORDER);
        Integer recordsOnPage = Integer.parseInt(request.getParameter(MovieParameter.RECORDS_ON_PAGE));

        HttpSession session = request.getSession();
        String lang = request.getLocale().getLanguage();
        if (session.getAttribute(FrontControllerParameter.LOCALE) != null) {
            lang = (String) session.getAttribute(FrontControllerParameter.LOCALE);
        }
        int startID = (currentPage - 1) * recordsOnPage;

        MovieService movieService = ServiceFactory.getInstance().getMovieService();
        int numberOfRecords = 0;
        try {
            numberOfRecords = movieService.countMovies();
            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            if (recordsOnPage*currentPage > startID + recordsOnPage) {
                recordsOnPage = recordsOnPage*currentPage - startID;
            }
            List<Movie> movies = movieService.takeOrderedMovieList(startID, recordsOnPage, orderType, lang);


            request.setAttribute("order", orderType);
            request.setAttribute("page", currentPage);
            request.setAttribute("onPage", recordsOnPage);
            request.setAttribute("movies", movies);
            request.setAttribute("numOfPages", numOfPages);

            request.getRequestDispatcher(JSPPagePath.MOVIE_LIST_PAGE).forward(request, response);
        } catch (CountingMoviesException e) {
            logger.error("Error while counting movies in DB", e);
        } catch (MovieServiceException e) {
            logger.error("Error while getting movie list", e);
        }

    }
}
