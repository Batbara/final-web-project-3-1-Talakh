package by.tr.web.controller.command.impl;

import by.tr.web.controller.Parameter;
import by.tr.web.controller.Path;
import by.tr.web.controller.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAccountImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.USER_ACCOUNT_PATH);
        dispatcher.forward(request,response);
    }
}
