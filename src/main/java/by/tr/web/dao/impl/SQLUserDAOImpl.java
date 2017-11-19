package by.tr.web.dao.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.Status;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.ConnectionPoolException;
import by.tr.web.exception.dao.UserDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAOImpl implements UserDAO {
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public User register(String login, String password, String eMail) throws UserDAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new UserDAOException("Failed to get instance of connection pool", e);
        }
        try {
            connection = connectionPool.takeConnection();
            String registerQuery = "INSERT INTO mpb.users (userName, eMail, password, status) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(registerQuery, Statement.RETURN_GENERATED_KEYS);

            User user = new User(login, password, eMail, Status.user);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.geteMail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getStatus().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int userID = rs.getInt(1);

            user.setId(userID);

            return user;
        } catch (ConnectionPoolException e) {
            throw new UserDAOException("Failed to get connection", e);
        } catch (SQLException e) {
            throw new UserDAOException(e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public User login(String login, String password) {
        return null;
    }

    @Override
    public boolean checkUniqueData(String login, String eMail) {
        return false;
    }

    @Override
    public boolean isPasswordConfirmed(String login, String password) {
        return false;
    }
}
