package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.domain.Movie;
import by.tr.web.domain.Show;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.ShowBuilder;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.MovieService;
import by.tr.web.service.ShowBuildService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.validation.DataTypeValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddMovie implements Command {
    private static final Logger logger = Logger.getLogger(AddMovie.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        MovieService movieService = ServiceFactory.getInstance().getMovieService();


        /*response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();*/

        try {

            ShowBuildService buildService = ServiceFactory.getInstance().getShowBuildService();

            ShowBuilder showBuilder = new MovieBuilder();
            showBuilder = buildService.retrieveBasicShowInfo(request, showBuilder);

            MovieBuilder movieBuilder =  buildService.retrieveMovieInfo(request, (MovieBuilder)showBuilder);
            Movie englishMovie = movieBuilder.create();


            ShowBuilder translationShowBuilder = buildService.retrieveShowInfoTranslation(request, new MovieBuilder(),
                    DataTypeValidator.Language.RU);
            Show russianTranslation = translationShowBuilder.create();

            int movieId = movieService.addMovie(englishMovie, russianTranslation);
            String address = "/mpb?command=take_movie&showId="+Integer.toString(movieId);
            request.getRequestDispatcher(address).forward(request, response);
            //out.print(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ServiceException e) {
            logger.error("Cannot add new movie", e);
           // out.print(FrontControllerParameter.FAILURE_RESPONSE);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }
    }

}
