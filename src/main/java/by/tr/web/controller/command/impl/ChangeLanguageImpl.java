package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JSPPagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

@XmlRootElement(name = "ChangeLanguageImpl")
@XmlAccessorType(XmlAccessType.FIELD)
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
        if(!query.isEmpty()){
            addressConstructor.append(JSPPagePath.FRONT_CONTROLLER);
            addressConstructor.append("?");
            addressConstructor.append(query);
        } else {
            addressConstructor.append(address);
        }
        return addressConstructor.toString();
    }
}
