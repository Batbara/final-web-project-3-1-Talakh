package by.tr.web.service;

import by.tr.web.domain.Movie;
import by.tr.web.exception.service.common.ServiceException;

import java.util.List;

public interface MovieService extends ShowService{
    List<Movie> takeOrderedMovieList(int startID, int moviesNumber, String orderType, String lang) throws ServiceException;

    Movie takeMovie(int id, String lang) throws ServiceException;
}
