package by.tr.web.service.validation;

import by.tr.web.exception.service.common.ValidationException;

public interface MovieValidator {
    boolean validateMpaaRating (String mpaaRating) throws ValidationException;
}
