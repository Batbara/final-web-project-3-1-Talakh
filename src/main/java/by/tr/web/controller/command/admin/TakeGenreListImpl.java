package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.Genre;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.show.ShowService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeGenreListImpl implements Command{
    private static final Logger logger = Logger.getLogger(TakeGenreListImpl.class);

    /**
     * Command to take genre list from data base
     * <p>
     * Method tries to retrieve {@link List} of all films/tv-shows genres stored in data base
     * accordingly to chosen locale.
     * In case of success, {@link List} is converted to {@link Gson} object and written to response during AJAX call.
     * If an error occurs, {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR} will be sent.
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ShowService showService = ServiceFactory.getInstance().getShowService();
        String lang = RequestUtil.getLanguage(request);
        try{
            List<Genre> genreList = showService.takeGenreList(lang);

            String genreListJson = new Gson().toJson(genreList);

            response.setContentType(FrontControllerParameter.JSON_CONTENT_TYPE);
            response.setCharacterEncoding(FrontControllerParameter.DEFAULT_ENCODING);

            response.getWriter().write(genreListJson);
        } catch (ServiceException e) {
            logger.error("Cannot add genre list to response", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
