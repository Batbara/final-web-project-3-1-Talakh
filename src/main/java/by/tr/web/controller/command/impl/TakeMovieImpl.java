package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.domain.Movie;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TakeMovieImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int movieID = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();
        String lang = request.getLocale().getLanguage();
        if (session.getAttribute(FrontControllerParameter.LOCALE) != null) {
            lang = (String) session.getAttribute(FrontControllerParameter.LOCALE);
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        try {
            Movie movie = movieService.takeMovie(movieID, lang);

            request.setAttribute("movie", movie);
            request.getRequestDispatcher("/m").forward(request, response);
        } catch (ServiceException e) {
            //TODO: error page
        }

    }
}
