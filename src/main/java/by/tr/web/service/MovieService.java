package by.tr.web.service;

import by.tr.web.domain.Movie;
import by.tr.web.exception.service.movie.MovieServiceException;

import java.util.List;

public interface MovieService {
    List<Movie> takeOrderedMovieList(int startID, int moviesNumber, String orderType, String lang) throws MovieServiceException;
    int countMovies() throws MovieServiceException;
}
