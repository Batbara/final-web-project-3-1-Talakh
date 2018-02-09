package by.tr.web.dao.tv_show;

import by.tr.web.dao.exception.DAOException;
import by.tr.web.domain.Show;
import by.tr.web.domain.TvShow;

import java.util.List;

public interface TvShowDAO {

    List<TvShow> takeSortedTvShowList(int startID, int moviesNumber, String orderType, String lang) throws DAOException;

    TvShow takeTvShow(int id, String lang) throws DAOException;

    int countTvShow() throws DAOException;

    int addTvShow(TvShow tvShow, Show russianTranslation) throws DAOException;
}
