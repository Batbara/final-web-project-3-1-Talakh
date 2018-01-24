package by.tr.web.dao;

import by.tr.web.domain.Movie;
import by.tr.web.exception.dao.common.DAOException;

import java.util.List;

public interface MovieDAO {

    List<Movie> takeSortedMovieList(int startID, int moviesNumber, String orderType, String lang) throws DAOException;

    Movie takeMovie(int id, String lang) throws DAOException;

    int countMovie() throws DAOException;
}
