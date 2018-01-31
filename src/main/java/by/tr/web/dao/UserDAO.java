package by.tr.web.dao;

import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.common.DAOException;

import java.util.List;

public interface UserDAO {
    boolean register(User user) throws DAOException;

    User login(String login, String password, String lang) throws DAOException;

    User updateReviewList(User user) throws DAOException;

    boolean isUserRegistered(String login) throws DAOException;

    boolean isEmailRegistered(String eMail) throws DAOException;

    List<User> takeUserList(int startRecordNum, int recordsToTake, String lang) throws DAOException;

    int countUsers() throws DAOException;

    void banUser(User user) throws DAOException;

    List<BanReason> getBanReasonList(String lang) throws DAOException;

    void unbanUser(int userID) throws DAOException;

    void changeUserStatus (int userId, String newStatus) throws DAOException;
}
