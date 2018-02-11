package by.tr.web.controller.command.user;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageImpl implements Command {
    /**
     * Command to change application language.
     *
     * <p>
     * Method sets locale, chosen by client, as attribute to request and redirects back to calling address.
     * Supported languages are specified in {@link FrontControllerParameter.Language}
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentLocale = request.getParameter(FrontControllerParameter.LOCALE);
        request.getSession().setAttribute(FrontControllerParameter.LOCALE, currentLocale);
        String address = RequestUtil.formRedirectAddress(request);
        response.sendRedirect(address);
    }

}
