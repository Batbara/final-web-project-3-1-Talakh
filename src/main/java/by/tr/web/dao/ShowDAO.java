package by.tr.web.dao;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.common.UnsupportedOperationException;

import java.util.List;

public interface ShowDAO {
    default void addReview(UserReview userReview) throws DAOException{
        throw new UnsupportedOperationException("Operation addReview is not applicable");
    }

    default void rateShow(UserReview userReview) throws DAOException{
        throw new UnsupportedOperationException("Operation rateShow is not applicable");
    }
    List<UserReview> takeReviewList (int startReview, int reviewsNum, String reviewStatus, int showId) throws DAOException;

    int countShowReviews(int showId) throws DAOException;
    double takeShowRating (int showId) throws DAOException;

}
