package by.tr.web.controller.command.impl;

import by.tr.web.controller.FrontControllerParameter;
import by.tr.web.controller.JSPPagePath;
import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.InvalidLoginException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.exception.service.user.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logination implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter(FrontControllerParameter.LOGIN);
        String password = request.getParameter(FrontControllerParameter.PASSWORD);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user;
        try {
            user = userService.login(login, password);

            HttpSession session = request.getSession(true);

            session.setAttribute(FrontControllerParameter.USER, user);
            session.setAttribute(FrontControllerParameter.USER_STATUS, user.getUserStatus());

            response.sendRedirect(JSPPagePath.USER_ACCOUNT_PATH);
        } catch (InvalidLoginException ex) {
            showErrorMessage(request, response, FrontControllerParameter.LOGIN);
        }catch (NoSuchUserException e) {
            showErrorMessage(request, response, FrontControllerParameter.USER);
        } catch (IncorrectPasswordException e ) {
            showErrorMessage(request, response, FrontControllerParameter.PASSWORD);
        } catch (UserServiceException ex) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE);
            dispatcher.forward(request,response);
        }

    }

    private void showErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(FrontControllerParameter.LOGIN_ERROR, errorType);
        request.getRequestDispatcher(JSPPagePath.LOGIN_PAGE).forward(request, response);
    }
}
