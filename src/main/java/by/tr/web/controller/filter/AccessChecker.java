package by.tr.web.controller.filter;

import by.tr.web.controller.command.CommandProvider;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JSPPagePath;
import by.tr.web.domain.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class AccessChecker implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String command = req.getParameter(FrontControllerParameter.COMMAND);
        if(CommandProvider.getInstance().isAdminCommand(command)) {
            User.UserStatus userStatus = (User.UserStatus) req.getAttribute(FrontControllerParameter.USER_STATUS);

            if (userStatus == null || userStatus != User.UserStatus.ADMIN) {
                req.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE).forward(req, resp);
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
