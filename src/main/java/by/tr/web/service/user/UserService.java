package by.tr.web.service.user;

import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User register(String login, String password, String eMail) throws ServiceException;

    User login(String login, String password, String lang) throws ServiceException;

    List<User> takeUserList(int startRecordNum, int recordsToTake, String lang) throws ServiceException;

    User updateReviewList(User user) throws ServiceException;

    int countUsers() throws ServiceException;

    List<BanReason> takeBanReasonList(String lang) throws ServiceException;

    void unbanUser(String userID) throws ServiceException;

    void banUser(User user) throws ServiceException;

    void changeUserStatus(User user, String newStatus) throws ServiceException;

    User retrieveUserFromRequest(HttpServletRequest request, String parameter) throws ServiceException;

}
