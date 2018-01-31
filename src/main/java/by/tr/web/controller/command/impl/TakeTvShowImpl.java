package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.TvShow;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.ShowService;
import by.tr.web.service.TvShowService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeTvShowImpl implements Command {
    private final static Logger logger = Logger.getLogger(TakeTvShowImpl.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tvShowId = request.getParameter(JspAttribute.SHOW_ID);

        String lang = RequestUtil.getLanguage(request);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TvShowService tvShowService = serviceFactory.getTvShowService();

        ShowService showService = serviceFactory.getShowService();
        try {

            TvShow tvShow = tvShowService.takeTvShow(tvShowId, lang);
            double tvShowRating = showService.takeShowRating(tvShowId);
            tvShow.setUserRating(tvShowRating);

            int numberOfReviews = showService.countShowReviews(tvShowId);

            if (numberOfReviews != 0) {
                List<UserReview> userReviews = RequestUtil.takeReviewListForShow(request, numberOfReviews, tvShow.getShowID());
                tvShow.setReviewList(userReviews);
            }
            request.setAttribute(JspAttribute.REVIEWS_NUMBER, numberOfReviews);
            request.setAttribute(JspAttribute.SHOW, tvShow);

            RequestUtil.updateUserReviewList(request);

            request.getRequestDispatcher(JspPagePath.TVSHOW_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while taking movie", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
