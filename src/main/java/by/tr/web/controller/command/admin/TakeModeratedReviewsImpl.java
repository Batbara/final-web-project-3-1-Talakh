package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.domain.Review;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.show.review.ReviewService;
import by.tr.web.service.table.TableParameter;
import by.tr.web.service.table.TableService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeModeratedReviewsImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeModeratedReviewsImpl.class);

    /**
     * Command to take reviews on moderation
     * <p>
     * Method retrieves all reviews from data base with  status
     * to {@link by.tr.web.domain.Review.ReviewStatus#MODERATED} so administrators
     * could decide whether to delete review ({@link DeleteReviewImpl#execute(HttpServletRequest, HttpServletResponse)})
     * or post it ({@link PostReviewImpl#execute(HttpServletRequest, HttpServletResponse)}).
     * In case of success, List is put as attribute in request. After that request is forwarded to
     * {@link JspPagePath#REVIEWS_MODERATION_PAGE_PATH} page.
     * If an error occurs, {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR} will be sent.
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TableService tableService = ServiceFactory.getInstance().getTableService();
        ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
        try {
            int numberOfReviews = reviewService.countReviewsOnModeration();
            if (numberOfReviews == 0) {
                request.setAttribute(JspAttribute.REVIEWS_ON_MODERATION_LIST, null);
            } else {
                int currentReviewPage = tableService.takeCurrentPage(request,TableParameter.MODERATED_REVIEWS_TABLE );
                request.setAttribute(TableParameter.PAGE, currentReviewPage);

                int recordsOnPage = tableService.takeRecordsOnPage(request, TableParameter.MODERATED_REVIEWS_TABLE );
                request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);

                int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentReviewPage, numberOfReviews);


                int startRecordNum = (currentReviewPage - 1) * recordsOnPage;
                List<Review> reviewsOnModeration = reviewService.takeReviewsOnModeration(startRecordNum, recordsToTake);
                request.setAttribute(JspAttribute.REVIEWS_ON_MODERATION_LIST, reviewsOnModeration);

                int numOfPages = (int) Math.ceil((double) numberOfReviews / recordsOnPage);
                request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            }
            request.getRequestDispatcher(JspPagePath.REVIEWS_MODERATION_PAGE_PATH).forward(request, response);
        } catch (CountingServiceException e) {
            logger.error("Error while counting reviews on moderation", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ServiceException e) {
            logger.error("Error while taking reviews on moderation list", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
