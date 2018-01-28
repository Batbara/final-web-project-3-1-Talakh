package by.tr.web.controller.command.impl;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.ShowService;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
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


        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        try {
            ShowService showService = ServiceFactory.getInstance().getShowService();

            int showId = showService.retrieveShowId(request);
            String reviewTitle = showService.retrieveReviewTitle(request);
            String reviewContent = showService.retrieveReviewContent(request);

            UserReview userReview = new UserReviewBuilder()
                    .addUser(user)
                    .addShowId(showId)
                    .addReviewTitle(reviewTitle)
                    .addReviewContent(reviewContent)
                    .create();
            showService.addUserReview(userReview);

            UserService userService = ServiceFactory.getInstance().getUserService();
            User updatedUser = userService.updateReviewList(user);

            session.setAttribute(JspAttribute.USER, updatedUser);

            out.print(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ServiceException e) {
            logger.error("Cannot add user review", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        }
    }
}
