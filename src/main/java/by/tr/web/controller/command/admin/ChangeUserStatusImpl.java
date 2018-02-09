package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.exception.NoSuchUserException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChangeUserStatusImpl implements Command {
    private static final Logger logger = Logger.getLogger(ChangeUserStatusImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User userToChangeStatus = userService.retrieveUserFromRequest(request, JspAttribute.USER_CHANGE_STATUS_ID);

            String newUserStatus = request.getParameter(JspAttribute.NEW_USER_STATUS);

            userService.changeUserStatus(userToChangeStatus, newUserStatus);

            out.write(FrontControllerParameter.SUCCESS_RESPONSE);

        } catch (NoSuchUserException e) {
            logger.error("Cannot find user", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
