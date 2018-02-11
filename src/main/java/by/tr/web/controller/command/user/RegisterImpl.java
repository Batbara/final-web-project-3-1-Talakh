package by.tr.web.controller.command.user;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.exception.EmailAlreadyRegisteredException;
import by.tr.web.service.user.exception.InvalidEmailException;
import by.tr.web.service.user.exception.InvalidLoginException;
import by.tr.web.service.user.exception.InvalidPasswordException;
import by.tr.web.service.user.exception.UserAlreadyExistsException;
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

public class RegisterImpl implements Command {
    private final static Logger logger = Logger.getLogger(RegisterImpl.class);
    /**
     * Command to register new {@link User} in system.
     * <p>
     * Method retrieves all needed data from request, validates it and tries to login specified user.
     * In case of success, new {@link User} is added to data base and {@link HttpSession} is created for him.
     * Request address is written to response during AJAX call.
     * If an error occurs, an appropriate message will be written to response or {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR}
     * will be sent.
     *</p>
     **/
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String login = request.getParameter(JspAttribute.LOGIN);
        String password = request.getParameter(JspAttribute.PASSWORD);
        String eMail = request.getParameter(JspAttribute.EMAIL);

        response.setContentType(FrontControllerParameter.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();


        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        Map<String, String> dataToSend = new HashMap<>();

        try {

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            User newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute(JspAttribute.USER, newUser);

            dataToSend.put(FrontControllerParameter.REDIRECT_COMMAND, JspPagePath.USER_ACCOUNT_PATH);
            String jsonResponse = gson.toJson(dataToSend);
            out.write(jsonResponse);

        } catch (InvalidPasswordException ex) {
            logger.error("Incorrect password", ex);

            writeErrorMessage(request, out, gson, LocalizationPropertyKey.INVALID_PASSWORD);
        } catch (InvalidLoginException ex) {
            logger.error("Invalid login " + login, ex);

            writeErrorMessage(request, out, gson, LocalizationPropertyKey.INVALID_LOGIN_ERROR);
        } catch (InvalidEmailException ex) {
            logger.error("Invalid e-mail " + eMail, ex);
            writeErrorMessage(request, out, gson, LocalizationPropertyKey.INVALID_EMAIL);
        } catch (EmailAlreadyRegisteredException ex) {
            logger.error("E-mail " + eMail + " already registered", ex);
            writeErrorMessage(request, out, gson, LocalizationPropertyKey.EMAIL_ALREADY_EXISTS_ERROR);
        } catch (UserAlreadyExistsException ex) {
            logger.error("Trying to register already registered user", ex);
            writeErrorMessage(request, out, gson, LocalizationPropertyKey.USER_ALREADY_EXISTS_ERROR);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "Internal error", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Writes localized register error message to response as JSON object.
     *
     * @param request
     * Request object
     * @param out
     * Response write stream
     * @param gson
     * Gson object {@see https://github.com/google/gson} to convert error messages to JSON representation
     * @param errorType
     * Key to localized message with error
     */
    private void writeErrorMessage(HttpServletRequest request, PrintWriter out, Gson gson, String errorType) {
        String lang = RequestUtil.getLanguage(request);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALIZATION_BUNDLE_NAME,
                Locale.forLanguageTag(lang));

        String errorMessage = resourceBundle.getString(errorType);

        Map<String, String> dataToSend = new HashMap<>();
        dataToSend.put(FrontControllerParameter.REGISTER_ERROR, errorMessage);

        String jsonResponse = gson.toJson(dataToSend);
        out.write(jsonResponse);
    }
}
