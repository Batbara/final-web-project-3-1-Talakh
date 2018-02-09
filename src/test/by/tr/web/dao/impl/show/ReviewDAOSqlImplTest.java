package by.tr.web.dao.impl.show;

import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.show.ReviewDAOImpl;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.UserReviewBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAOSqlImplTest {
    private ReviewDAOImpl reviewDAO = new ReviewDAOImpl();
    private static ConnectionPool connectionPool;
    private final static String DELETE_USER_REVIEW = "DELETE FROM `test`.`review` WHERE `user_id`=? AND`show_id`=?";
    private static final String GET_USER_RATE = "SELECT review_rate FROM `test`.`review` WHERE `user_id`=? AND`show_id`=?";
    private static final String GET_USER_REVIEW = "SELECT review_title, review_content " +
            "   FROM review " +
            "    WHERE user_id = ? " +
            "   AND show_id = ? ";

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
    public void testRateShowSuccessfully() throws DAOException, SQLException {
        int userId = 41;
        int showId = 10;
        int userRate = 8;
        Review rate = new UserReviewBuilder()
                .addUser(new User(userId))
                .addShowId(showId)
                .addUserRate(userRate)
                .create();
        try {
            reviewDAO.rateShow(rate);
            int actualRate = getUserRate(userId, showId);

            Assert.assertEquals(userRate, actualRate);
        } finally {
            deleteUserReview(userId, showId);
        }
    }

    @Test
    public void testAddUserReviewSuccessfully() throws DAOException, SQLException {
        int userId = 19;
        int showId = 3;
        Review expectedReview = new UserReviewBuilder()
                .addUser(new User(userId))
                .addShowId(showId)
                .addReviewTitle("Boring Movie")
                .addReviewContent("A waste of money and time, I've never seen a movie this awful. " +
                        "I should have spend my time on something worthwhile than watching this bad movie. \n" +
                        "\n" +
                        "The scenes are very simple and made for TV not the big screen. " +
                        "The only actress who have done her job is Vanessa Redgrave. " +
                        "Other actors are just like having a vacation from acting, I'm very disappointed on the performance of " +
                        "Forest Whitaker. I think this is his worst movie ever. \n" +
                        "\n" +
                        "Oprah Winfrey should never try acting, she's like a wrestler from WWE trying to act. " +
                        "Very unnatural. \n" +
                        "\n" +
                        "The producers of this film just bet on the cameos of famous actors to get" +
                        " this film afloat. Worst film I have ever watched. Don't waste your money on it just wait it " +
                        "to be shown on free TV, not worth it to go cinemas and fork out money for it.")
                .create();
        try {
            reviewDAO.addReview(expectedReview);
            Review actualReview = getUserReview(userId, showId);
            Assert.assertEquals(actualReview, expectedReview);
        } finally {
            deleteUserReview(userId, showId);
        }
    }

    private Review getUserReview(int userId, int showId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(GET_USER_REVIEW);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);
            resultSet = preparedStatement.executeQuery();
            Review review = null;
            if (resultSet.next()) {
                review = new UserReviewBuilder()
                        .addUser(new User(userId))
                        .addShowId(showId)
                        .addReviewContent(resultSet.getString(1))
                        .addReviewContent(resultSet.getString(2))
                        .create();
            }
            return review;
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private int getUserRate(int userId, int showId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(GET_USER_RATE);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private void deleteUserReview(int userId, int showId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(DELETE_USER_REVIEW);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, showId);
            preparedStatement.executeUpdate();

        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }
}
