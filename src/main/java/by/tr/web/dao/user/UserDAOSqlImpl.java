package by.tr.web.dao.user;

import by.tr.web.dao.configuration.Configuration;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.configuration.QueryConfigurationFactory;
import by.tr.web.dao.configuration.SqlQueryName;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.domain.builder.UserReviewBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDAOSqlImpl implements UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();


    @Override
    public boolean register(User user) throws DAOException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            String userName = user.getUserName();
            String password = user.getPassword();
            String eMail = user.getEmail();

            boolean isUserRegistered;
            try {
                isUserRegistered = checkUsernameForRegistration(connection, userName);
            } catch (UserNotRegisteredException e) {
                isUserRegistered = false;
            }

            if (isUserRegistered) {
                throw new RegistrationDAOException("Cannot register user '" + userName + "'");
            }
            checkEmailForRegistration(connection, eMail);

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String registerQuery = queryConfig.getSqlQuery(SqlQueryName.REGISTER_QUERY);

            preparedStatement = connection.prepareStatement(registerQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, eMail);

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
            checkUsernameForRegistration(connection, login);
            checkPasswordMatch(connection, login, password);

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String takeUserQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_USER_QUERY);

            preparedStatement = connection.prepareStatement(takeUserQuery);

            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            User user = null;
            boolean isBanned = false;
            if (resultSet.next()) {

                int userID = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String status = resultSet.getString(3);
                isBanned = resultSet.getShort(4) == 1;
                Timestamp regDate = resultSet.getTimestamp(5);

                user = new UserBuilder()
                        .addId(userID)
                        .addUserName(login)
                        .addEmail(email)
                        .addUserStatus(status)
                        .addBanStatus(isBanned)
                        .addRegistrationDate(regDate)
                        .create();

                setUserReviews(connection, user);
            }
            if (isBanned) {
                setBanInfo(connection, user, lang);
            }

            return user;
        } catch (SQLException e) {
            throw new DAOException("Failed to login user '" + login + "'", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

    }

    @Override
    public User updateReviewList(User user) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            setUserReviews(connection, user);
            return user;
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<User> takeUserList(int startPosition, int usersNum, String lang) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
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

                user = new UserBuilder()
                        .addId(userID)
                        .addUserName(userName)
                        .addEmail(userEmail)
                        .addUserStatus(userStatus)
                        .addBanStatus(isBanned)
                        .addRegistrationDate(regDate)
                        .create();
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String userCounterQuery = queryConfig.getSqlQuery(SqlQueryName.USER_COUNTER_QUERY);

            resultSet = statement.executeQuery(userCounterQuery);

            int userCounter = 0;
            if (resultSet.next()) {
                userCounter = resultSet.getInt(1);
            }
            return userCounter;
        } catch (SQLException e) {
            throw new CounterDAOException("Error while executing user counter query", e);
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String banUserQuery = queryConfig.getSqlQuery(SqlQueryName.BAN_USER_QUERY);

            preparedStatement = connection.prepareStatement(banUserQuery);

            BanInfo banInfo = user.getBanInfo();
            preparedStatement.setTimestamp(1, banInfo.getBanTime());
            preparedStatement.setInt(2, banInfo.getBanReason().getId());
            preparedStatement.setInt(3, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Unexpected result from update query");
            }
        } catch (SQLException e) {
            throw new BanUserException("SQL error while banning user id=" + user.getId(), e);
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
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
            throw new BanUserException("SQL error while taking ban reason list; language - " + lang, e);
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String unbanUserQuery = queryConfig.getSqlQuery(SqlQueryName.UNBAN_USER_QUERY);

            preparedStatement = connection.prepareStatement(unbanUserQuery);
            preparedStatement.setInt(1, userID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Unexpected result from update query");
            }
        } catch (SQLException e) {
            throw new BanUserException("Error while trying to unban user id=" + userID, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void changeUserStatus(int userId, String newStatus) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String changeUserStatusQuery = queryConfig.getSqlQuery(SqlQueryName.CHANGE_USER_STATUS_QUERY);

            preparedStatement = connection.prepareStatement(changeUserStatusQuery);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Unexpected result from update query");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to execute user change status query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private void setBanInfo(Connection connection, User user, String lang) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String takeBanInfo = queryConfig.getSqlQuery(SqlQueryName.TAKE_BAN_INFO);

            preparedStatement = connection.prepareStatement(takeBanInfo);

            int userID = user.getId();
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            BanInfo banInfo = new BanInfo();
            while (resultSet.next()) {
                Timestamp banTime = resultSet.getTimestamp(1);
                String banReason = resultSet.getString(2);

                banInfo.setBanTime(banTime);
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

            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String takeUserReviewsQuery = queryConfig.getSqlQuery(SqlQueryName.TAKE_USER_REVIEWS_QUERY);

            preparedStatement = connection.prepareStatement(takeUserReviewsQuery);

            int userID = user.getId();
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            List<Review> reviewList = new ArrayList<>();
            Review review;
            while (resultSet.next()) {
                int showId = resultSet.getInt(1);
                int userRate = resultSet.getInt(2);
                String reviewContent = resultSet.getString(3);
                Timestamp reviewPostDate = resultSet.getTimestamp(4);
                String reviewStatus = resultSet.getString(5);

                review = new UserReviewBuilder()
                        .addShowId(showId)
                        .addUser(user)
                        .addUserRate(userRate)
                        .addReviewContent(reviewContent)
                        .addPostDate(reviewPostDate)
                        .addReviewStatus(reviewStatus)
                        .create();

                reviewList.add(review);
            }
            user.setReviews(reviewList);
        } catch (SQLException e) {
            throw new UserInitializaionException("Cannot get list of user reviews", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private boolean checkPasswordMatch(Connection connection, String login, String password) throws PasswordDAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String checkPasswordQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_PASSWORD_QUERY);

            preparedStatement = connection.prepareStatement(checkPasswordQuery);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new PasswordDAOException("Password mismatch for user '" + login + "'");
            }
            return true;
        } catch (SQLException e) {
            throw new PasswordDAOException("Cannot check password existence", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private boolean checkEmailForRegistration(Connection connection, String eMail) throws EmailDaoException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String checkEmailQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_EMAIL_QUERY);

            preparedStatement = connection.prepareStatement(checkEmailQuery);

            preparedStatement.setString(1, eMail);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                throw new EmailDaoException("Email " + eMail + " is already registered");
            }
            return true;
        } catch (SQLException e) {
            String errorMessage = "Cannot check if \'" + eMail + "\' is registered";
            throw new EmailDaoException(errorMessage, e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }

    }

    private boolean checkUsernameForRegistration(Connection connection, String login) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Configuration queryConfig = QueryConfigurationFactory.getInstance().getUserQueryConfig();
            String checkUserExistQuery = queryConfig.getSqlQuery(SqlQueryName.CHECK_USER_EXIST_QUERY);

            preparedStatement = connection.prepareStatement(checkUserExistQuery);

            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new UserNotRegisteredException("User " + login + " is not registered");
            }
            return true;
        } catch (SQLException e) {
            String errorMessage = "Cannot check if \'" + login + "\' is registered";
            throw new DAOException(errorMessage, e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }

    }
}
