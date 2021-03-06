package by.tr.web.dao.impl.movie;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.exception.CounterDAOException;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.movie.MovieDAOSqlImpl;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Movie;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.UserBuilder;
import by.tr.web.domain.builder.UserReviewBuilder;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.show.ShowAlreadyExistsException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOSqlImplTest {
    private MovieDAOSqlImpl movieDAO;
    private final static String DEFAULT_TIME_PATTERN = "yyyy-MM-dd'T'hh:mm:ss";
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void initConnectionPool() {
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @AfterClass
    public static void destroyConnectionPool() {
        connectionPool.dispose();
    }

    @Before
    public void setUp() {
        movieDAO = new MovieDAOSqlImpl();
    }

    @Test
    public void testMoviesOrderedByYear() throws DAOException {
        List<Movie> expectedMovieList = formExpectedMovieListByYear();

        int startPosition = 0;
        int numberOfMovies = expectedMovieList.size();
        String lang = "en";
        String orderType = Movie.MovieOrderType.YEAR.toString();

        List<Movie> actualMovieList = movieDAO.takeSortedMovieList(startPosition, numberOfMovies, orderType, lang);

        boolean expectedEquals = true;
        boolean actual = actualMovieList.equals(expectedMovieList);
        Assert.assertEquals(expectedEquals, actual);
    }

    @Test
    public void testMoviesOrderedByTitle() throws DAOException {
        List<Movie> expectedMovieList = formExpectedMovieListByTitle();

        int startPosition = 5;
        int numberOfMovies = expectedMovieList.size();
        String lang = "en";
        String orderType = Movie.MovieOrderType.TITLE.toString();

        List<Movie> actualMovieList = movieDAO.takeSortedMovieList(startPosition, numberOfMovies, orderType, lang);

        boolean expectedEquals = true;
        boolean actual = actualMovieList.equals(expectedMovieList);
        Assert.assertEquals(expectedEquals, actual);
    }

    @Test
    public void testCountMovies() throws CounterDAOException {
        int expected = 22;
        int actual = movieDAO.countMovie();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = DAOException.class)
    public void testTakeMovieFail() throws DAOException {
        int movieId = 100500;
        String lang = "en";
        movieDAO.takeMovie(movieId, lang);
    }

    @Test
    public void testTakeMovieSuccessful() throws DAOException, ParseException, RequestParameterNotFound {
        int id = 23;
        String lang = "ru";

        Movie expectedMovie = formExpectedMovie(id);
        Movie actualMovie = movieDAO.takeMovie(id, lang);

        boolean expectedEquals = true;
        boolean actual = actualMovie.equals(expectedMovie);
        Assert.assertEquals(expectedEquals, actual);
    }

    @Test(expected = ShowAlreadyExistsException.class)
    public void testAddMovieFailed() throws ParseException, RequestParameterNotFound, DAOException {
        Movie movieToAdd = new MovieBuilder()
                .addTitle("Logan")
                .addPremiereDate(TypeFormatUtil.getDateFromString("2017-02-17", FrontControllerParameter.SQL_DATE_PATTERN))
                .create();
        Movie movieToAddRussianTranslation = new MovieBuilder()
                .addTitle("Логан")
                .create();
        movieDAO.addMovie(movieToAdd, movieToAddRussianTranslation);
    }

    private List<Movie> formExpectedMovieListByTitle() {
        List<Movie> movieList = new ArrayList<>();

        Movie kingdomOfHeaven = new MovieBuilder()
                .addId(7)
                .addTitle("Kingdom of Heaven")
                .addPoster("/images/poster/71.jpg")
                .addYear(2005)
                .addUserRating(7d)
                .create();
        movieList.add(kingdomOfHeaven);

        Movie lifeOfPi = new MovieBuilder()
                .addId(20)
                .addTitle("Life of Pi")
                .addPoster("/images/poster/201.jpg")
                .addYear(2012)
                .addUserRating(7d)
                .create();
        movieList.add(lifeOfPi);

        return movieList;
    }

    private List<Movie> formExpectedMovieListByYear() {
        List<Movie> movieList = new ArrayList<>();

        Movie timeMachineMovie = new MovieBuilder()
                .addId(16)
                .addTitle("The Time Machine")
                .addPoster("/images/poster/161.jpg")
                .addYear(1960)
                .addUserRating(7.0)
                .create();
        movieList.add(timeMachineMovie);

        Movie myFairLadyMovie = new MovieBuilder()
                .addId(12)
                .addTitle("My Fair Lady")
                .addPoster("/images/poster/121.jpg")
                .addYear(1964)
                .addUserRating(8.5d)
                .create();
        movieList.add(myFairLadyMovie);

        Movie godFatherMovie = new MovieBuilder()
                .addId(5)
                .addTitle("The Godfather")
                .addPoster("/images/poster/51.jpg")
                .addYear(1972)
                .addUserRating(9d)
                .create();
        movieList.add(godFatherMovie);

        return movieList;
    }

    private Movie formExpectedMovie(int id) throws ParseException, RequestParameterNotFound {

        String synopsis = "Четырехпалый Френки должен был переправить краденый алмаз из Англии в США " +
                "своему боссу Эви. Но вместо этого герой попадает в эпицентр больших неприятностей." +
                " Сделав ставку на подпольном боксерском поединке, Френки попадает в круговорот весьма" +
                " нежелательных событий. Вокруг героя и его груза разворачивается сложная интрига с " +
                "участием множества колоритных персонажей лондонского дна — русского гангстера, троих" +
                " незадачливых грабителей, хитрого боксера и угрюмого громилы грозного мафиози. " +
                "Каждый норовит в одиночку сорвать Большой Куш.";
        long runTimeInMillis = TypeFormatUtil.getTimestampFromString("01:42:00", "hh:mm:ss").getTime();
        Time runTime = new Time(runTimeInMillis);

        long premiereDateInMillis = TypeFormatUtil.getTimestampFromString("2000-08-23", "yyyy-MM-dd").getTime();
        java.sql.Date premiereDate = new Date(premiereDateInMillis);

        List<Genre> movieGenres = new ArrayList<>();
        movieGenres.add(new Genre("комедия"));
        movieGenres.add(new Genre("криминал"));

        List<Country> movieCountries = new ArrayList<>();
        movieCountries.add(new Country("США"));
        movieCountries.add(new Country("Великобритания"));

        List<Review> reviews = formReviews(id);

        Movie movie = new MovieBuilder()
                .addId(id)
                .addTitle("Большой куш")
                .addYear(2000)
                .addPremiereDate(premiereDate)
                .addSynopsis(synopsis)
                .addPoster("/images/poster/232.jpg")
                .addBoxOffice(83557872)
                .addBudget(10000000)
                .addMpaaRating("R")
                .addRuntime(runTime)
                .addGenres(movieGenres)
                .addCountries(movieCountries)
                .addUserRating(0d)
                .create();
        return movie;
    }

    private List<Review> formReviews(int movieId) throws ParseException, RequestParameterNotFound {

        User user = new UserBuilder()
                .addId(31)
                .addUserName("RainbowSunshine")
                .create();

        String reviewContent = "Спасибо, мистер Ричи!\\nСуществуют фильмы, которые я готов пересматривать" +
                " огромное количество раз, среди них «Большой куш». Ознакомился я с данной картиной около" +
                " шести месяцев назад, но если бы знал о всей её крутости, то незамедлительно посмотрел бы " +
                "намного раньше. Гай Ричи один из лучших режиссёров современности. Его фильмы выделяются " +
                "на фоне остальных благодаря оригинальному сюжету, добротному юмору, прекрасному саундтреку " +
                "и колоритным персонажам. «Большой куш» вообще побил все возможные рекорды по количеству " +
                "колоритных персонажей на одну минуту фильма. Здесь очень много запоминающихся личностей," +
                " именно поэтому так люблю «Snatch». \\nСюжет пересказывать не буду, скажу только что он " +
                "безумно оригинальный и запоминающийся, все кто посмотрел фильм согласятся со мной. В нём " +
                "нет ничего лишнего, каждый диалог, каждая сцена, каждая секунда продуманы до мелочей. " +
                "Нет ощущения что нужно убрать какой-то момент, или наоборот добавить, один из редких " +
                "случаев когда всё идеально. Лучше один раз увидеть, чем сто раз услышать!\\nНа следующий " +
                "день после просмотра я рассказывал друзьям про каждого персонажа, про их крутость и " +
                "убойные высказывания. Это один из лучших актёрских составов в истории и с этим нельзя " +
                "не согласиться. Не могу выделить кого-то одного, все безумно порадовали. " +
                "\\nПеред нами идеальный фильм в котором я не нашёл минусов, всё выполнено качественно " +
                "и со знанием дела. Всячески рекомендую к просмотру, особенно парням, уверен что вы не пожалеете. " +
                "Лично для меня «Большой куш» стал настоящим открытием и я могу смело сказать" +
                " что это мой самый любимый фильм. ";

        Timestamp postDate = TypeFormatUtil.getTimestampFromString("2018-01-21T19:10:49", DEFAULT_TIME_PATTERN);

        List<Review> reviews = new ArrayList<>();
        Review review = new UserReviewBuilder()
                .addShowId(movieId)
                .addUser(user)
                .addReviewContent(reviewContent)
                .addPostDate(postDate)
                .addUserRate(9)
                .create();
        reviews.add(review);

        return reviews;
    }

}