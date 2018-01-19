package by.tr.web.dao.impl.movie;

import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.dao.impl.show.ShowQuery;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Movie;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.exception.dao.movie.MovieCounterDAOException;
import by.tr.web.exception.dao.movie.MovieDAOException;
import by.tr.web.exception.dao.movie.MovieInitializationException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(MovieDAOImpl.class);

    private final static String MOVIE_COUNTER_QUERY =
            "SELECT DISTINCT COUNT(*)" +
                    "  FROM mpb.movie;";

    private final static String MOVIE_LIST_QUERY =
            "SELECT s.show_id, sl.show_title AS title," +
                    " sl.show_poster," +
                    " s.show_year AS `year`," +
                    "  ( SELECT AVG(r.review_rate)" +
                    "    FROM  review AS r" +
                    "      WHERE (s.show_id = r.show_id)" +
                    "       GROUP BY (r.show_id)" +
                    "    ) AS rating" +
                    " FROM mpb.`show` AS s" +
                    "  INNER JOIN show_lang AS sl" +
                    "   ON (s.show_id = sl.show_id)" +
                    " WHERE sl.show_lang = ? " +
                    " AND show_type = 'movie'" +
                    "   ORDER BY %s " +
                    "  LIMIT ?, ?;";
    private final static String MOVIE_INFO_QUERY =
            "SELECT show_title AS title, " +
                    "   show_year AS `year`, show_premiere_date AS p_date, " +
                    "       show_runtime AS runtime," +
                    "       movie_box_office AS b_office," +
                    "       movie_budget AS budget," +
                    "   movie_mpaa_rating AS rating," +
                    "       show_synopsis, show_poster" +
                    "   FROM `show` AS s" +
                    "     INNER JOIN show_lang AS sl" +
                    "       ON (s.show_id = sl.show_id)" +
                    "   INNER JOIN movie AS m" +
                    "   ON (s.show_id = m.movie_show_id)" +
                    "  WHERE s.show_id = ? " +
                    "   AND sl.show_lang = ? ";

    @Override
    public List<Movie> takeOrderedMovieList(int startPosition, int moviesNumber, String orderType, String lang)
            throws MovieDAOException {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            String movieListQuery = String.format(MOVIE_LIST_QUERY, orderType);
            preparedStatement = connection.prepareStatement(movieListQuery);

            preparedStatement.setString(1, lang);
            preparedStatement.setInt(2, startPosition);
            preparedStatement.setInt(3, moviesNumber);

            resultSet = preparedStatement.executeQuery();

            List<Movie> movies = new ArrayList<>();
            Movie movie;
            while (resultSet.next()) {
                int showID = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String poster = resultSet.getString(3);
                int year = resultSet.getInt(4);
                double rating = resultSet.getDouble(5);

                movie = new Movie();
                movie.setShowID(showID);
                movie.setTitle(title);
                movie.setPoster(poster);
                movie.setYear(year);
                movie.setUserRating(rating);

                movies.add(movie);
            }
            return movies;

        } catch (SQLException e) {
            logger.error("Error while preparing SQL-statement", e);
            throw new MovieDAOException("Error while preparing SQL-statement", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public int countMovies() throws MovieCounterDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(MOVIE_COUNTER_QUERY);

            int movieCounter = 0;
            if (resultSet.next()) {
                movieCounter = resultSet.getInt(1);
            }
            return movieCounter;
        } catch (SQLException e) {
            throw new MovieCounterDAOException("Error while executing movie counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public Movie takeMovie(int id, String lang) throws MovieDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(MOVIE_INFO_QUERY);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new MovieDAOException("Movie with id " + id + " not found");
            }

            Movie movie = new Movie(id);

            setMovieInfo(movie, resultSet);
            setMovieGenres(movie, lang, connection);
            setMovieCountries(movie, lang, connection);
            setMovieReviews(movie, connection);

            return movie;
        } catch (SQLException e) {
            throw new MovieDAOException("Error while executing SQL query", e);
        }finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private void setMovieInfo(Movie movie, ResultSet resultSet) throws MovieDAOException {
        try {
            String title = resultSet.getString(1);
            Date yearDate = resultSet.getDate(2);
            Date premiereDate = resultSet.getDate(3);
            Time runtime = resultSet.getTime(4);
            Long boxOffice = resultSet.getLong(5);
            Long movieBudget = resultSet.getLong(6);
            String mpaaRating = resultSet.getString(7);
            String synopsis = resultSet.getString(8);
            String poster = resultSet.getString(9);

            movie.setTitle(title);
            movie.setYear(getYearFromDate(yearDate));
            movie.setPremiereDate(premiereDate);
            movie.setRuntime(runtime);
            movie.setBoxOffice(boxOffice);
            movie.setBudget(movieBudget);
            movie.setMpaaRating(mpaaRating);
            movie.setSynopsis(synopsis);
            movie.setPoster(poster);

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing movie information", e);
        }

    }

    private void setMovieGenres(Movie movie, String lang, Connection connection) throws MovieDAOException{
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(ShowQuery.GENRE_OF_SHOW_QUERY);

            preparedStatement.setInt(1, movie.getShowID());
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            Genre genre;
            while (resultSet.next()){
                String genreName = resultSet.getString(1);

                genre = new Genre();
                genre.setGenreName(genreName);
                movie.addGenre(genre);
            }

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing genre list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }

    private void setMovieCountries(Movie movie, String lang, Connection connection) throws MovieDAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(ShowQuery.COUNTRY_OF_SHOW_QUERY);

            preparedStatement.setInt(1, movie.getShowID());
            preparedStatement.setString(2, lang);

            resultSet = preparedStatement.executeQuery();
            Country country;
            while (resultSet.next()){
                String countryName = resultSet.getString(1);

                country = new Country();
                country.setCountryName(countryName);
                movie.addCountry(country);
            }

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing country list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }
    private void setMovieReviews(Movie movie, Connection connection) throws MovieDAOException{
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(ShowQuery.REVIEWS_OF_SHOW_QUERY);

            preparedStatement.setInt(1, movie.getShowID());
            resultSet = preparedStatement.executeQuery();
            UserReview review;
            User user;
            while (resultSet.next()){
                int userID = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                int userRate = resultSet.getInt(3);
                String reviewContent = resultSet.getString(4);
                Timestamp postDate = resultSet.getTimestamp(5);

                review = new UserReview();
                user = new User();
                user.setId(userID);
                user.setUserName(userName);
                review.setUser(user);

                review.setUserRate(userRate);
                review.setReviewContent(reviewContent);
                review.setPostDate(postDate);
                review.setShow(movie);

                movie.addReview(review);
            }

        } catch (SQLException e) {
            throw new MovieInitializationException("Error while initializing country list", e);
        } finally {
            connectionPool.closeResources(preparedStatement, resultSet);
        }
    }
    private int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        return year;
    }

}
