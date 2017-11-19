package by.tr.web.dao;

import by.tr.web.domain.User;
import by.tr.web.exception.dao.UserDAOException;

public interface UserDAO {
    User register(String login, String password, String eMail) throws UserDAOException;
    User login (String login, String password);
    boolean checkUniqueData(String login, String eMail);
    boolean isPasswordConfirmed(String login, String password);
}
