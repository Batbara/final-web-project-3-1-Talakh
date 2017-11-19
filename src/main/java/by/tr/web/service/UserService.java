package by.tr.web.service;

import by.tr.web.domain.User;
import by.tr.web.exception.service.UserServiceException;

public interface UserService {
    User register(String login, String password, String eMail) throws UserServiceException;
    User login (String login, String password) throws UserServiceException;
}
