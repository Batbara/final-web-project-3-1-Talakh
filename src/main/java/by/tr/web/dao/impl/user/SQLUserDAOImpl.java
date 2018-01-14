package by.tr.web.dao.impl.user;

import by.tr.web.dao.UserDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAOImpl implements UserDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(SQLUserDAOImpl.class);

    private String USER_COUNTER_QUERY =
            "SELECT DISTINCT COUNT(*)" +
            "  FROM mpb.user";

    private String REGISTER_QUERY =
            "INSERT INTO mpb.user (user_name, " +
                    "user_password, user_email) " +
                    "VALUES (?, MD5(?), ?)";

    private String GET_USER_QUERY =
            "SELECT mpb.user.user_id, mpb.user.user_email," +
                    "       mpb.user.user_status " +
                    "  FROM mpb.user " +
                    " WHERE mpb.user.user_name = ?";

    private String GET_USERS_INFO_QUERY =
            "SELECT user_id, user_name," +
            "       user_email, user_status," +
            "       user_is_banned" +
            "  FROM mpb.user" +
            " LIMIT ?, ?";

    private String GET_BAN_INFO =
            "SELECT user_ban_time, user_unban_time," +
                    "       banned_user_ban_reason" +
                    "   FROM mpb.user" +
                    "    INNER JOIN mpb.ban_reason" +
                    "   ON (ban_reason.ban_reason_id = user.user_ban_reason_id)" +
                    " WHERE user_id = ?" +
                    "  AND ban_reason.lang = ?";

    private String GET_BAN_REASONS_QUERY =
            "SELECT ban_reason_id, banned_user_ban_reason" +
            "   FROM mpb.ban_reason" +
            " WHERE lang = ?";

    private String CHECK_USER_QUERY =
            "SELECT user_id" +
                    "   FROM mpb.user" +
                    "  WHERE mpb.user.user_name = ?";

    private String CHECK_PASSWORD_QUERY =
            "SELECT  mpb.user.user_id" +
                    "   FROM mpb.user" +
                    " WHERE mpb.user.user_name = ? " +
                    "  AND mpb.user.user_password = MD5(?)";

    private String CHECK_EMAIL_QUERY =
            "SELECT mpb.user.user_id " +
                    "   FROM mpb.user " +
                    "  WHERE mpb.user.user_email = ?";
    private String BAN_USER_QUERY =
            "UPDATE mpb.user" +
            "   SET user_is_banned = 1," +
            "       user_ban_time = ?," +
            "        user_unban_time = ?," +
            "        user_ban_reason_id = ?" +
            " WHERE user_id = ?";

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

    @Override
    public List<User> takeUserList(int startPosition, int usersNum, String lang) throws UserDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USERS_INFO_QUERY);

            preparedStatement.setInt(1, startPosition);
            preparedStatement.setInt(2, usersNum);

            resultSet = preparedStatement.executeQuery();


            List<User> userList = new ArrayList<>();
            User user;
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String userEmail = resultSet.getString(3);
                String userStatus = resultSet.getString(4);
                boolean isBanned = resultSet.getShort(5) == 1;

                user = new User(userID, userName, userEmail, userStatus, isBanned);
                if (isBanned) {
                    setBanInfo(connection, user, lang);
                }
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new UserDAOException("Error while retrieving users list", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countUsers() throws UserDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(USER_COUNTER_QUERY);

            int userCounter = 0;
            if (resultSet.next()) {
                userCounter = resultSet.getInt(1);
            }
            return userCounter;
        } catch (SQLException e) {
            throw new UserDAOException("Error while executing user counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public void banUser(User user) throws UserDAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(BAN_USER_QUERY);

            BanInfo banInfo = user.getBanInfo();
            preparedStatement.setTimestamp(1, banInfo.getBanTime());
            preparedStatement.setTimestamp(2, banInfo.getUnbanTime());
            preparedStatement.setInt(3, banInfo.getBanReason().getId());
            preparedStatement.setInt(4, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected == 0){
                throw new UserDAOException("Unexpected result from update query");
            }
        } catch (SQLException e) {
            throw new UserDAOException("SQL error while banning user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<BanReason> getBanReasonList(String lang) throws UserDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_BAN_REASONS_QUERY);

            preparedStatement.setString(1, lang);

            resultSet = preparedStatement.executeQuery();
            List<BanReason> banReasonList = new ArrayList<>();
            BanReason banReason;
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String reason = resultSet.getString(2);

                banReason = new BanReason();
                banReason.setId(id);
                banReason.setReason(reason);
                banReasonList.add(banReason);
            }
            return banReasonList;
        } catch (SQLException e) {
            throw new UserDAOException("SQL error while taking ban reasons", e);
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

    private void setBanInfo(Connection connection, User user, String lang) throws UserDAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(GET_BAN_INFO);

            int userID = user.getId();
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            BanInfo banInfo = new BanInfo();
            while (resultSet.next()) {
                Timestamp banTime = resultSet.getTimestamp(1);
                Timestamp unbanTime = resultSet.getTimestamp(2);
                String banReason = resultSet.getString(3);

                banInfo.setBanTime(banTime);
                banInfo.setUnbanTime(unbanTime);
                banInfo.setBanReason(banReason);
            }
            user.setBanInfo(banInfo);
        } catch (SQLException e) {
            throw new UserDAOException("Cannot get ban info", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }


    }

    private boolean isPasswordExisting(Connection connection, String login, String password) throws UserDAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(CHECK_PASSWORD_QUERY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new UserDAOException("Cannot check password existence", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
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
