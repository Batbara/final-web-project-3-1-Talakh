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
    public static final String CHANGE_USER_STATUS_QUERY = "user.change.user.status";

    public static final String ADD_NEW_MOVIE = "movie.add.new.movie";
    public static final String MOVIE_COUNTER_QUERY = "movie.count.movies";
    public static final String TAKE_MOVIE_LIST_QUERY = "movie.take.movielist";
    public static final String TAKE_MOVIE_INFO_QUERY = "movie.take.movie.info";

    public static final String TVSHOW_COUNTER_QUERY = "tv.count.tvshows";
    public static final String TAKE_TVSHOW_LIST_QUERY = "tv.take.tvshow.list";
    public static final String TAKE_TV_SHOW = "tv.take.tvshow";

    public static final String ADD_NEW_SHOW = "show.add.new.show";
    public static final String ADD_SHOW_TRANSLATION = "show.add.new.translation";
    public static final String ADD_SHOW_GENRES = "show.add.show.genres";
    public static final String ADD_SHOW_COUNTRIES = "show.add.show.countries";
    public static final String TAKE_GENRE_OF_SHOW_QUERY = "show.take.genre";
    public static final String TAKE_COUNTRY_OF_SHOW_QUERY = "show.take.country";
    public static final String TAKE_COUNTRY_LIST_QUERY = "show.take.country.list";
    public static final String TAKE_GENRE_LIST_QUERY = "show.take.genre.list";
    public static final String TAKE_REVIEWS_OF_SHOW_QUERY = "show.take.reviews";
    public static final String TAKE_SHOW_RATING = "show.take.rating";

    public static final String ADD_USER_RATE = "show.add.user.rate";
    public static final String ADD_USER_REVIEW = "show.add.user.review";
    public static final String UPDATE_USER_RATE = "show.update.user.rate";
    public static final String UPDATE_USER_REVIEW = "show.update.user.review";
    public static final String CHECK_USER_REVIEW_PRESENCE = "show.check.user.review.presence";
    public static final String TAKE_POSTED_REVIEW_LIST = "show.take.posted.review.list";
    public static final String TAKE_MODERATED_REVIEW_LIST_QUERY = "show.take.moderated.review.list";
    public static final String COUNT_POSTED_REVIEWS_QUERY = "show.count.posted.reviews";
    public static final String COUNT_MODERATED_REVIEWS_QUERY = "show.count.moderating.reviews";
    public static final String POST_USER_REVIEW = "show.post.user.review";
    public static final String DELETE_USER_REVIEW = "show.delete.user.review";
    private SqlQueryName() {
    }
}
