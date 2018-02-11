package by.tr.web.controller.command.user;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspPagePath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeAccountImpl implements Command {

    /**
     * Redirects to user account page
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(JspPagePath.USER_ACCOUNT_PATH);
    }
}
