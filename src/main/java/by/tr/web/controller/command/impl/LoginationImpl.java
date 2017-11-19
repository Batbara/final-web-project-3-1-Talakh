package by.tr.web.controller.command.impl;

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

public class LoginationImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        User user;
        try {
            user = userService.login(login, password);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            response.sendRedirect("/mpb?command=showAccount");
        } catch (NoSuchUserException e) {
            request.getSession().setAttribute("loginError", "No such user!");
            response.sendRedirect("jsp/login.jsp");
        } catch (IncorrectPasswordException e) {
            request.getSession().setAttribute("loginError", "Incorrect password!");
            response.sendRedirect("jsp/login.jsp");
        } catch (UserServiceException ex) {
            response.sendRedirect("/index.jsp");
        }

    }
}
