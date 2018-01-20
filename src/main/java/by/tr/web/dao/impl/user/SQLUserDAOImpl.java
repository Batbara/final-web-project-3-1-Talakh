package by.tr.web.dao.impl.user;

import by.tr.web.dao.Configuration;
import by.tr.web.dao.UserDAO;
import by.tr.web.dao.factory.ConfigurationFactory;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.parameter.SqlQueryName;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.Show;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.common.DAOException;
import by.tr.web.exception.dao.user.PasswordDAOException;
import by.tr.web.exception.dao.user.RegistrationDAOException;
import by.tr.web.exception.dao.user.UserInitializaionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();


    @Override
    public boolean register(User user) throws DAOException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String registerQuery = queryConfig.getSqlQuery(SqlQueryName.REGISTER_QUERY);

            preparedStatement = connection.prepareStatement(registerQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.geteMail());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int userID = resultSet.getInt(1);

            user.setId(userID);

            return true;
        } catch (SQLException e) {
            throw new RegistrationDAOException("Failed to register user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public User login(String login, String password, String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            boolean existingPassword = isPasswordExisting(connection, login, password);
            if (!existingPassword) {
                throw new PasswordDAOException("No such password in data base");
            }

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String takeUserQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_USER_QUERY);

            preparedStatement = connection.prepareStatement(takeUserQuery);

            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            User user = null;
            boolean isBanned = false;
            if (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String eMail = resultSet.getString(2);
                String status = resultSet.getString(3);
                isBanned = resultSet.getShort(4) == 1;
                Timestamp regDate = resultSet.getTimestamp(5);

                user = new User(userID, login, eMail, status, isBanned, regDate);
            }
            if (isBanned) {
                setBanInfo(connection, user, lang);
            }
            setUserReviews(connection, user);
            return user;
        } catch (SQLException e) {
            throw new DAOException("Failed to login user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

    }

    @Override
    public boolean isUserRegistered(String login) throws DAOException {
        Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
        String checkUserExistQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_USER_EXIST_QUERY);

        return checkForRegistration(checkUserExistQuery, login);
    }

    @Override
    public boolean isEmailRegistered(String eMail) throws DAOException {
        Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
        String checkEmailQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_EMAIL_QUERY);

        return checkForRegistration(checkEmailQuery, eMail);
    }

    @Override
    public List<User> takeUserList(int startPosition, int usersNum, String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String takeUserList = queryConfig.getSqlQuery(SqlQueryName.TAKE_USER_LIST);

            preparedStatement = connection.prepareStatement(takeUserList);

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
                Timestamp regDate = resultSet.getTimestamp(6);

                user = new User(userID, userName, userEmail, userStatus, isBanned, regDate);
                if (isBanned) {
                    setBanInfo(connection, user, lang);
                }
                setUserReviews(connection, user);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DAOException("Error while retrieving users list", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countUsers() throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String userCounterQuery = queryConfig.getSqlQuery(SqlQueryName.USER_COUNTER_QUERY);

            resultSet = statement.executeQuery(userCounterQuery);

            int userCounter = 0;
            if (resultSet.next()) {
                userCounter = resultSet.getInt(1);
            }
            return userCounter;
        } catch (SQLException e) {
            throw new DAOException("Error while executing user counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public void banUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String banUserQuery = queryConfig.getSqlQuery(SqlQueryName.BAN_USER_QUERY);

            preparedStatement = connection.prepareStatement(banUserQuery);

            BanInfo banInfo = user.getBanInfo();
            preparedStatement.setTimestamp(1, banInfo.getBanTime());
            preparedStatement.setTimestamp(2, banInfo.getUnbanTime());
            preparedStatement.setInt(3, banInfo.getBanReason().getId());
            preparedStatement.setInt(4, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Unexpected result from update query");
            }
        } catch (SQLException e) {
            throw new DAOException("SQL error while banning user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<BanReason> getBanReasonList(String lang) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String takeBanReasonsQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_BAN_REASONS_QUERY);

            preparedStatement = connection.prepareStatement(takeBanReasonsQuery);

            preparedStatement.setString(1, lang);

            resultSet = preparedStatement.executeQuery();
            List<BanReason> banReasonList = new ArrayList<>();
            BanReason banReason;
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String reason = resultSet.getString(2);

                banReason = new BanReason();
                banReason.setId(id);
                banReason.setReason(reason);
                banReasonList.add(banReason);
            }
            return banReasonList;
        } catch (SQLException e) {
            throw new DAOException("SQL error while taking ban reasons", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void unbanUser(int userID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String unbanUserQuery = queryConfig.getSqlQuery(SqlQueryName.UNBAN_USER_QUERY);

            preparedStatement = connection.prepareStatement(unbanUserQuery);
            preparedStatement.setInt(1, userID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Unexpected result from update query");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to execute user unban query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private void setBanInfo(Connection connection, User user, String lang) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String takeBanInfo = queryConfig.getSqlQuery(SqlQueryName.TAKE_BAN_INFO);

            preparedStatement = connection.prepareStatement(takeBanInfo);

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
                banInfo.setBanReason(new BanReason(banReason));
            }
            user.setBanInfo(banInfo);
        } catch (SQLException e) {
            throw new UserInitializaionException("Cannot get ban info", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private void setUserReviews(Connection connection, User user) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String takeUserReviewsQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_USER_REVIEWS_QUERY);

            preparedStatement = connection.prepareStatement(takeUserReviewsQuery);

            int userID = user.getId();
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            List<UserReview> reviewList = new ArrayList<>();
            UserReview review;
            while (resultSet.next()) {
                int showId = resultSet.getInt(1);
                int userRate = resultSet.getInt(2);
                String reviewContent = resultSet.getString(3);
                Timestamp reviewPostDate = resultSet.getTimestamp(4);

                review = new UserReview();
                Show show = new Show();
                show.setShowID(showId);

                review.setShow(show);
                review.setUser(user);
                review.setUserRate(userRate);
                if (reviewContent != null) {
                    review.setReviewContent(reviewContent);
                    review.setPostDate(reviewPostDate);
                }
                reviewList.add(review);
            }
            user.setUserReviews(reviewList);
        } catch (SQLException e) {
            throw new UserInitializaionException("Cannot get list of user reviews", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private boolean isPasswordExisting(Connection connection, String login, String password) throws DAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Configuration queryConfig = ConfigurationFactory.getInstance().getUserQueryConfig();
            String checkPasswordQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_PASSWORD_QUERY);

            preparedStatement = connection.prepareStatement(checkPasswordQuery);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new PasswordDAOException("Cannot check password existence", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private boolean checkForRegistration(String query, String userName) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            String errorMessage = "Cannot check if \'" + userName + "\' is registered";
            throw new RegistrationDAOException(errorMessage, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

}
