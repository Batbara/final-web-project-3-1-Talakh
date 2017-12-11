package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAccountImpl implements Command {// не используй в именовании show - сам же класс никому ничего не показывает, take как вариант
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("/account");
    }
}
