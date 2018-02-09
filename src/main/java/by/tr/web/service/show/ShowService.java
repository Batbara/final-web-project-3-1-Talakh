package by.tr.web.service.show;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.Show;
import by.tr.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShowService {
    List<Show> takeSortedShowList(int startID, int showsNumber, String lang) throws ServiceException;

    void deleteShow(String showId) throws ServiceException;

    int countAllShows() throws ServiceException;

    List<Review> takeShowReviewList(int startNumber, int reviewsNumber, int showId)
            throws ServiceException;


    List<Country> takeCountryList(String lang) throws ServiceException;

    List<Genre> takeGenreList(String lang) throws ServiceException;


    double takeShowRating(String showId) throws ServiceException;

    int countShowReviews(String showId) throws ServiceException;

    int retrieveShowId(HttpServletRequest request) throws ServiceException;


}
