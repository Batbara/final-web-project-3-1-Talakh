package by.tr.web.controller.command.impl.user_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.EmailAlreadyRegisteredException;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidEmailException;
import by.tr.web.exception.service.user.InvalidLoginException;
import by.tr.web.exception.service.user.UserAlreadyExistsException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationImpl implements Command {
    private final static Logger logger = Logger.getLogger(RegistrationImpl.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = request.getParameter(JspAttribute.LOGIN);
        String password = request.getParameter(JspAttribute.PASSWORD);
        String eMail = request.getParameter(JspAttribute.EMAIL);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User newUser;
        try {
            newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute(JspAttribute.USER, newUser);

            response.sendRedirect(JspPagePath.USER_ACCOUNT_PATH);
        } catch (IncorrectPasswordException ex) {
            logger.error("Incorrect password", ex);
            showRegisterError(request, response, JspAttribute.PASSWORD);
        } catch (InvalidLoginException ex) {
            logger.error("Invalid login",ex);
            showRegisterError(request, response, JspAttribute.LOGIN);
        } catch (InvalidEmailException | EmailAlreadyRegisteredException ex) {
            logger.error("E-mail error", ex);
            showRegisterError(request, response, JspAttribute.EMAIL);
        } catch (UserAlreadyExistsException ex) {
            logger.error("User already exists", ex);
            showRegisterError(request, response, JspAttribute.USER);
        } catch (ServiceException e){
            logger.log(Level.FATAL, "Internal error", e);
           /* RequestDispatcher dispatcher = request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE);
            dispatcher.forward(request, response);*/
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private void showRegisterError(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(FrontControllerParameter.REGISTER_ERROR, errorType);
        request.getRequestDispatcher(JspPagePath.REGISTER_PAGE).forward(request, response);
    }
}
