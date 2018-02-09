package by.tr.web.service.show.review;

import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.input_validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;

public class ReviewBuildServiceImpl implements ReviewBuildService {
    @Override
    public Review retrieveReviewWithContent(HttpServletRequest request) throws ServiceException {
        int showId = retrieveShowId(request);
        String reviewTitle = retrieveReviewTitle(request);
        String reviewContent = retrieveReviewContent(request);
        Review review = new UserReviewBuilder()
                .addShowId(showId)
                .addReviewTitle(reviewTitle)
                .addReviewContent(reviewContent)
                .create();
        return review;
    }

    @Override
    public Review retrieveRate(HttpServletRequest request) throws ValidationException {
        int userRate = retrieveUserRate(request);
        int showId = retrieveShowId(request);

        Review review = new UserReviewBuilder()
                .addUserRate(userRate)
                .addShowId(showId)
                .create();
        return review;
    }

    @Override
    public Review retrieveBasicReviewInfo(HttpServletRequest request) throws ValidationException {
        int showId = retrieveShowId(request);

        String userIdString = request.getParameter(JspAttribute.USER_ID);

        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkForPositive(userIdString);
        int userId = Integer.parseInt(userIdString);

        Review review = new UserReviewBuilder()
                .addShowId(showId)
                .addUser(new User(userId))
                .create();

        return review;
    }

    private String retrieveReviewTitle(HttpServletRequest request) throws ValidationException {
        String reviewTitle = request.getParameter(JspAttribute.REVIEW_TITLE);
        if (reviewTitle == null) {
            throw new RequestParameterNotFound("Review title is empty");
        }
        return reviewTitle;
    }

    private String retrieveReviewContent(HttpServletRequest request) throws ValidationException {
        String reviewTitle = request.getParameter(JspAttribute.REVIEW_CONTENT);
        if (reviewTitle == null) {
            throw new RequestParameterNotFound("Review content is empty");
        }
        return reviewTitle;
    }
    private int retrieveShowId(HttpServletRequest request) throws ValidationException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        String parameter = request.getParameter(JspAttribute.SHOW_ID);

        validator.checkForPositive(parameter);
        return Integer.parseInt(parameter);
    }

    private int retrieveUserRate(HttpServletRequest request) throws ValidationException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        String parameter = request.getParameter(JspAttribute.USER_RATE);

        validator.checkForPositive(parameter);
        return Integer.parseInt(parameter);

    }

}
