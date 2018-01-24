package by.tr.web.controller.command.impl.user_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidLoginException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.util.Util;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginationImpl implements Command {
    private final static Logger logger = Logger.getLogger(LoginationImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter(JspAttribute.LOGIN);
        String password = request.getParameter(JspAttribute.PASSWORD);
        String lang = Util.getLanguage(request);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user;
        try {
            user = userService.login(login, password, lang);

            if (isBanned(user)) {
                request.setAttribute(JspAttribute.USER, user);
                sendErrorMessage(request, response, FrontControllerParameter.USER_IS_BANNED);
            } else {
                HttpSession session = request.getSession(true);

                session.setAttribute(JspAttribute.USER, user);
                response.sendRedirect(JspPagePath.USER_ACCOUNT_PATH);
            }
        } catch (InvalidLoginException ex) {
            logger.error("Invalid login", ex);
            sendErrorMessage(request, response, JspAttribute.LOGIN);
        } catch (NoSuchUserException e) {
            logger.error("No such user", e);
            sendErrorMessage(request, response, JspAttribute.USER);
        } catch (IncorrectPasswordException e) {
            logger.error("Incorrect password", e);
            sendErrorMessage(request, response, JspAttribute.PASSWORD);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           /* RequestDispatcher dispatcher = request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE);
            dispatcher.forward(request, response);*/
        }

    }

    private boolean isBanned(User user) {
        return user.getIsBanned();
    }

    private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(FrontControllerParameter.LOGIN_ERROR, errorType);
        request.getRequestDispatcher(JspPagePath.LOGIN_PAGE).forward(request, response);
    }
}
