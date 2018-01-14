package by.tr.web.dao;

import by.tr.web.domain.Movie;
import by.tr.web.exception.dao.movie.MovieCounterDAOException;
import by.tr.web.exception.dao.movie.MovieDAOException;

import java.util.List;

public interface MovieDAO {
    List<Movie> takeOrderedMovieList(int startID, int moviesNumber, String orderType, String lang) throws MovieDAOException;
    int countMovies() throws MovieCounterDAOException;
    Movie takeMovie(int id, String lang) throws MovieDAOException;
}
