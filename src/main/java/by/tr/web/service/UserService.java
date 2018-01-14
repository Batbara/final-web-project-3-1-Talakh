package by.tr.web.service;

import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;

import java.util.List;

public interface UserService {

    User register(String login, String password, String eMail) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    List<User> takeUserList(int startRecordNum, int recordsToTake, String lang) throws ServiceException;

    int countUsers() throws ServiceException;

    List<BanReason> takeBanReasonList(String lang) throws ServiceException;

    void banUser(User user) throws ServiceException;
}
