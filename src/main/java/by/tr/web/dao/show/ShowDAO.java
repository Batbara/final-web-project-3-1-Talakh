package by.tr.web.dao.show;

import by.tr.web.dao.exception.DAOException;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.Show;

import java.util.List;

public interface ShowDAO {


    List<Show> takeSortedShowList(int startID, int showsNumber, String lang) throws DAOException;

    void deleteShow (int showId) throws DAOException;

    int countAllShows() throws DAOException;

    List<Review> takeShowReviewList(int startReview, int reviewsNum, int showId) throws DAOException;

    List<Country> takeCountryList(String lang) throws DAOException;

    List<Genre> takeGenreList(String lang) throws DAOException;

    int countShowReviews(int showId) throws DAOException;

    double takeShowRating(int showId) throws DAOException;

}
