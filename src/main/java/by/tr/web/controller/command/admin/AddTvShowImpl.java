package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.Show;
import by.tr.web.domain.TvShow;
import by.tr.web.domain.builder.ShowBuilder;
import by.tr.web.domain.builder.TvShowBuilder;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.show.ShowAlreadyExistsException;
import by.tr.web.service.show.ShowBuildService;
import by.tr.web.service.tv_show.TvShowService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AddTvShowImpl implements Command {

    private static final Logger logger = Logger.getLogger(AddTvShowImpl.class);
    private static final String TV_SHOW_REDIRECT_PATH = "/mpb?command=take_tv_show&showId=%d";

    /**
     * Command to create new instance of {@link TvShow} and add to data base
     * <p>
     * Method retrieves all needed data from request, validates it and tries to add it to data base.
     * In case of success, link to added  {@link TvShow} is written to response during AJAX call.
     * If an error occurs, an appropriate message will be written to response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TvShowService tvShowService = ServiceFactory.getInstance().getTvShowService();

        response.setContentType(FrontControllerParameter.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        Map<String, String> dataToSend = new HashMap<>();
        try {

            ShowBuildService buildService = ServiceFactory.getInstance().getShowBuildService();

            ShowBuilder showBuilder = new TvShowBuilder();
            showBuilder = buildService.retrieveBasicShowInfo(request, showBuilder);

            TvShowBuilder tvShowBuilder = buildService.retrieveTvShowInfo(request, (TvShowBuilder) showBuilder);
            TvShow tvShowEnglish = tvShowBuilder.create();

            ShowBuilder translationShowBuilder = buildService.retrieveShowInfoTranslation(request, tvShowBuilder,
                    DataTypeValidator.Language.RU);
            Show russianTranslation = translationShowBuilder.create();

            int tvShowId = tvShowService.addTvShow(tvShowEnglish, russianTranslation);
            String address = String.format(TV_SHOW_REDIRECT_PATH, tvShowId);

            dataToSend.put(FrontControllerParameter.REDIRECT_COMMAND, address);

            String jsonResponse = gson.toJson(dataToSend);

            out.write(jsonResponse);
        } catch (ShowAlreadyExistsException e) {
            logger.error("Tv-show already exists", e);

            String lang = RequestUtil.getLanguage(request);
            ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALIZATION_BUNDLE_NAME,
                    Locale.forLanguageTag(lang));

            String errorMessage = resourceBundle.getString(LocalizationPropertyKey.SHOW_ALREADY_EXISTS);
            dataToSend.put(FrontControllerParameter.ADD_SHOW_ERROR, errorMessage);
            String jsonResponse = gson.toJson(dataToSend);
            out.write(jsonResponse);

        } catch (ServiceException e) {
            logger.error("Cannot add new tv-show", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
