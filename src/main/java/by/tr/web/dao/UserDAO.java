package by.tr.web.dao;

import by.tr.web.domain.User;
import by.tr.web.exception.dao.user.UserDAOException;

public interface UserDAO {
    boolean register(User user) throws UserDAOException;

    User login(String login, String password) throws UserDAOException;

    boolean isUserRegistered(String login) throws UserDAOException;
    boolean isEmailRegistered(String eMail) throws UserDAOException;
}
