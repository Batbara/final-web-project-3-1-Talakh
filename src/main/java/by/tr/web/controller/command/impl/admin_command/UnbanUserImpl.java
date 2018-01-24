package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UnbanUserImpl implements Command {

    private static final Logger logger = Logger.getLogger(UnbanUserImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userUnbanID = request.getParameter(JspAttribute.USER_UNBAN_ID);


        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.unbanUser(userUnbanID);
            out.print(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ServiceException e) {
            logger.error("Error while unbanning user", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
