package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.common.ValidationException;
import by.tr.web.service.ShowService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteReview implements Command {

    private static final Logger logger = Logger.getLogger(DeleteReview.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();
        try {
            ShowService showService = ServiceFactory.getInstance().getShowService();
            UserReview reviewToDelete = showService.retrieveUserReview(request);

            showService.deleteUserReview(reviewToDelete);

            out.print(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ValidationException e) {
            logger.error("Invalid parameters in request", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred while deleting review", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
