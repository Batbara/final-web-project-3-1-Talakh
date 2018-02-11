package by.tr.web.service.movie;

import by.tr.web.domain.Movie;
import by.tr.web.domain.Show;
import by.tr.web.service.ServiceException;

import java.util.List;

public interface MovieService {
    /**
     * Counts all {@link Movie} entities in data base
     *
     * @return number of {@link Movie} entities
     * @throws ServiceException If any error occurs
     */
    int countMovies() throws ServiceException;

    /**
     * Take specified List of {@link Movie} entities
     *
     * @param start        Index of first {@link Movie} to be added to List
     * @param moviesNumber Total number of {@link Movie} to take
     * @param orderType    Sort ordering for List
     * @param lang         Client language
     * @return List of {@link Movie} entities
     * @throws ServiceException If any error occurs
     */
    List<Movie> takeOrderedMovieList(int start, int moviesNumber, String orderType, String lang) throws ServiceException;

    /**
     * Take {@link Movie} by its id number
     *
     * @param id   Movie id number
     * @param lang Client language
     * @return {@link Movie} entity with all data
     * @throws ServiceException If any error occurs
     */
    Movie takeMovie(String id, String lang) throws ServiceException;

    /**
     * Add new {@link Movie} information
     *
     * @param movieEnglish       Movie data in default language
     * @param russianTranslation Russian translation for movie data
     * @return Integer number - added {@link Movie} id
     * @throws ServiceException If any error occurs
     */
    int addMovie(Movie movieEnglish, Show russianTranslation) throws ServiceException;
}
