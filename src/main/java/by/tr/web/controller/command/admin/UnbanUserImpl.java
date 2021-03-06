package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.user.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UnbanUserImpl implements Command {

    private static final Logger logger = Logger.getLogger(UnbanUserImpl.class);
    /**
     * Command to unban {@link User}
     *
     * <p>
     * Method retrieves all needed user data from request, validates it and tries to unban requested user.
     * In case of success, {@link FrontControllerParameter#SUCCESS_RESPONSE} is written to response during AJAX call.
     * If an error occurs, {@link FrontControllerParameter#FAILURE_RESPONSE} will be written to response.
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userUnbanId = request.getParameter(JspAttribute.USER_UNBAN_ID);


        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.unbanUser(userUnbanId);
            out.write(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ServiceException e) {
            logger.error("Error while unbanning user", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
