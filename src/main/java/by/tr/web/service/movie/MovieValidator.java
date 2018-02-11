package by.tr.web.service.movie;

import by.tr.web.service.input_validator.ValidationException;

public interface MovieValidator {
    /**
     * Check if input {@link by.tr.web.domain.Movie.MPAARating} for {@link by.tr.web.domain.Movie} is valid
     *
     * @param mpaaRating String to check
     * @return true if MPAA rating is valid
     * @throws ValidationException If MPAA rating is invalid
     */
    boolean validateMpaaRating(String mpaaRating) throws ValidationException;
}
