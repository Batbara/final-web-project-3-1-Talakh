package by.tr.web.service.movie;

import by.tr.web.domain.Movie;
import by.tr.web.domain.Show;
import by.tr.web.service.ServiceException;

import java.util.List;

public interface MovieService {
    int countMovie() throws ServiceException;

    List<Movie> takeOrderedMovieList(int startID, int moviesNumber, String orderType, String lang) throws ServiceException;

    Movie takeMovie(String id, String lang) throws ServiceException;

    int addMovie(Movie movieEnglish, Show russianTranslation) throws ServiceException;
}