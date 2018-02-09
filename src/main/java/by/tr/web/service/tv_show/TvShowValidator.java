package by.tr.web.service.tv_show;

import by.tr.web.service.input_validator.ValidationException;

public interface TvShowValidator {
    boolean validateShowStatus(String showStatus) throws ValidationException;
}
