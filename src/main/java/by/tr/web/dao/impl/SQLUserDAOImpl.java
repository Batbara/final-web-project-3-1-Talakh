package by.tr.web.dao.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.Status;
import by.tr.web.domain.User;
import by.tr.web.exception.dao.ConnectionPoolException;
import by.tr.web.exception.dao.PasswordDAOException;
import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.dao.UserDAOException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAOImpl implements UserDAO {
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public boolean register(User user) throws UserDAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new UserDAOException("Failed to get instance of connection pool", e);
        }
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String registerQuery = "INSERT INTO mpb.users (userName, eMail, password, status) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(registerQuery, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.geteMail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getStatus().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int userID = rs.getInt(1);

            user.setId(userID);

            return true;
        } catch (ConnectionPoolException e) {
            throw new UserDAOException("Failed to get connection", e);
        } catch (SQLException e) {
            throw new UserDAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public User login(String login, String password) throws UserDAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new UserDAOException("Failed to get instance of connection pool", e);
        }
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String getPasswordQuery = "SELECT mpb.users.password FROM mpb.users WHERE mpb.users.userName = \"" + login + "\"";
            st = connection.createStatement();
            rs = st.executeQuery(getPasswordQuery);

            String storedPassword;
            if(rs.next()) {
                storedPassword = rs.getString(1);

                if (!checkPassword(password, storedPassword)) {
                    throw new PasswordDAOException();
                }
            }

            String getUserQuery = "SELECT mpb.users.id, mpb.users.eMail, mpb.users.status " +
                    "FROM mpb.users WHERE mpb.users.userName = \"" + login + "\"";
            rs = st.executeQuery(getUserQuery);
            User user = null;
            if(rs.next()) {
                int userID = rs.getInt(1);
                String eMail = rs.getString(2);
                String status = rs.getString(3);
                Status userStatus = Status.valueOf(status);
                user =  formUser(userID, login, eMail, userStatus);
            }

            return user;
        } catch (SQLException e) {
            throw new UserDAOException("SQL errors");
        } finally {
            connectionPool.closeConnection(connection, st, rs);
        }

    }

    private User formUser(int id, String login, String eMail, Status status) {
        User user = new User();
        user.setId(id);
        user.setUserName(login);
        user.seteMail(eMail);
        user.setStatus(status);
        return user;
    }

    private boolean checkPassword(String userPassword, String storedHash) throws PasswordDAOException {
        boolean isPasswordCorrect;
        String algorithmVersion = "$2a$";
        if (null == storedHash || !storedHash.startsWith(algorithmVersion)) {
            throw new PasswordDAOException();
        }

        isPasswordCorrect = BCrypt.checkpw(userPassword, storedHash);

        return isPasswordCorrect;
    }

    @Override
    public boolean isUserRegistered(String login) throws UserDAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new UserDAOException("Failed to get instance of connection pool", e);
        }
        Statement st = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.takeConnection();

            String checkUserQuery = "SELECT * FROM mpb.users WHERE mpb.users.userName = \"" + login + "\"";
            st = connection.createStatement();
            rs = st.executeQuery(checkUserQuery);

            return rs.next();
        } catch (SQLException e) {
            throw new UserDAOException(e);
        } finally {
            connectionPool.closeConnection(connection, st, rs);
        }
    }

}
