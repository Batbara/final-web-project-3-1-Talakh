package by.tr.web.service.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.UserDAOException;
import by.tr.web.exception.service.ExceptionMessage;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.exception.service.ValidationException;
import by.tr.web.service.UserService;
import by.tr.web.service.validation.impl.RegisterValidator;

public class UserServiceImpl implements UserService {
    @Override
    public User register(String login, String password, String eMail) throws UserServiceException {
        RegisterValidator registerValidator = new RegisterValidator();
        boolean isDataValid;
        try {
            isDataValid = registerValidator.validate(login, password, eMail);
        } catch (ValidationException e) {
            throw new UserServiceException();
        }
        if (!isDataValid) {
            throw new UserServiceException(ExceptionMessage.UNEXPECTED_ERROR);
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        User user;
        try {
            user=userDAO.register(login, password, eMail);
        } catch (UserDAOException ex){
            throw new UserServiceException(ex);
        }
        return user;
    }

    @Override
    public User login(String login, String password) {
        return null;
    }
}
