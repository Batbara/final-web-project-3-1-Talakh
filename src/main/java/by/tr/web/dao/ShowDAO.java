package by.tr.web.dao;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.common.UnsupportedOperationException;

public interface ShowDAO {
    default void addReview(UserReview userReview) throws DAOException{
        throw new UnsupportedOperationException("Operation addReview is not applicable");
    }

    default void rateShow(UserReview userReview) throws DAOException{
        throw new UnsupportedOperationException("Operation rateShow is not applicable");
    }

}
