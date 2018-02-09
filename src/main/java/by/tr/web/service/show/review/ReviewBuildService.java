package by.tr.web.service.show.review;

import by.tr.web.domain.Review;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.ValidationException;

import javax.servlet.http.HttpServletRequest;

public interface ReviewBuildService {
    Review retrieveReviewWithContent(HttpServletRequest request) throws ServiceException;

    Review retrieveRate(HttpServletRequest request) throws ValidationException;

    Review retrieveBasicReviewInfo(HttpServletRequest request) throws ValidationException;
}
