package by.tr.web.service.show.review;

import by.tr.web.domain.Review;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidationException;

public class ReviewValidatorImpl implements ReviewValidator {
    @Override
    public boolean checkUserRate(Review review) throws ValidationException {
        checkUserInReview(review);
        checkShowInReview(review);

        int userRate = review.getUserRate();
        if (userRate <= 0 || userRate > 10) {
            throw new ValidationException("Invalid user rate");
        }
        return true;
    }

    @Override
    public boolean checkReviewStatus(String inputReviewStatus) throws InvalidReviewStatusException {
        if (inputReviewStatus == null) {
            throw new InvalidReviewStatusException("Input review status is empty");
        }
        for (Review.ReviewStatus reviewStatus : Review.ReviewStatus.values()) {
            String statusName = reviewStatus.name();
            if (statusName.equalsIgnoreCase(inputReviewStatus)) {
                return true;
            }
        }
        throw new InvalidReviewStatusException("Invalid review status constant");
    }

    @Override
    public boolean checkUserReviewContent(Review review) throws ValidationException {
        checkUserInReview(review);
        checkShowInReview(review);

        String reviewContent = review.getReviewContent();

        if (reviewContent == null) {
            throw new RequestParameterNotFound("Review content is empty");
        }

        return true;
    }
}
