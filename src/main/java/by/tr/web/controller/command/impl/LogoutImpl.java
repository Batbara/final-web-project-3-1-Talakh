package by.tr.web.controller.command.impl;

import by.tr.web.controller.constant.JSPPagePath;
import by.tr.web.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.getSession().invalidate();
        response.sendRedirect(JSPPagePath.INDEX);
    }
}
