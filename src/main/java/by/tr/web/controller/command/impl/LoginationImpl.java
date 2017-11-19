package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginationImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        try {
            userService.login(login, password);
        } catch (UserServiceException ex){
            response.sendRedirect("jsp/index.jsp");
        }
        response.sendRedirect("/mpb?command=showAccount");
    }
}
