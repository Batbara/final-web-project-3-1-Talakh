package by.tr.web.service;

import by.tr.web.domain.UserReview;
import by.tr.web.exception.service.common.ServiceException;

public interface ShowService {

     void addUserRate(UserReview userReviewRate) throws ServiceException;

}
