package by.tr.web.service;

import by.tr.web.domain.TvShow;
import by.tr.web.exception.service.common.ServiceException;

import java.util.List;

public interface TvShowService {
    List<TvShow> takeOrderedTvShowList(int startID, int tvShowsNumber, String orderType, String lang) throws ServiceException;

    TvShow takeTvShow(String id, String lang) throws ServiceException;

    int countTvShow() throws ServiceException;
}
