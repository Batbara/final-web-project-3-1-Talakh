package by.tr.web.dao.show.review;

import by.tr.web.dao.exception.DAOException;
import by.tr.web.domain.Review;

import java.util.List;

public interface ReviewDAO {

    void addReview(Review review) throws DAOException;

    void rateShow(Review review) throws DAOException;

    List<Review> takeReviewsOnModeration(int startReview, int reviewsNum) throws DAOException;

    void postReview(int userId, int showId) throws DAOException;

    void deleteReview(int userId, int showId) throws DAOException;

    int countReviewsOnModeration() throws DAOException;

}
