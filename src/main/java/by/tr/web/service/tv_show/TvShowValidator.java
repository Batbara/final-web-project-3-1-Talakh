package by.tr.web.service.tv_show;

import by.tr.web.service.input_validator.ValidationException;

public interface TvShowValidator {

    /**
     * Check if input {@link by.tr.web.domain.TvShow.ShowStatus} for {@link by.tr.web.domain.TvShow} is valid
     *
     * @param showStatus String to check
     * @return true if status is valid
     * @throws ValidationException If TV-show status is invalid
     */
    boolean validateShowStatus(String showStatus) throws ValidationException;
}
