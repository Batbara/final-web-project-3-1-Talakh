package by.tr.web.controller.command.user;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.exception.InvalidLoginException;
import by.tr.web.service.user.exception.InvalidPasswordException;
import by.tr.web.service.user.exception.NoSuchUserException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginUserImpl implements Command {
    private final static Logger logger = Logger.getLogger(LoginUserImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        response.setContentType(FrontControllerParameter.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        Map<String, String> dataToSend = new HashMap<>();
        try {
            String login = request.getParameter(JspAttribute.LOGIN);
            String password = request.getParameter(JspAttribute.PASSWORD);
            String lang = RequestUtil.getLanguage(request);
            User user = userService.login(login, password, lang);

            if (isBanned(user)) {
                String banInformation = TypeFormatUtil.translateBanInfo(user.getBanInfo(), lang);
                dataToSend.put(FrontControllerParameter.USER_IS_BANNED, banInformation);

                String jsonResponse = gson.toJson(dataToSend);
                out.write(jsonResponse);
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute(JspAttribute.USER, user);

                String address = RequestUtil.formRedirectAddress(request);

                dataToSend.put(FrontControllerParameter.REDIRECT_COMMAND, address);
                String jsonResponse = gson.toJson(dataToSend);
                out.write(jsonResponse);
            }
        } catch (InvalidLoginException ex) {
            logger.error("Invalid login", ex);
            writeErrorMessage(request, out, gson, LocalizationPropertyKey.INVALID_LOGIN_ERROR);

        } catch (NoSuchUserException e) {
            logger.error("No such user", e);
            writeErrorMessage(request, out, gson, LocalizationPropertyKey.NO_SUCH_USER_ERROR);
        } catch (InvalidPasswordException e) {
            logger.error("Incorrect password", e);
            writeErrorMessage(request, out, gson, LocalizationPropertyKey.INCORRECT_PASSWORD_ERROR);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private boolean isBanned(User user) {
        return user.getIsBanned();
    }

    private void writeErrorMessage(HttpServletRequest request, PrintWriter out, Gson gson, String errorType) {
        String lang = RequestUtil.getLanguage(request);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALIZATION_BUNDLE_NAME,
                Locale.forLanguageTag(lang));

        String errorMessage = resourceBundle.getString(errorType);

        Map<String, String> dataToSend = new HashMap<>();
        dataToSend.put(FrontControllerParameter.LOGIN_ERROR, errorMessage);

        String jsonResponse = gson.toJson(dataToSend);
        out.write(jsonResponse);
    }
}
