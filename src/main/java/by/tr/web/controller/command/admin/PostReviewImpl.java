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

public class PostReviewImpl implements Command {

    private static final Logger logger = Logger.getLogger(PostReviewImpl.class);

    /**
     * Command to post review
     * <p>
     * Method retrieves all needed review data from request, validates it and tries to change requested review status
     * to {@link by.tr.web.domain.Review.ReviewStatus#POSTED} so it could be shown to all users.
     * In case of success, {@link FrontControllerParameter#SUCCESS_RESPONSE} is written to response during AJAX call.
     * If an error occurs, {@link FrontControllerParameter#FAILURE_RESPONSE} will be written to response.
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();
        try {
            ServiceFactory factory = ServiceFactory.getInstance();

            ReviewBuildService reviewBuildService = factory.getReviewBuildService();
            ReviewService reviewService = factory.getReviewService();

            Review reviewToPost = reviewBuildService.retrieveBasicReviewInfo(request);
            reviewService.postUserReview(reviewToPost);

            out.write(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ValidationException e) {
            logger.error("Invalid parameters in request", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred while posting review", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
