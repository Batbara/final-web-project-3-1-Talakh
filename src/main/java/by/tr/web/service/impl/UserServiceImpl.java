package by.tr.web.service.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.PasswordDAOException;
import by.tr.web.exception.dao.UserDAOException;
import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.service.NoSuchUserException;
import by.tr.web.exception.service.UserAlreadyExistsException;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.impl.LoginValidatorImpl;
import by.tr.web.service.validation.impl.RegisterValidatorImpl;

public class UserServiceImpl implements UserService {

    @Override
    public User register(String login, String password, String eMail) throws UserServiceException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        RegisterValidatorImpl registerValidator =(RegisterValidatorImpl) validatorFactory.getRegisterValidator();

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
                throw new UserAlreadyExistsException("User already exists");
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
        LoginValidatorImpl loginValidatorImpl =(LoginValidatorImpl) validatorFactory.getLoginValidator();

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
