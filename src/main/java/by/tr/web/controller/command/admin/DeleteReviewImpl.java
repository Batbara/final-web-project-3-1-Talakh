package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.domain.Review;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.show.review.ReviewBuildService;
import by.tr.web.service.show.review.ReviewService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteReviewImpl implements Command {

    private static final Logger logger = Logger.getLogger(DeleteReviewImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();
        try {
            ServiceFactory factory = ServiceFactory.getInstance();

            ReviewService reviewService = factory.getReviewService();
            ReviewBuildService reviewBuildService = factory.getReviewBuildService();

            Review reviewToDelete = reviewBuildService.retrieveBasicReviewInfo(request);

            reviewService.deleteUserReview(reviewToDelete);

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
