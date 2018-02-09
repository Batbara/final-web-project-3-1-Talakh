package by.tr.web.controller.command.user;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentLocale = request.getParameter(FrontControllerParameter.LOCALE);
        request.getSession().setAttribute(FrontControllerParameter.LOCALE, currentLocale);
        String address = RequestUtil.formRedirectAddress(request);
        response.sendRedirect(address);
    }

    /*private String formRedirectAddress(HttpServletRequest request) {
        String address = request.getParameter(JspAttribute.ADDRESS);
        String query = request.getParameter(FrontControllerParameter.QUERY);
        StringBuilder addressConstructor = new StringBuilder();
        if (!query.isEmpty()) {
            addressConstructor.append(JspPagePath.FRONT_CONTROLLER);
            addressConstructor.append("?");
            addressConstructor.append(query);
        } else {
            addressConstructor.append(address);
        }
        return addressConstructor.toString();
    }*/
}
