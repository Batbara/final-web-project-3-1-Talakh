package by.tr.web.dao;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;

public interface ShowDAO {
    void addReview(UserReview userReview) throws DAOException;
    void rateShow(UserReview userReview) throws DAOException;
}
