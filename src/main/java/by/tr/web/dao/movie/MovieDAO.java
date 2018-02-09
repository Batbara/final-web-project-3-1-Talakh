package by.tr.web.dao.movie;

import by.tr.web.dao.exception.DAOException;
import by.tr.web.domain.Movie;
import by.tr.web.domain.Show;

import java.util.List;

public interface MovieDAO {

    List<Movie> takeSortedMovieList(int startID, int moviesNumber, String orderType, String lang) throws DAOException;

    Movie takeMovie(int id, String lang) throws DAOException;

    int addMovie(Movie movieEnglish, Show russianTranslation) throws DAOException;

    int countMovie() throws DAOException;
}
