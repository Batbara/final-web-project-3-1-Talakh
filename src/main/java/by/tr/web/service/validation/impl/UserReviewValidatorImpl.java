package by.tr.web.service.validation.impl;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ValidationException;
import by.tr.web.service.validation.UserReviewValidator;

public class UserReviewValidatorImpl implements UserReviewValidator {
    @Override
    public boolean checkUserRate(UserReview userReview) throws ValidationException {
        checkUserInReview(userReview);
        checkShowInReview(userReview);

        int userRate = userReview.getUserRate();
        if (userRate <= 0 || userRate > 10) {
            throw new ValidationException("Invalid user rate");
        }
        return true;
    }

    @Override
    public boolean checkUserReviewContent(UserReview userReview) throws ValidationException {
        checkUserInReview(userReview);
        checkShowInReview(userReview);
        String reviewContent = userReview.getReviewContent();
        if(reviewContent == null){
            throw new ValidationException("Invalid review content");
        }
        if(userReview.getPostDate() == null){
            throw new ValidationException("Invalid post date");
        }
        return true;
    }
}
