package by.tr.web.dao.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.User;
import by.tr.web.domain.UserStatus;
import by.tr.web.exception.dao.ConnectionPoolException;
import by.tr.web.exception.dao.PasswordDAOException;
import by.tr.web.exception.dao.UserDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAOImpl implements UserDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private String REGISTER_QUERY = "INSERT INTO mpb.users (userName, eMail, password, status) " +
            "VALUES (?, ?, MD5(CONCAT(?,CURRENT_TIMESTAMP)), ?)";
    private String GET_USER_QUERY = "SELECT mpb.users.id, mpb.users.eMail, mpb.users.status " +
            "FROM mpb.users WHERE mpb.users.userName = \"";
    private String CHECK_USER_QUERY = "SELECT * FROM mpb.users WHERE mpb.users.userName = \"";
    private String CHECK_PASSWORD_QUERY = "SELECT mpb.users.id FROM mpb.users WHERE mpb.users.userName = \"";

    @Override
    public boolean register(User user) throws UserDAOException {
        if (connectionPool == null) {
            throw new UserDAOException("Failed to init connection pool");
        }
        preparedStatement = null;
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
            throw new UserDAOException("SQL error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public User login(String login, String password) throws UserDAOException {
        if (connectionPool == null) {
            throw new UserDAOException("Failed to init connection pool");
        }
        statement = null;
        resultSet = null;
        try {
            connection = connectionPool.takeConnection();

            boolean passwordCorrect = isPasswordCorrect(connection, login, password);
            if (!passwordCorrect) {
                throw new PasswordDAOException("Unexpected error");
            }

            String getUserQuery = constructQuery(GET_USER_QUERY, login);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getUserQuery);

            User user = null;
            if (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String eMail = resultSet.getString(2);
                String status = resultSet.getString(3);
                UserStatus statusOfUser = UserStatus.valueOf(status.toUpperCase());
                user = formUser(userID, login, eMail, statusOfUser);
            }

            return user;
        } catch (SQLException e) {
            throw new UserDAOException("SQL error", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

    }

    private User formUser(int id, String login, String eMail, UserStatus userStatus) {
        User user = new User();
        user.setId(id);
        user.setUserName(login);
        user.seteMail(eMail);
        user.setStatus(userStatus);
        return user;
    }

    private String constructQuery(String query, String criteria) {
        StringBuilder queryConstructor = new StringBuilder(query);
        queryConstructor.append(criteria);
        queryConstructor.append("\"");
        return queryConstructor.toString();
    }

    private String getPasswordCheckingQuery(String login, String inputPassword) {
        StringBuilder queryConstructor = new StringBuilder(CHECK_PASSWORD_QUERY);
        queryConstructor.append(login);
        queryConstructor.append("\" AND mpb.users.password = MD5(\"");
        queryConstructor.append(inputPassword);
        queryConstructor.append("\")");
        return queryConstructor.toString();
    }

    private boolean isPasswordCorrect(Connection connection, String login, String password) throws SQLException, PasswordDAOException {

        String passwordCheckingQuery = getPasswordCheckingQuery(login, password);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(passwordCheckingQuery);

        return resultSet.next();

    }


    @Override
    public boolean isUserRegistered(String login) throws UserDAOException {
        if (connectionPool == null) {
            throw new UserDAOException("Failed to init connection pool");
        }
        statement = null;
        resultSet = null;

        try {
            connection = connectionPool.takeConnection();

            String checkUserQuery = constructQuery(CHECK_USER_QUERY, login);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(checkUserQuery);

            return resultSet.next();
        } catch (SQLException e) {
            throw new UserDAOException("SQL error", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

}
