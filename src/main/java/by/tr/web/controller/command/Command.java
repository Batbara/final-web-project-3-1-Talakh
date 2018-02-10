package by.tr.web.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    /**
     * Executes command from request
     * <p>
     *     @param request
     * {@link HttpServletRequest} object sent by client
     *
     * @param response
     * {@link HttpServletResponse} object sent by client
     *
     * @throws IOException
     * @throws ServletException
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
