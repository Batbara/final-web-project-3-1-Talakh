package by.tr.web.service.movie;

import by.tr.web.service.input_validator.ValidationException;

public interface MovieValidator {
    boolean validateMpaaRating (String mpaaRating) throws ValidationException;
}
