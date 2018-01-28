package by.tr.web.service;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShowService {

    List<UserReview> takeReviewList(int startNumber, int reviewsNumber, String reviewStatus, int showId)
            throws ServiceException;

    void addUserRate(UserReview userReviewRate) throws ServiceException;

    void addUserReview(UserReview userReview) throws ServiceException;

    double takeShowRating (String showId) throws ServiceException;

    int countReviews(String showId) throws ServiceException;

    String retrieveReviewTitle(HttpServletRequest request) throws ServiceException;

    String retrieveReviewContent(HttpServletRequest request) throws ServiceException;

    int retrieveUserRate(HttpServletRequest request) throws ServiceException;

    int retrieveShowId(HttpServletRequest request) throws ServiceException;

}
