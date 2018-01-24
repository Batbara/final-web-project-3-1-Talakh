package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.domain.Movie;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeMovieImpl implements Command {

    private final static Logger logger = Logger.getLogger(TakeMovieImpl.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int movieID = Integer.parseInt(request.getParameter("id"));

        String lang = Util.getLanguage(request);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        try {
            Movie movie = movieService.takeMovie(movieID, lang);

            request.setAttribute("movie", movie);
            request.getRequestDispatcher("/m").forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while taking movie", e);
           // request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
