package by.tr.web.service.user;

import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.dao.DAOFactory;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.user.EmailDaoException;
import by.tr.web.dao.user.PasswordDAOException;
import by.tr.web.dao.user.RegistrationDAOException;
import by.tr.web.dao.user.UserDAO;
import by.tr.web.dao.user.UserNotRegisteredException;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.InvalidNumericalInput;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidatorFactory;
import by.tr.web.service.user.exception.CountingUserException;
import by.tr.web.service.user.exception.EmailAlreadyRegisteredException;
import by.tr.web.service.user.exception.InvalidPasswordException;
import by.tr.web.service.user.exception.InvalidUserIDException;
import by.tr.web.service.user.exception.NoSuchUserException;
import by.tr.web.service.user.exception.UserAlreadyExistsException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User register(String login, String password, String email) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator userValidator = validatorFactory.getUserValidator();

        userValidator.validateCredentials(login, password, email);

        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        User user;
        try {

            user = new User(login, password, email);
            userDAO.register(user);

        } catch (RegistrationDAOException e) {
            throw new UserAlreadyExistsException("User " + login + " already exists");
        } catch (EmailDaoException e) {
            throw new EmailAlreadyRegisteredException("E-mail " + email + " is already registered");
        } catch (DAOException ex) {
            throw new ServiceException("Error while registering user", ex);
        }
        return user;
    }

    @Override
    public User login(String login, String password, String lang) throws ServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

        UserValidator userValidator = validatorFactory.getUserValidator();
        userValidator.validateCredentials(login, password);

        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();
        dataTypeValidator.checkLanguage(lang);

        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        try {

            return userDAO.login(login, password, lang);

        } catch (UserNotRegisteredException e) {
            throw new NoSuchUserException("Such user is not registered", e);
        } catch (PasswordDAOException e) {
            throw new InvalidPasswordException("Incorrect password", e);
        } catch (DAOException ex) {
            throw new ServiceException("Error while login user", ex);
        }

    }

    @Override
    public List<User> takeUserList(int startRecordNum, int recordsToTake, String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkLanguage(lang);

        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        List<User> userList;
        try {
            userList = userDAO.takeUserList(startRecordNum, recordsToTake, lang);
        } catch (DAOException e) {
            throw new ServiceException("Unable to get users list from data base", e);
        }
        return userList;
    }

    @Override
    public User updateReviewList(User user) throws ServiceException {
        if (user == null) {
            throw new NoSuchUserException("User is empty");
        }
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.updateReviewList(user);
        } catch (DAOException e) {
            throw new ServiceException("Cannot update user review list", e);
        }
    }

    @Override
    public int countUsers() throws ServiceException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        int usersCount;
        try {
            usersCount = userDAO.countUsers();
            return usersCount;
        } catch (DAOException e) {
            throw new CountingUserException("Cannot count users in data base", e);
        }
    }

    @Override
    public List<BanReason> takeBanReasonList(String lang) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        validator.checkLanguage(lang);

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        List<BanReason> banReasonList;
        try {
            banReasonList = userDAO.getBanReasonList(lang);
            return banReasonList;
        } catch (DAOException e) {
            throw new ServiceException("Cannot retrieve ban reason list from data base", e);
        }
    }

    @Override
    public void unbanUser(String userID) throws ServiceException {
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        try {
            validator.checkForPositive(userID);
        } catch (InvalidNumericalInput | RequestParameterNotFound e) {
            throw new InvalidUserIDException("Incorrect user ID input", e);
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            int idFromString = Integer.parseInt(userID);
            userDAO.unbanUser(idFromString);
        } catch (DAOException e) {
            throw new ServiceException("Error while unbanning user", e);
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
        } catch (DAOException e) {
            throw new ServiceException("Unable to ban user " + user.toString(), e);
        }
    }

    @Override
    public void changeUserStatus(User user, String newStatus) throws ServiceException {
        if (user == null) {
            throw new NoSuchUserException("User is null");
        }
        UserValidator validator = ValidatorFactory.getInstance().getUserValidator();
        validator.checkUserStatus(newStatus);

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        int userId = user.getId();
        try {
            userDAO.changeUserStatus(userId, newStatus.toLowerCase());
        } catch (DAOException e) {
            throw new ServiceException("Cannot change user with id=" + Integer.toString(userId)
                    + ", status='" + user.getUserStatus().toString() +
                    "' to " + newStatus, e);
        }

    }

    @Override
    public User retrieveUserFromRequest(HttpServletRequest request, String userParameter) throws ServiceException {
        List<User> userList = (List<User>) request.getSession().getAttribute(JspAttribute.USER_LIST);
        if (userList == null) {
            throw new ServiceException("User list is empty");
        }
        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        String userIdParameter = request.getParameter(userParameter);
        validator.checkForPositive(userIdParameter);

        int userId = Integer.parseInt(userIdParameter);
        for (User user : userList) {
            if (user.getId() == userId) {
                return user;
            }
        }
        throw new NoSuchUserException("Cannot find user with id " + userId);
    }
}
