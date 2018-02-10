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

public class AddUserRateImpl implements Command {
    private static final Logger logger = Logger.getLogger(AddUserRateImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);

        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            ReviewService reviewService = factory.getReviewService();
            ReviewBuildService reviewBuildService = factory.getReviewBuildService();

            Review review = reviewBuildService.retrieveRate(request);
            review.setUser(user);

            reviewService.addUserRate(review);

            UserService userService = factory.getUserService();
            User updatedUser = userService.updateReviewList(user);

            session.setAttribute(JspAttribute.USER, updatedUser);

            out.write(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ServiceException e) {
            logger.error("Cannot add user rate", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}