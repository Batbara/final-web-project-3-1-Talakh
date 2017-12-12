package by.tr.web.dao.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.PasswordDAOException;
import by.tr.web.exception.dao.UserDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAOImpl implements UserDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();


    private String REGISTER_QUERY = "INSERT INTO mpb.users (userName, eMail, password, status) " +
            "VALUES (?, ?, MD5(CONCAT(?,CURRENT_TIMESTAMP)), ?)";
    private String GET_USER_QUERY = "SELECT mpb.users.id, mpb.users.eMail, mpb.users.status " +
            "FROM mpb.users WHERE mpb.users.userName = ?";
    private String CHECK_USER_QUERY = "SELECT * FROM mpb.users WHERE mpb.users.userName = ?";
    private String CHECK_PASSWORD_QUERY = "SELECT mpb.users.id FROM mpb.users WHERE mpb.users.userName = ? " +
            "AND mpb.users.password = MD5(?)";

    @Override
    public boolean register(User user) throws UserDAOException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REGISTER_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.geteMail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserStatus().name());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int userID = resultSet.getInt(1);

            user.setId(userID);

            return true;
        } catch (SQLException e) {
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

            boolean passwordCorrect = isPasswordCorrect(connection, login, password);
            if (!passwordCorrect) {
                throw new PasswordDAOException("Unexpected error");
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
            throw new UserDAOException("Failed to login user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

    }

    private User formUser(int id, String login, String eMail, String userStatus) {
        User user = new User();
        user.setId(id);
        user.setUserName(login);
        user.seteMail(eMail);
        user.setUserStatus(userStatus);
        return user;
    }

    private boolean isPasswordCorrect(Connection connection, String login, String password) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PASSWORD_QUERY);

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();

    }


    @Override
    public boolean isUserRegistered(String login) throws UserDAOException {

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CHECK_USER_QUERY);

            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new UserDAOException("Can't check user registration", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

}
