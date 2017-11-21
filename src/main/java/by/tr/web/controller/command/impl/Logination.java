package by.tr.web.controller.command.impl;

import by.tr.web.controller.Parameter;
import by.tr.web.controller.Path;
import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.IncorrectPasswordException;
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
            session.setAttribute(Parameter.USER_STATUS,user.getUserStatus());
            session.setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));

            response.sendRedirect(Path.SHOW_ACCOUNT_QUERY);
        } catch (NoSuchUserException e) {
            request.setAttribute(Parameter.LOGIN_ERROR, Parameter.LOGIN);
            request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));

            request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request,response);
        } catch (IncorrectPasswordException e) {
            request.setAttribute(Parameter.LOGIN_ERROR, Parameter.PASSWORD);
            request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));

            request.getRequestDispatcher(Path.LOGIN_PAGE).forward(request,response);
        } catch (UserServiceException ex) {
            response.sendRedirect(Path.INDEX);
        }

    }
}
