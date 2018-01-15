package by.tr.web.dao;

import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.user.UserDAOException;

import java.util.List;

public interface UserDAO {
    boolean register(User user) throws UserDAOException;

    User login(String login, String password) throws UserDAOException;

    boolean isUserRegistered(String login) throws UserDAOException;
    boolean isEmailRegistered(String eMail) throws UserDAOException;
    List<User> takeUserList(int startRecordNum, int recordsToTake, String lang) throws UserDAOException;
    int countUsers() throws UserDAOException;
    void banUser (User user) throws UserDAOException;
    List<BanReason> getBanReasonList(String lang) throws UserDAOException;
    void unbanUser(int userID) throws UserDAOException;
}
