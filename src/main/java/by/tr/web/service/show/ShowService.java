package by.tr.web.service.show;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.Show;
import by.tr.web.service.ServiceException;

import java.util.List;

public interface ShowService {
    /**
     * Take specified List of {@link Show} entities
     *
     * @param start       Index of first {@link Show} to be added to List
     * @param showsNumber Total number of {@link Show} to take
     * @param lang        Client language
     * @return List of {@link Show} entities
     * @throws ServiceException If any error occurs
     */
    List<Show> takeSortedShowList(int start, int showsNumber, String lang) throws ServiceException;

    /**
     * Delete specified {@link Show}
     *
     * @param showId Id number of {@link Show} to delete
     * @throws ServiceException If any error occurs
     */
    void deleteShow(String showId) throws ServiceException;

    /**
     * Count all {@link Show} entities present in system
     *
     * @return Number of all Show entities
     * @throws ServiceException If any error occurs
     */
    int countAllShows() throws ServiceException;

    /**
     * Take specified List of {@link Review} for concrete {@link Show} instance.
     *
     * @param startNumber   Index of first {@link Review} to be added to List
     * @param reviewsNumber Number of all Review entities
     * @param showId        Id number of {@link Show} entity, which reviews are requested
     * @return List of {@link Review} entities
     * @throws ServiceException If any error occurs
     */
    List<Review> takeShowReviewList(int startNumber, int reviewsNumber, int showId)
            throws ServiceException;


    List<Country> takeCountryList(String lang) throws ServiceException;

    List<Genre> takeGenreList(String lang) throws ServiceException;

    double takeShowRating(String showId) throws ServiceException;

    int countShowReviews(String showId) throws ServiceException;


}
