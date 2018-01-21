package by.tr.web.dao.impl.user;

import by.tr.web.controller.constant.Util;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.user.PasswordDAOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;


public class SQLUserDAOImplTest {
    private SQLUserDAOImpl userDAO;
    private static ConnectionPool connectionPool;
    private final static String dateTimePattern = "yyyy-MM-dd'T'hh:mm:ss";

    private static final String DELETE_USER_QUERY = "DELETE FROM test.user WHERE user_name = ?";
    private static final String GET_USER_QUERY = "SELECT user.user_id, user.user_email, " +
            "        user.user_status, user.user_is_banned, " +
            "        user.user_reg_date" +
            "     FROM  user" +
            "       WHERE user_name = ?";
    private static final String REMOVE_BAN_QUERY = "UPDATE  user " +
            "  SET user_is_banned = 0," +
            "     user_ban_time = NULL," +
            "     user_unban_time = NULL," +
            "     user_ban_reason_id = NULL" +
            "   WHERE user_name = ?";
    private static final String SET_BAN_QUERY = "UPDATE  user" +
            " SET user_is_banned = 1," +
            "    user_ban_time = ?," +
            "    user_unban_time = ?, " +
            "    user_ban_reason_id = ?" +
            "  WHERE user_id = ?";
    private static final String TAKE_BAN_INFO = "SELECT user_ban_time, user_unban_time," +
            "                            ban_reason_id, banned_user_ban_reason " +
            "                      FROM  user" +
            "                       INNER JOIN  ban_reason" +
            "                         ON (ban_reason.ban_reason_id = user.user_ban_reason_id)" +
            "                       WHERE user_id = ?" +
            "                         AND ban_reason.lang = 'ru'";

    @Before
    public void setUp() {
        userDAO = new SQLUserDAOImpl();

    }

    @BeforeClass
    public static void initConnectionPool() {
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @AfterClass
    public static void destroyConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
    }

    @Test
    public void testSuccessfulRegistration() throws DAOException, SQLException {
        String userName = "PusheenTheCat";
        String eMail = "fluffy.moor@gmail.com";
        String password = "catzDaBest!2007";

        User user = new User();
        user.setUserName(userName);
        user.setEmail(eMail);
        user.setPassword(password);

        boolean expected = true;
        boolean actual = userDAO.register(user);
        try {
            Assert.assertEquals(expected, actual);
        } finally {
            deleteAddedUser(userName);
        }
    }

    @Test
    public void testUserSuccessfulLogin() throws DAOException, ParseException {
        String userName = "Maggie";
        String password = "12345";
        String lang = "ru";

        User expectedUser = new User();

        expectedUser.setId(37);
        expectedUser.setUserName(userName);
        expectedUser.setEmail("maggie.simpson@gmail.com");
        expectedUser.setStatus(User.UserStatus.CASUAL_VIEWER);

        Timestamp regDate = Util.getTimeFromString("2018-01-19T19:19:36", dateTimePattern);
        expectedUser.setRegistrationDate(regDate);

        expectedUser.setIsBanned(false);

        User actualUser = userDAO.login(userName, password, lang);

        boolean expected = true;
        boolean actual = actualUser.equals(expectedUser);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = PasswordDAOException.class)
    public void testUserFailedLogin() throws DAOException {
        String userName = "Maggie";
        String password = "lalala";
        String lang = "ru";
        userDAO.login(userName, password, lang);
    }

    @Test
    public void testIsUserRegistered() throws DAOException {
        String login = "fakeUSERname";
        boolean expected = false;
        boolean actual = userDAO.isUserRegistered(login);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsEmailRegistered() throws DAOException {
        String email = "talahbarbara@gmail.com";
        boolean expected = true;
        boolean actual = userDAO.isEmailRegistered(email);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCountUsers() throws DAOException {
        int expected = 10;
        int actual = userDAO.countUsers();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSuccessfulBanUser() throws SQLException, DAOException {
        String userName = "ThePond";

        BanInfo banInfo = new BanInfo();
        banInfo.setBanReason(new BanReason(3));
        banInfo.setBanTime(new Timestamp(System.currentTimeMillis()));
        banInfo.setUnbanTime(new Timestamp(System.currentTimeMillis() + 100000000L));

        User user = new UserBuilder()
                .addId(27)
                .addUserName(userName)
                .addBanInfo(banInfo)
                .create();

        userDAO.banUser(user);

        boolean expectedBanned = true;
        User actualUser = getUser(userName);
        boolean actual = actualUser.getIsBanned();
        try {
            Assert.assertEquals(expectedBanned, actual);
        } finally {
            removeBanFromUser(userName);
        }
    }

    @Test
    public void testSuccessfulUnbanUser() throws DAOException, SQLException {
        String userName = "Amelia";
        User user = getUser(userName);

        userDAO.unbanUser(user.getId());

        User userAfterUnban = getUser(userName);

        boolean expectedBanned = false;
        boolean actual = userAfterUnban.getIsBanned();

        try {
            Assert.assertEquals(expectedBanned, actual);
        } finally {
            setBanToUser(user);
        }
    }

    private User getUser(String userName) throws SQLException, DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_QUERY);
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();

            User user = null;
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String userEmail = resultSet.getString(2);
                String userStatus = resultSet.getString(3);
                boolean isBanned = resultSet.getShort(4) == 1;
                Timestamp regDate = resultSet.getTimestamp(5);

             //   user = new User(userID, userName, userEmail, userStatus, isBanned, regDate);
                user = new UserBuilder()
                        .addId(userID)
                        .addUserName(userName)
                        .addEmail(userEmail)
                        .addUserStatus(User.UserStatus.valueOf(userStatus.toUpperCase()))
                        .addBanStatus(isBanned)
                        .addRegistrationDate(regDate)
                        .create();
                if (isBanned) {
                    setBanInfo(connection, user);
                }
            }
            return user;
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private void setBanInfo(Connection connection, User user) throws DAOException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(TAKE_BAN_INFO);

            int userID = user.getId();
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();
            BanInfo banInfo = new BanInfo();
            while (resultSet.next()) {
                Timestamp banTime = resultSet.getTimestamp(1);
                Timestamp unbanTime = resultSet.getTimestamp(2);
                int banReasonId = resultSet.getInt(3);
                String banReason = resultSet.getString(4);

                banInfo.setBanTime(banTime);
                banInfo.setUnbanTime(unbanTime);
                banInfo.setBanReason(new BanReason(banReasonId, banReason));
            }
            user.setBanInfo(banInfo);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private void deleteAddedUser(String userName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);

            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();

        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private void removeBanFromUser(String userName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();


            preparedStatement = connection.prepareStatement(REMOVE_BAN_QUERY);
            preparedStatement.setString(1, userName);

            preparedStatement.executeUpdate();

        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private void setBanToUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(SET_BAN_QUERY);

            BanInfo banInfo = user.getBanInfo();

            preparedStatement.setTimestamp(1, banInfo.getBanTime());
            preparedStatement.setTimestamp(2, banInfo.getUnbanTime());
            preparedStatement.setInt(3, banInfo.getBanReason().getId());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();

        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }
}