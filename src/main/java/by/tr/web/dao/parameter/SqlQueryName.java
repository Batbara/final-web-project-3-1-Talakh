package by.tr.web.dao.parameter;

public final class SqlQueryName {
    public static final String USER_COUNTER_QUERY = "user.count.users";
    public static final String REGISTER_QUERY = "user.register";
    public static final String TAKE_USER_QUERY = "user.take.user";
    public static final String TAKE_USER_LIST = "user.take.userlist";
    public static final String TAKE_USER_REVIEWS_QUERY = "user.take.user.reviews";
    public static final String TAKE_BAN_INFO = "user.take.ban.info";
    public static final String TAKE_BAN_REASONS_QUERY = "user.take.ban.reasons";
    public static final String CHECK_USER_EXIST_QUERY = "user.check.exist.user";
    public static final String CHECK_PASSWORD_QUERY = "user.check.password";
    public static final String CHECK_EMAIL_QUERY = "user.check.email";
    public static final String BAN_USER_QUERY = "user.ban.user";
    public static final String UNBAN_USER_QUERY = "user.unban.user";

    public static final String MOVIE_COUNTER_QUERY = "movie.count.movies";
    public static final String TAKE_MOVIE_LIST_QUERY = "movie.take.movielist";
    public static final String TAKE_MOVIE_INFO_QUERY = "movie.take.movie.info";

    public static final String TAKE_GENRE_OF_SHOW_QUERY = "show.take.genre";
    public static final String TAKE_COUNTRY_OF_SHOW_QUERY = "show.take.country";
    public static final String TAKE_REVIEWS_OF_SHOW_QUERY = "show.take.reviews";

    private SqlQueryName() {
    }
}
