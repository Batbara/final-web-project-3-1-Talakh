package by.tr.web.controller;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.command.command_provider.CommandProvider;
import by.tr.web.controller.constant.FrontControllerParameter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@MultipartConfig
public class FileUploader extends HttpServlet {
    private static final long serialVersionUID = 1876424766313236894L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(FrontControllerParameter.COMMAND);

        Command command = CommandProvider.getInstance().getCommand(commandName);
        command.execute(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
