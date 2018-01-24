package by.tr.web.dao;

import by.tr.web.domain.TvShow;
import by.tr.web.exception.dao.common.DAOException;

import java.util.List;

public interface TvShowDAO {

    List<TvShow> takeSortedTvShowList(int startID, int moviesNumber, String orderType, String lang) throws DAOException;

    TvShow takeTvShow(int id, String lang) throws DAOException;
    int countTvShow() throws DAOException;
}
