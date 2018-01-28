package by.tr.web.controller.constant;

import by.tr.web.domain.TvShow;
import by.tr.web.domain.User;
import by.tr.web.exception.controller.UserStatusBundleException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class LocalizationPropertyKey {
    private static Map<User.UserStatus, String> userStatusMap = new HashMap<>();
    private static Map<TvShow.ShowStatus, String> tvShowStatusMap = new HashMap<>();

    private final static Logger logger = Logger.getLogger(LocalizationPropertyKey.class);

    public static final String USER_BAN_INFO = "local.info.user.ban";
    public static final String USER_UNBAN_INFO = "local.info.user.unban";
    public static final String USER_BAN_REASON = "local.info.user.banReason";

    public static final String STATUS_ADMIN = "local.info.user.status.admin";
    public static final String STATUS_CASUAL_VIEWER = "local.info.user.status.casualViewer";
    public static final String STATUS_MOVIE_FAN = "local.info.user.status.movieFan";
    public static final String STATUS_REVIEWER = "local.info.user.status.reviewer";
    public static final String STATUS_CRITIC = "local.info.user.status.critic";

    public static final String STATUS_FINISHED = "local.info.tvshow.status.finished";
    public static final String STATUS_RETURNING = "local.info.tvshow.status.returning";
    public static final String STATUS_PAUSE = "local.info.tvshow.status.paused";

    public static final String SHOW_RATING = "local.show.sidenav.rating";
    public static final String USER_CUSTOM_RATE = "local.show.user.custom.rate";

    public static final String SHOW_TOTAL_REVIEWS = "local.show.reviews.total";
    public static final String NONE_REVIEWS = "local.show.reviews.none";
    public static final String SHOWING_MESSAGE = "local.message.showing";
    public static String getUserStatusProperty(User.UserStatus userStatus) {
        return userStatusMap.get(userStatus);
    }

    public static String getTvShowStatusProperty(TvShow.ShowStatus showStatus){
        return tvShowStatusMap.get(showStatus);
    }


    private LocalizationPropertyKey() {}
    static {

        try {
            for (User.UserStatus userStatus : User.UserStatus.values()) {
                Field field = LocalizationPropertyKey.class.getField("STATUS_" + userStatus.name());
                String fieldValue = (String) field.get(null);
                userStatusMap.put(userStatus, fieldValue);
            }
            for(TvShow.ShowStatus tvShowStatus : TvShow.ShowStatus.values()){
                Field field = LocalizationPropertyKey.class.getField("STATUS_" + tvShowStatus.name());
                String fieldValue = (String) field.get(null);
                tvShowStatusMap.put(tvShowStatus, fieldValue);
            }
        } catch (NoSuchFieldException e) {
            String message = "Cannot resolve class field";
            logger.error(message, e);
            throw new UserStatusBundleException(message, e);
        } catch (IllegalAccessException e) {
            String message = "Cannot access class field";
            logger.error(message, e);
            throw new UserStatusBundleException(message, e);
        }
    }
}
