package by.tr.web.dao.impl.movie;

import by.tr.web.dao.MovieDAO;
import by.tr.web.dao.impl.connection_pool.ConnectionPool;
import by.tr.web.domain.Movie;
import by.tr.web.exception.dao.movie.MovieCounterDAOException;
import by.tr.web.exception.dao.movie.MovieDAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(MovieDAOImpl.class);

    private final static String MOVIE_COUNTER_QUERY =
            "SELECT DISTINCT COUNT(*)" +
                    "  FROM mpb.movie;";

    private final static String MOVIE_LIST_QUERY =
            "SELECT sl.show_title AS title," +
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

    @Override
    public List<Movie> takeOrderedMovieList(int startPosition, int moviesNumber, String orderType, String lang) throws MovieDAOException {

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
                String title = resultSet.getString(1);
                String poster = resultSet.getString(2);
                int year = resultSet.getInt(3);
                double rating = resultSet.getDouble(4);

                movie = new Movie();
                movie.setTitle(title);
                movie.setPoster(poster);
                movie.setYear(year);
                movie.setUserRating(rating);

                movies.add(movie);
            }
            return movies;

        } catch (SQLException e) {
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
            logger.error("Error while executing movie counter query", e);
            throw new MovieCounterDAOException("Error while executing movie counter query", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }
}
