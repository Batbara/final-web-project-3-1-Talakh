package by.tr.web.controller.command.impl;

import by.tr.web.controller.Parameter;
import by.tr.web.controller.Path;
import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));

        String login = request.getParameter(Parameter.LOGIN);
        String password = request.getParameter(Parameter.PASSWORD);
        String eMail = request.getParameter(Parameter.EMAIL);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User newUser;
        try {
            newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute(Parameter.USER, newUser);
            session.setAttribute(Parameter.USER_STATUS, newUser.getUserStatus());
            session.setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));

            response.sendRedirect(Path.SHOW_ACCOUNT_QUERY);
        } catch (UserServiceException ex) {
            showRegisterError(request, response);
        }

    }

    private void showRegisterError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(Parameter.REGISTER_ERROR, Parameter.LOGIN);
        request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));
        request.getRequestDispatcher(Path.REGISTER_PAGE).forward(request, response);
    }
}
