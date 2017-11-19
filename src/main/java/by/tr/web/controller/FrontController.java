package by.tr.web.controller;

import by.tr.web.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 7346647311889363507L;
    private CommandProvider commandProvider = new CommandProvider();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandName = request.getParameter("command");

        Command command = commandProvider.getCommand(commandName);
        command.execute(request,response);
    }
}
