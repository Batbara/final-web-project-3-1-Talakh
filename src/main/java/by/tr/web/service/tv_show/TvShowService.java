package by.tr.web.service.tv_show;

import by.tr.web.domain.Show;
import by.tr.web.domain.TvShow;
import by.tr.web.service.ServiceException;

import java.util.List;

public interface TvShowService {
    List<TvShow> takeOrderedTvShowList(int startID, int tvShowsNumber, String orderType, String lang) throws ServiceException;

    TvShow takeTvShow(String id, String lang) throws ServiceException;

    int countTvShow() throws ServiceException;

    int addTvShow(TvShow tvShow, Show russianTranslation) throws ServiceException;
}
