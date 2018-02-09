package by.tr.web.controller.command.entity;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.show.review.ReviewBuildService;
import by.tr.web.service.show.review.ReviewService;
import by.tr.web.service.user.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddReviewImpl implements Command {
    private static final Logger logger = Logger.getLogger(AddUserRateImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);

        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        try {
            ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
            ReviewBuildService reviewBuildService = ServiceFactory.getInstance().getReviewBuildService();

            Review review = reviewBuildService.retrieveReviewWithContent(request);
            review.setUser(user);

            reviewService.addUserReview(review);

            UserService userService = ServiceFactory.getInstance().getUserService();
            User updatedUser = userService.updateReviewList(user);

            session.setAttribute(JspAttribute.USER, updatedUser);

            out.write(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ServiceException e) {
            logger.error("Cannot add user review", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
