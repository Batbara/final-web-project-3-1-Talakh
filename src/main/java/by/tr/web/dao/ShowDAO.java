package by.tr.web.dao;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.common.UnsupportedOperationException;

import java.util.List;

public interface ShowDAO {
    default void addReview(UserReview userReview) throws DAOException {
        throw new UnsupportedOperationException("Operation addReview is not applicable");
    }

    default void rateShow(UserReview userReview) throws DAOException {
        throw new UnsupportedOperationException("Operation rateShow is not applicable");
    }

    List<UserReview> takeShowReviewList(int startReview, int reviewsNum, int showId) throws DAOException;

    List<UserReview> takeReviewsOnModeration(int startReview, int reviewsNum) throws DAOException;

    int countShowReviews(int showId) throws DAOException;

    int countReviewsOnModeration() throws DAOException;

    double takeShowRating(int showId) throws DAOException;

}
