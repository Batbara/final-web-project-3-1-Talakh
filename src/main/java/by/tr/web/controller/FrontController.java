package by.tr.web.controller;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.command.CommandProvider;
import by.tr.web.controller.constant.FrontControllerParameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 7346647311889363507L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        String commandName = request.getParameter(FrontControllerParameter.COMMAND);

        Command command = CommandProvider.getInstance().getCommand(commandName);
        command.execute(request, response);
    }
}
