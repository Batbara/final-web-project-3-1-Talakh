package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.CountingServiceException;
import by.tr.web.service.ShowService;
import by.tr.web.service.TableService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeReviewsOnModeration implements Command {
    private static final Logger logger = Logger.getLogger(TakeReviewsOnModeration.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TableService tableService = ServiceFactory.getInstance().getTableService();
        ShowService showService = ServiceFactory.getInstance().getShowService();
        try {
            int numberOfReviews = showService.countReviewsOnModeration();
            if (numberOfReviews == 0) {
                request.setAttribute(JspAttribute.REVIEWS_ON_MODERATION_LIST, null);
            } else {
                int currentReviewPage = tableService.takeCurrentPage(request);
                request.setAttribute(TableParameter.PAGE, currentReviewPage);

                request.setAttribute(FrontControllerParameter.DEFAULT_RECORDS_ON_PAGE, 25);
                int recordsOnPage = tableService.takeRecordsOnPage(request);
                request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);

                int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentReviewPage, numberOfReviews);


                int startRecordNum = (currentReviewPage - 1) * recordsOnPage;
                List<UserReview> reviewsOnModeration = showService.takeReviewsOnModeration(startRecordNum, recordsToTake);
                request.setAttribute(JspAttribute.REVIEWS_ON_MODERATION_LIST, reviewsOnModeration);

                int numOfPages = (int) Math.ceil((double) numberOfReviews / recordsOnPage);
                request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);


            }
            request.getRequestDispatcher(JspPagePath.REVIEWS_MODERATION_PAGE_PATH).forward(request, response);
        } catch (CountingServiceException e) {
            logger.error("Error while counting reviews on moderation", e);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while taking reviews on moderation list", e);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }
    }
}
