package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.Country;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.ShowService;
import by.tr.web.service.factory.ServiceFactory;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeCountryList implements Command {
    private static final Logger logger = Logger.getLogger(TakeGenreList.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ShowService showService = ServiceFactory.getInstance().getShowService();
        String lang = RequestUtil.getLanguage(request);
        try{
            List<Country> countryList = showService.takeCountryList(lang);

            String genreListJson = new Gson().toJson(countryList);

            response.setContentType(FrontControllerParameter.JSON_CONTENT_TYPE);
            response.setCharacterEncoding(FrontControllerParameter.DEFAULT_ENCODING);

            response.getWriter().write(genreListJson);
        } catch (ServiceException e) {
            logger.error("Cannot add country list to response", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
