package by.tr.web.service.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.UserStatus;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.PasswordDAOException;
import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.dao.UserDAOException;
import by.tr.web.exception.service.NoSuchUserException;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.exception.service.ValidationException;
import by.tr.web.service.UserService;
import by.tr.web.service.validation.impl.LoginValidator;
import by.tr.web.service.validation.impl.RegisterValidator;

public class UserServiceImpl implements UserService {
    private RegisterValidator registerValidator = new RegisterValidator();
    private LoginValidator loginValidator = new LoginValidator();

    @Override
    public User register(String login, String password, String eMail) throws UserServiceException {

        boolean isDataValid;
        try {
            isDataValid = registerValidator.validate(login, password, eMail);
        } catch (ValidationException e) {
            throw new UserServiceException(e);
        }
        if (!isDataValid) {
            throw new UserServiceException("Unexpected error");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (isUserRegistered) {
                throw new UserServiceException("User already exists");
            } else {
                user = new User(login, password, eMail, UserStatus.USER);
                userDAO.register(user);
            }
        } catch (UserDAOException ex) {
            throw new UserServiceException(ex);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws UserServiceException {
        boolean isDataValid;
        try {
            isDataValid = loginValidator.validate(login, password);
        } catch (ValidationException e) {
            throw new UserServiceException(e);
        }
        if (!isDataValid) {
            throw new UserServiceException("Unexpected error");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (!isUserRegistered) {
                throw new NoSuchUserException();
            } else {
                user = userDAO.login(login, password);
                if (user == null) {
                    throw new UserServiceException("Unexpected error");
                }
            }
        } catch (PasswordDAOException e) {
            throw new IncorrectPasswordException(e);
        } catch (UserDAOException ex) {
            throw new UserServiceException(ex);
        }
        return user;

    }
}
