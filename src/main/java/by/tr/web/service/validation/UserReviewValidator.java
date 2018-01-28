package by.tr.web.service.validation;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ValidationException;

public interface UserReviewValidator {
    boolean checkUserRate(UserReview userReview) throws ValidationException;

    boolean checkReviewStatus (String reviewStatus) throws ValidationException;

    boolean checkUserReviewContent(UserReview userReview) throws ValidationException;

    default boolean checkUserInReview(UserReview userReview) throws ValidationException {
        if (userReview.getUser() == null) {
            throw new ValidationException("No user information in review");
        }
        int userId = userReview.getUser().getId();
        if (userId <= 0) {
            throw new ValidationException("Invalid user ID");
        }
        return true;
    }

    default boolean checkShowInReview(UserReview review) throws ValidationException {
        if (review.getShowId() <= 0) {
            throw new ValidationException("Invalid show ID");
        }
        return true;
    }
}
