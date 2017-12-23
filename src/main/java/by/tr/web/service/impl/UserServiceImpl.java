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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User register(String login, String password, String eMail) throws UserServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator registerValidator = validatorFactory.getRegisterValidator();

        boolean isDataValid = registerValidator.validateCredentials(login, password, eMail);
        if (!isDataValid) {
            logger.log(Level.ERROR, "Unexpected error while validating user register credentials");
            throw new UserServiceException("Unexpected error while validating user register credentials");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (isUserRegistered) {
                logger.log(Level.ERROR, "User " + login + " already exists");
                throw new UserAlreadyExistsException("User " + login + " already exists");
            }
            boolean isEmailRegistered = userDAO.isEmailRegistered(eMail);
            if (isEmailRegistered) {
                logger.log(Level.ERROR, "E-mail " + eMail + " is already registered");
                throw new EMailAlreadyRegisteredException("E-mail " + eMail + " is already registered");
            } else {
                user = new User(login, password, eMail);
                userDAO.register(user);
            }
        } catch (UserDAOException ex) {
            logger.error("Error while registering user", ex);
            throw new UserServiceException("Error while registering user", ex);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws UserServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        UserValidator loginValidatorImpl = validatorFactory.getLoginValidator();

        boolean isDataValid = loginValidatorImpl.validateCredentials(login, password);

        if (!isDataValid) {
            logger.log(Level.ERROR, "Unexpected error while validating user login credentials");
            throw new UserServiceException("Unexpected error while validating user login credentials");
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            boolean isUserRegistered = userDAO.isUserRegistered(login);
            if (!isUserRegistered) {
                logger.log(Level.ERROR, "No such user as "+login);
                throw new NoSuchUserException("No such user as "+login);
            } else {
                user = userDAO.login(login, password);
                if (user == null) {
                    logger.log(Level.ERROR, "Unexpected logging error");
                    throw new UserServiceException("Unexpected logging error");
                }
            }
        } catch (PasswordDAOException e) {
            logger.error("Incorrect password", e);
            throw new IncorrectPasswordException("Incorrect password", e);
        } catch (UserDAOException ex) {
            logger.error("Error while login user", ex);
            throw new UserServiceException("Error while login user", ex);
        }
        return user;

    }
}
