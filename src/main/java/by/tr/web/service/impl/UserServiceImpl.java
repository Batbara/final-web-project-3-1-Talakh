package by.tr.web.service.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.user.PasswordDAOException;
import by.tr.web.exception.dao.user.UserDAOException;
import by.tr.web.exception.service.common.LangNotSupportedException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.CountingUserException;
import by.tr.web.exception.service.user.EMailAlreadyRegisteredException;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.exception.service.user.UserAlreadyExistsException;
import by.tr.web.exception.service.user.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User register(String login, String password, String eMail) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator registerValidator = validatorFactory.getRegisterValidator();

        boolean isDataValid = registerValidator.validateCredentials(login, password, eMail);
        if (!isDataValid) {
            throw new ServiceException("Unexpected error while validating user register credentials");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (isUserRegistered) {
                throw new UserAlreadyExistsException("User " + login + " already exists");
            }
            boolean isEmailRegistered = userDAO.isEmailRegistered(eMail);
            if (isEmailRegistered) {
                throw new EMailAlreadyRegisteredException("E-mail " + eMail + " is already registered");
            } else {
                user = new User(login, password, eMail);
                userDAO.register(user);
            }
        } catch (UserDAOException ex) {
            throw new ServiceException("Error while registering user", ex);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator loginValidatorImpl = validatorFactory.getLoginValidator();

        boolean isDataValid = loginValidatorImpl.validateCredentials(login, password);

        if (!isDataValid) {
            throw new ServiceException("Unexpected error while validating user login credentials");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (!isUserRegistered) {
                throw new NoSuchUserException("No such user as " + login);
            } else {
                user = userDAO.login(login, password);
                if (user == null) {
                    throw new ServiceException("Unexpected logging error");
                }
            }
        } catch (PasswordDAOException e) {
            throw new IncorrectPasswordException("Incorrect password", e);
        } catch (UserDAOException ex) {
            throw new ServiceException("Error while login user", ex);
        }
        return user;

    }

    @Override
    public List<User> takeUserList(int startRecordNum, int recordsToTake, String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        boolean isDataValid = validator.validateInputParameters(startRecordNum, recordsToTake, lang);
        if (!isDataValid) {
            throw new ServiceException("Unexpected result from input validation");
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        List<User> userList;
        try {
            userList = userDAO.takeUserList(startRecordNum, recordsToTake, lang);
        } catch (UserDAOException e) {
            throw new ServiceException("Unable to get users list from data base", e);
        }
        return userList;
    }

    @Override
    public int countUsers() throws ServiceException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        int usersCount;
        try {
            usersCount = userDAO.countUsers();
            return usersCount;
        } catch (UserDAOException e) {
            throw new CountingUserException("Cannot count users in data base", e);
        }
    }

    @Override
    public List<BanReason> takeBanReasonList(String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        if (!validator.checkLanguage(lang)) {
            throw new LangNotSupportedException("Language " + lang + " is not supported");
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        List<BanReason> banReasonList;
        try {
            banReasonList = userDAO.getBanReasonList(lang);
            return banReasonList;
        } catch (UserDAOException e) {
            throw new ServiceException("Cannot retrieve ban reason list from data base", e);
        }
    }

    @Override
    public void banUser(User user) throws ServiceException {
        if (user == null) {
            throw new NoSuchUserException("User is null");
        }
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            userDAO.banUser(user);
        } catch (UserDAOException e) {
            throw new UserServiceException("Unable to ban user " + user.toString(), e);
        }
    }
}
