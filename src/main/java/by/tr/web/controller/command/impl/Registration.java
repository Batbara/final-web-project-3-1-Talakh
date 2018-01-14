package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JSPPagePath;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.EMailAlreadyRegisteredException;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidEMailException;
import by.tr.web.exception.service.user.InvalidLoginException;
import by.tr.web.exception.service.user.UserAlreadyExistsException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {
    private final static Logger logger = Logger.getLogger(Registration.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = request.getParameter(FrontControllerParameter.LOGIN);
        String password = request.getParameter(FrontControllerParameter.PASSWORD);
        String eMail = request.getParameter(FrontControllerParameter.EMAIL);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User newUser;
        try {
            newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute(FrontControllerParameter.USER, newUser);
            session.setAttribute(FrontControllerParameter.USER_STATUS, newUser.getUserStatus());

            response.sendRedirect(JSPPagePath.USER_ACCOUNT_PATH);
        } catch (IncorrectPasswordException ex) {
            logger.error("Incorrect password", ex);
            showRegisterError(request, response, FrontControllerParameter.PASSWORD);
        } catch (InvalidLoginException ex) {
            logger.error("Invalid login",ex);
            showRegisterError(request, response, FrontControllerParameter.LOGIN);
        } catch (InvalidEMailException | EMailAlreadyRegisteredException ex) {
            logger.error("E-mail error", ex);
            showRegisterError(request, response, FrontControllerParameter.EMAIL);
        } catch (UserAlreadyExistsException ex) {
            logger.error("User already exists", ex);
            showRegisterError(request, response, FrontControllerParameter.USER);
        } catch (ServiceException e){
            logger.log(Level.FATAL, "Internal error", e);
            RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE);
            dispatcher.forward(request, response);
        }

    }

    private void showRegisterError(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(FrontControllerParameter.REGISTER_ERROR, errorType);
        request.getRequestDispatcher(JSPPagePath.REGISTER_PAGE).forward(request, response);
    }
}
