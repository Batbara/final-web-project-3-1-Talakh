package by.tr.web.dao.impl;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.UserStatus;
import by.tr.web.domain.User;
import by.tr.web.exception.ExceptionMessage;
import by.tr.web.exception.dao.ConnectionPoolException;
import by.tr.web.exception.dao.PasswordDAOException;
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
            throw new UserDAOException(ExceptionMessage.CONNECTION_POOL_INSTANCE_FAILURE, e);
        }
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String registerQuery = "INSERT INTO mpb.users (userName, eMail, password, status) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(registerQuery, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.geteMail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserStatus().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int userID = rs.getInt(1);

            user.setId(userID);

            return true;
        } catch (ConnectionPoolException e) {
            throw new UserDAOException(ExceptionMessage.CONNECTION_FAILURE, e);
        } catch (SQLException e) {
            throw new UserDAOException(ExceptionMessage.SQL_ERROR, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public User login(String login, String password) throws UserDAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new UserDAOException(ExceptionMessage.CONNECTION_POOL_INSTANCE_FAILURE, e);
        }
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();

            boolean passwordCorrect = isPasswordCorrect(connection, login,password);
            if(!passwordCorrect){
                throw new UserDAOException(ExceptionMessage.UNEXPECTED_ERROR);
            }

            String getUserQuery = "SELECT mpb.users.id, mpb.users.eMail, mpb.users.status " +
                    "FROM mpb.users WHERE mpb.users.userName = \"" + login + "\"";
            st = connection.createStatement();
            rs = st.executeQuery(getUserQuery);
            User user = null;
            if (rs.next()) {
                int userID = rs.getInt(1);
                String eMail = rs.getString(2);
                String status = rs.getString(3);
                UserStatus statusOfUser = UserStatus.valueOf(status);
                user = formUser(userID, login, eMail, statusOfUser);
            }

            return user;
        } catch (SQLException e) {
            throw new UserDAOException(ExceptionMessage.SQL_ERROR, e);
        } finally {
            connectionPool.closeConnection(connection, st, rs);
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
    private boolean isPasswordCorrect(Connection connection, String login, String password) throws SQLException, PasswordDAOException {

        String getPasswordQuery = "SELECT mpb.users.password FROM mpb.users WHERE mpb.users.userName = \"" + login + "\"";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getPasswordQuery);

        String storedPassword;
        if (rs.next()) {
            storedPassword = rs.getString(1);

            if (!checkPassword(password, storedPassword)) {
                throw new PasswordDAOException();
            }
        }
        return true;

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
            throw new UserDAOException(ExceptionMessage.CONNECTION_POOL_INSTANCE_FAILURE, e);
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
            throw new UserDAOException(ExceptionMessage.SQL_ERROR, e);
        } finally {
            connectionPool.closeConnection(connection, st, rs);
        }
    }

}
