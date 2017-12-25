package by.tr.web.dao.impl.user;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.user.PasswordDAOException;
import by.tr.web.exception.dao.user.UserDAOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAOImpl implements UserDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(SQLUserDAOImpl.class);

    private String REGISTER_QUERY = "INSERT INTO mpb.user (user_name, user_password,user_email) " +
            "VALUES (?, MD5(CONCAT(?,CURRENT_TIMESTAMP)), ?)";
    private String GET_USER_QUERY = "SELECT mpb.user.user_id, mpb.user.user_email, mpb.user.user_status " +
            "FROM mpb.user WHERE mpb.user.user_name = ?";
    private String CHECK_USER_QUERY = "SELECT * FROM mpb.user WHERE mpb.user.user_name = ?";
    private String CHECK_PASSWORD_QUERY = "SELECT  mpb.user.user_id FROM mpb.user WHERE mpb.user.user_name = ? " +
            "AND mpb.user.user_password = MD5(?)";
    private String CHECK_EMAIL_QUERY = "SELECT mpb.user.user_id FROM mpb.user WHERE mpb.user.user_email = ?";

    @Override
    public boolean register(User user) throws UserDAOException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REGISTER_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.geteMail());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int userID = resultSet.getInt(1);

            user.setId(userID);
           // user.setStatus(User.UserStatus.CASUAL_VIEWER);

            return true;
        } catch (SQLException e) {
            logger.error("Failed to register user", e);
            throw new UserDAOException("Failed to register user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public User login(String login, String password) throws UserDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            boolean existingPassword = isPasswordExisting(connection, login, password);
            if (!existingPassword) {
                logger.log(Level.WARN, "No such password in data base");
                throw new PasswordDAOException("No such password in data base");
            }

            preparedStatement = connection.prepareStatement(GET_USER_QUERY);

            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String eMail = resultSet.getString(2);
                String status = resultSet.getString(3);
                user = formUser(userID, login, eMail, status);
            }

            return user;
        } catch (SQLException e) {
            logger.error("Failed to login user", e);
            throw new UserDAOException("Failed to login user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

    }

    @Override
    public boolean isUserRegistered(String login) throws UserDAOException {
        return checkForRegistration(CHECK_USER_QUERY, login);
    }

    @Override
    public boolean isEmailRegistered(String eMail) throws UserDAOException {
        return checkForRegistration(CHECK_EMAIL_QUERY, eMail);
    }
    private User formUser(int id, String login, String eMail, String userStatus) {
        User user = new User();
        user.setId(id);
        user.setUserName(login);
        user.seteMail(eMail);
        user.setUserStatus(userStatus);
        return user;
    }
    private boolean isPasswordExisting(Connection connection, String login, String password) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PASSWORD_QUERY);

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();

    }
    private boolean checkForRegistration(String query, String parameter) throws UserDAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, parameter);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            String errorMessage = "Cannot check if \'" + parameter + "\' is registered";
            logger.error(errorMessage, e);
            throw new UserDAOException(errorMessage, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

}
