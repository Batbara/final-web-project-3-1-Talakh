package by.tr.web.controller.command.impl;

import by.tr.web.controller.Parameter;
import by.tr.web.controller.Path;
import by.tr.web.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));
        String address = request.getParameter(Parameter.ADDRESS);
        String query = request.getParameter("query");
        StringBuilder addressConstructor = new StringBuilder();
        addressConstructor.append(address);
        if(!query.isEmpty()){
            addressConstructor.append("?");
            addressConstructor.append(query);
        }
        response.sendRedirect(addressConstructor.toString());
    }
}
