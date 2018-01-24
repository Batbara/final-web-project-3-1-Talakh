package by.tr.web.filter;

import by.tr.web.controller.command.CommandProvider;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessChecker implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String command = req.getParameter(FrontControllerParameter.COMMAND);
        if(command == null){
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        boolean isAccessGranted = true;
        if (isAdminCommand(command)) {
            isAccessGranted = checkAccess((HttpServletRequest) req);
        }
        if (isAccessGranted) {
            chain.doFilter(req, resp);
        } else {
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private boolean isAdminCommand(String command) {
        CommandProvider commandProvider = CommandProvider.getInstance();
        return commandProvider.isAdminCommand(command);
    }

    private boolean checkAccess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);
        if (user == null) {
            return false;
        }
        User.UserStatus userStatus = user.getUserStatus();

        return userStatus != null && userStatus == User.UserStatus.ADMIN;
    }

}
