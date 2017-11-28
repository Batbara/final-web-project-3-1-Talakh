package by.tr.web.controller.command.impl;

import by.tr.web.controller.Parameter;
import by.tr.web.controller.Path;
import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.service.InvalidLoginException;
import by.tr.web.exception.service.NoSuchUserException;
import by.tr.web.exception.service.UserServiceException;
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
        String login = request.getParameter(Parameter.LOGIN);
        String password = request.getParameter(Parameter.PASSWORD);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user;
        try {
            user = userService.login(login, password);

            HttpSession session = request.getSession(true);

            session.setAttribute(Parameter.USER, user);
            session.setAttribute(Parameter.USER_STATUS, user.getUserStatus());

            response.sendRedirect(Path.USER_ACCOUNT_PATH);
        } catch (InvalidLoginException ex) {
            showErrorMessage(request, response, Parameter.LOGIN);
        }catch (NoSuchUserException e) {
            showErrorMessage(request, response, Parameter.USER);
        } catch (IncorrectPasswordException e ) {
            showErrorMessage(request, response, Parameter.PASSWORD);
        } catch (UserServiceException ex) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(Path.INTERNAL_ERROR_PAGE);
            dispatcher.forward(request,response);
        }

    }

    private void showErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(Parameter.LOGIN_ERROR, errorType);
        request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request, response);
    }
}
