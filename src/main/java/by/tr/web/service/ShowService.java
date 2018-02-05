package by.tr.web.service;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShowService {

    List<UserReview> takeShowReviewList(int startNumber, int reviewsNumber, int showId)
            throws ServiceException;

    List<UserReview> takeReviewsOnModeration(int startReview, int reviewsNumber) throws ServiceException;

    List<Country> takeCountryList(String lang) throws ServiceException;

    List<Genre> takeGenreList(String lang) throws ServiceException;

    void addUserRate(UserReview userReviewRate) throws ServiceException;

    void addUserReview(UserReview userReview) throws ServiceException;

    void postUserReview(UserReview userReview) throws ServiceException;

    void deleteUserReview(UserReview userReview) throws ServiceException;

    double takeShowRating(String showId) throws ServiceException;

    int countShowReviews(String showId) throws ServiceException;

    int countReviewsOnModeration() throws ServiceException;

    String retrieveReviewTitle(HttpServletRequest request) throws ServiceException;

    String retrieveReviewContent(HttpServletRequest request) throws ServiceException;

    int retrieveUserRate(HttpServletRequest request) throws ServiceException;

    int retrieveShowId(HttpServletRequest request) throws ServiceException;

    UserReview retrieveUserReview(HttpServletRequest request) throws ServiceException;

}
