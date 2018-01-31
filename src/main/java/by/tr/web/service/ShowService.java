package by.tr.web.service;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShowService {

    List<UserReview> takeShowReviewList(int startNumber, int reviewsNumber, int showId)
            throws ServiceException;
    List<UserReview> takeReviewsOnModeration (int startReview, int reviewsNumber) throws ServiceException;
    void addUserRate(UserReview userReviewRate) throws ServiceException;

    void addUserReview(UserReview userReview) throws ServiceException;

    double takeShowRating (String showId) throws ServiceException;

    int countShowReviews(String showId) throws ServiceException;

    int countReviewsOnModeration() throws ServiceException;

    String retrieveReviewTitle(HttpServletRequest request) throws ServiceException;

    String retrieveReviewContent(HttpServletRequest request) throws ServiceException;

    int retrieveUserRate(HttpServletRequest request) throws ServiceException;

    int retrieveShowId(HttpServletRequest request) throws ServiceException;

}
