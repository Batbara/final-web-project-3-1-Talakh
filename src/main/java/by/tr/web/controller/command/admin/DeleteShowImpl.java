package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.show.ShowService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteShowImpl implements Command {
    private static final Logger logger = Logger.getLogger(DeleteReviewImpl.class);

    /**
     * Command to delete show
     * <p>
     * Method retrieves all needed show data from request, validates it and tries to delete requested show from data base.
     * In case of success, {@link FrontControllerParameter#SUCCESS_RESPONSE} is written to response during AJAX call.
     * If an error occurs, {@link FrontControllerParameter#FAILURE_RESPONSE} will be written to response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();
        try {
            ShowService showService = ServiceFactory.getInstance().getShowService();
            String showId = request.getParameter(JspAttribute.SHOW_ID);

            showService.deleteShow(showId);

            out.write(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ValidationException e) {
            logger.error("Invalid parameters in request", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred while deleting review", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
