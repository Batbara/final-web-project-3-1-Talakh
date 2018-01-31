package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChangeUserStatus implements Command {
    private static final Logger logger = Logger.getLogger(ChangeUserStatus.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User userToChangeStatus = userService.retrieveUserFromRequest(request, JspAttribute.USER_CHANGE_STATUS_ID);

            String newUserStatus = request.getParameter(JspAttribute.NEW_USER_STATUS);

            userService.changeUserStatus(userToChangeStatus, newUserStatus);

            out.print(FrontControllerParameter.SUCCESS_RESPONSE);

        } catch (NoSuchUserException e) {
            logger.error("Cannot find user", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
