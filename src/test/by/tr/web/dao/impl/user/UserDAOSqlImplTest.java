package by.tr.web.dao.impl.user;

import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.user.PasswordDAOException;
import by.tr.web.dao.user.UserDAOSqlImpl;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.service.input_validator.RequestParameterNotFound;
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


public class UserDAOSqlImplTest {
    private UserDAOSqlImpl userDAO;
    private static ConnectionPool connectionPool;
    private final static String DEFAULT_TIME_PATTERN = "yyyy-MM-dd'T'hh:mm:ss";

    private static final String DELETE_USER_QUERY = "DELETE FROM test.user WHERE user_name = ?";
    public static final String SET_USER_STATUS = "UPDATE user SET user_status = ? WHERE user_id = ?";
    private static final String GET_USER_QUERY = "SELECT user.user_id, user.user_email, " +
            "        user.user_status, user.user_is_banned, " +
            "        user.user_reg_date" +
            "     FROM  user" +
            "       WHERE user_name = ?";
    private static final String REMOVE_BAN_QUERY = "UPDATE  user " +
            "  SET user_is_banned = 0," +
            "     user_ban_time = NULL," +
            "     user_ban_reason_id = NULL" +
            "   WHERE user_name = ?";
    private static final String SET_BAN_QUERY = "UPDATE  user" +
            " SET user_is_banned = 1," +
            "    user_ban_time = ?," +
            "    user_ban_reason_id = ?" +
            "  WHERE user_id = ?";
    private static final String TAKE_BAN_INFO = "SELECT user_ban_time, " +
            "                            ban_reason_id, banned_user_ban_reason " +
            "                      FROM  user" +
            "                       INNER JOIN  ban_reason" +
            "                         ON (ban_reason.ban_reason_id = user.user_ban_reason_id)" +
            "                       WHERE user_id = ?" +
            "                         AND ban_reason.lang = 'ru'";

    @Before
    public void setUp() {
        userDAO = new UserDAOSqlImpl();

    }

    @BeforeClass
    public static void initConnectionPool() {
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @AfterClass
    public static void destroyConnectionPool() {
        connectionPool.dispose();
    }

    @Test
    public void testSuccessfulRegistration() throws DAOException, SQLException {
        String userName = "PusheenTheCat";
        String email = "fluffy.moor@gmail.com";
        String password = "catzDaBest!2007";

        User user = new UserBuilder()
                .addUserName(userName)
                .addEmail(email)
                .addPassword(password)
                .create();

        boolean expected = true;
        boolean actual = userDAO.register(user);
        try {
            Assert.assertEquals(expected, actual);
        } finally {
            deleteAddedUser(userName);
        }
    }

    @Test
    public void testUserSuccessfulLogin() throws DAOException, ParseException, RequestParameterNotFound {
        String userName = "Maggie";
        String password = "12345";
        String lang = "ru";
        Timestamp regDate = TypeFormatUtil.getTimestampFromString("2018-01-19T19:19:36", DEFAULT_TIME_PATTERN);

        User expectedUser = new UserBuilder()
                .addId(37)
                .addUserName(userName)
                .addEmail("maggie.simpson@gmail.com")
                .addUserStatus("CASUAL_VIEWER")
                .addRegistrationDate(regDate)
                .addBanStatus(false)
                .create();
        Review rate = new UserReviewBuilder()
                .addUser(expectedUser)
                .addShowId(8)
                .addUserRate(8)
                .create();

        Review review = new UserReviewBuilder()
                .addUser(expectedUser)
                .addShowId(9)
                .addUserRate(8)
                .addReviewTitle("DOH!")
                .addReviewContent("\n" +
                        "«Симпсоны» — самый лучший мультик, который я когда-либо смотрела. Они подходят для всех возрастов. Независимо от того, сколько Вам лет, Вы будете улыбаться и смеяться, когда смотрите этот мультик.\n" +
                        "\n" +
                        "Знакомые нам всем герои оказываются в очередной смешной передряге, из которой умудряются, как всегда, выйти с улыбками на лицах.\n" +
                        "\n" +
                        "Если хочется от души повеселиться и приятно провести время, посмотрите «Симпсонов».")
                .addPostDate(TypeFormatUtil.getTimestampFromString("2018-01-28T17:17:07", DEFAULT_TIME_PATTERN))
                .addReviewStatus("posted")
                .create();

        expectedUser.addUserReview(rate);
        expectedUser.addUserReview(review);

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
    public void testCountUsers() throws DAOException {
        int expected = 32;
        int actual = userDAO.countUsers();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSuccessfulBanUser() throws SQLException, DAOException {
        String userName = "ThePond";

        BanInfo banInfo = new BanInfo();
        banInfo.setBanReason(new BanReason(3));
        banInfo.setBanTime(new Timestamp(System.currentTimeMillis()));

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

    @Test
    public void testSuccessfulUserStatusChange() throws SQLException, DAOException {

        int userId = 25;
        String oldStatus = "casual_viewer";
        try {

            userDAO.changeUserStatus(userId, "reviewer");
            User changedUser = getUser("crazyPanda");

            User.UserStatus expectedStatus = User.UserStatus.REVIEWER;
            User.UserStatus actualStatus = changedUser.getUserStatus();

            Assert.assertEquals(expectedStatus, actualStatus);
        } finally {
            resetStatus(userId, oldStatus);
        }
    }

    private void resetStatus(int userId, String status) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SET_USER_STATUS);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();

        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
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

                user = new UserBuilder()
                        .addId(userID)
                        .addUserName(userName)
                        .addEmail(userEmail)
                        .addUserStatus(userStatus)
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
                int banReasonId = resultSet.getInt(2);
                String banReason = resultSet.getString(3);

                banInfo.setBanTime(banTime);
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
            preparedStatement.setInt(2, banInfo.getBanReason().getId());
            preparedStatement.setInt(3, user.getId());

            preparedStatement.executeUpdate();

        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }
}