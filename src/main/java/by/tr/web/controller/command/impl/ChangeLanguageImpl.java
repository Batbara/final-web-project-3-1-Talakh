package by.tr.web.controller.command.impl;

import by.tr.web.controller.FrontControllerParameter;
import by.tr.web.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentLocale = request.getParameter(FrontControllerParameter.LOCALE);
        request.getSession().setAttribute(FrontControllerParameter.LOCALE, currentLocale);
        String address = formRedirectAddress(request);
        response.sendRedirect(address);
    }
    private String formRedirectAddress(HttpServletRequest request){
        String address = request.getParameter(FrontControllerParameter.ADDRESS);
        String query = request.getParameter(FrontControllerParameter.QUERY);
        StringBuilder addressConstructor = new StringBuilder();
        addressConstructor.append(address);
        if(!query.isEmpty()){
            addressConstructor.append("?");
            addressConstructor.append(query);
        }
        return addressConstructor.toString();
    }
}
