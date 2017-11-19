package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationImpl implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String eMail = request.getParameter("eMail");


        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        User newUser;
        try {
            newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute("user",newUser);
            response.sendRedirect("/mpb?command=showAccount");
        } catch (UserServiceException ex){

            request.getSession().setAttribute("registerError","Such user already exists!");
            response.sendRedirect("jsp/register.jsp");
        }

    }
}
