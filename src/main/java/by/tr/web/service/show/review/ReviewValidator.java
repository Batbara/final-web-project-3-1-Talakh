package by.tr.web.service.show.review;

import by.tr.web.domain.Review;
import by.tr.web.service.input_validator.ValidationException;

public interface ReviewValidator {
    boolean checkUserRate(Review review) throws ValidationException;

    boolean checkReviewStatus (String reviewStatus) throws ValidationException;

    boolean checkUserReviewContent(Review review) throws ValidationException;

    default boolean checkUserInReview(Review review) throws ValidationException {
        if (review.getUser() == null) {
            throw new ValidationException("No user information in review");
        }
        int userId = review.getUser().getId();
        if (userId <= 0) {
            throw new ValidationException("Invalid user ID");
        }
        return true;
    }

    default boolean checkShowInReview(Review review) throws ValidationException {
        if (review.getShowId() <= 0) {
            throw new ValidationException("Invalid show ID");
        }
        return true;
    }
}
