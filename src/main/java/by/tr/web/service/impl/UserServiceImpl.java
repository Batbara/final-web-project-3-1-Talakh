package by.tr.web.service.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.PasswordDAOException;
import by.tr.web.exception.dao.UserDAOException;
import by.tr.web.exception.service.user.EMailAlreadyRegisteredException;
import by.tr.web.exception.service.user.IncorrectPasswordException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.exception.service.user.UserAlreadyExistsException;
import by.tr.web.exception.service.user.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.UserValidator;

public class UserServiceImpl implements UserService {

    @Override
    public User register(String login, String password, String eMail) throws UserServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator registerValidator = validatorFactory.getRegisterValidator();

        boolean isDataValid = registerValidator.validate(login, password, eMail);
        if (!isDataValid) {
            throw new UserServiceException("Unexpected error");
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
            throw new UserServiceException("Error while registering user", ex);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws UserServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator loginValidatorImpl = validatorFactory.getLoginValidator();

        boolean isDataValid = loginValidatorImpl.validate(login, password);

        if (!isDataValid) {
            throw new UserServiceException("Unexpected error");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (!isUserRegistered) {
                throw new NoSuchUserException("No such user");
            } else {
                user = userDAO.login(login, password);
                if (user == null) {
                    throw new UserServiceException("Unexpected error");
                }
            }
        } catch (PasswordDAOException e) {
            throw new IncorrectPasswordException("Incorrect password", e);
        } catch (UserDAOException ex) {
            throw new UserServiceException("Error while login user", ex);
        }
        return user;

    }
}
