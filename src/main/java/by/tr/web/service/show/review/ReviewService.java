package by.tr.web.service.show.review;

import by.tr.web.domain.Review;
import by.tr.web.service.ServiceException;

import java.util.List;

public interface ReviewService {

    List<Review> takeReviewsOnModeration(int startReview, int reviewsNumber) throws ServiceException;

    void addUserRate(Review reviewRate) throws ServiceException;

    void addUserReview(Review review) throws ServiceException;

    void postUserReview(Review review) throws ServiceException;

    void deleteUserReview(Review review) throws ServiceException;

    int countReviewsOnModeration() throws ServiceException;
}
