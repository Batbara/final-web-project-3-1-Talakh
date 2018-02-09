package by.tr.web.service.show.review;

import by.tr.web.dao.DAOFactory;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.show.ReviewDAO;
import by.tr.web.domain.Review;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.ValidatorFactory;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    @Override
    public void addUserRate(Review reviewRate) throws ServiceException {

        ReviewValidator validator = ValidatorFactory.getInstance().getReviewValidator();
        validator.checkUserRate(reviewRate);

        ReviewDAO reviewDAO = DAOFactory.getInstance().getReviewDAO();
        try {
            reviewDAO.rateShow(reviewRate);
        } catch (DAOException e) {
            throw new ReviewException("Cannot set user[id=" +
                    reviewRate.getUser().getId() +
                    "] rate for show [id=" +
                    reviewRate.getShowId()
                    + "]", e);
        }

    }

    @Override
    public void addUserReview(Review review) throws ServiceException {
        ReviewValidator validator = ValidatorFactory.getInstance().getReviewValidator();
        validator.checkUserReviewContent(review);

        ReviewDAO reviewDAO = DAOFactory.getInstance().getReviewDAO();
        try {
            reviewDAO.addReview(review);
        } catch (DAOException e) {
            throw new ReviewException("Cannot set user[id=" +
                    review.getUser().getId() +
                    "] review for show [id=" +
                    review.getShowId()
                    + "]", e);
        }
    }

    @Override
    public void postUserReview(Review review) throws ServiceException {
        ReviewValidator validator = ValidatorFactory.getInstance().getReviewValidator();
        validator.checkShowInReview(review);
        validator.checkUserInReview(review);

        ReviewDAO reviewDAO = DAOFactory.getInstance().getReviewDAO();
        int showId = review.getShowId();
        int userId = review.getUser().getId();
        try {
            reviewDAO.postReview(userId, showId);
        } catch (DAOException e) {
            throw new ReviewException("Cannot post user [id=" +
                    userId + "] review for show [id=" + showId + "]", e);
        }
    }

    @Override
    public void deleteUserReview(Review review) throws ServiceException {
        ReviewValidator validator = ValidatorFactory.getInstance().getReviewValidator();
        validator.checkShowInReview(review);
        validator.checkUserInReview(review);

        ReviewDAO reviewDAO = DAOFactory.getInstance().getReviewDAO();
        int showId = review.getShowId();
        int userId = review.getUser().getId();

        try {
            reviewDAO.deleteReview(userId, showId);
        } catch (DAOException e) {
            throw new ReviewException("Cannot delete user [id=" +
                    userId + "] review for show [id=" + showId + "]", e);
        }
    }

    @Override
    public int countReviewsOnModeration() throws ServiceException {
        ReviewDAO reviewDAO = DAOFactory.getInstance().getReviewDAO();
        try {
            return reviewDAO.countReviewsOnModeration();
        } catch (DAOException e) {
            throw new CountingServiceException("Cannot count reviews on moderation", e);
        }
    }

    @Override
    public List<Review> takeReviewsOnModeration(int startReview, int reviewsNumber) throws ServiceException {
        ReviewDAO reviewDAO = DAOFactory.getInstance().getReviewDAO();
        try {
            return reviewDAO.takeReviewsOnModeration(startReview, reviewsNumber);
        } catch (DAOException e) {
            throw new ReviewException("Cannot take reviews on moderation from data base", e);
        }
    }

}
