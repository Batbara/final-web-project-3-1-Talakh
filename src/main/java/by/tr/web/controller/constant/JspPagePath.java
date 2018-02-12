package by.tr.web.controller.constant;

import by.tr.web.domain.Show;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class JspPagePath {

    public static final String INDEX = "/index.jsp";
    public static final String USER_ACCOUNT_PATH = "/account";
    public static final String INTERNAL_ERROR_PAGE = "/WEB-INF/jsp/error/internal-error.jsp";
    public static final String MOVIE_LIST_PAGE = "/movies";
    public static final String MOVIE_PAGE = "/m";
    public static final String TVSHOW_PAGE = "jsp/tvshow.jsp";
    public static final String TVSHOWS_LIST_PAGE = "jsp/tvshows.jsp";
    public static final String FRONT_CONTROLLER = "/mpb";

    public static final String ADD_MOVIE_PATH = "/WEB-INF/jsp/admin/addMoviePage.jsp";
    public static final String ADD_TV_SERIES_PATH = "/WEB-INF/jsp/admin/addTvShowPage.jsp";

    public static final String SHOW_LIST_PAGE_PATH = "/WEB-INF/jsp/admin/content.jsp";
    public static final String ADMINISTRATION_PAGE_PATH = "/WEB-INF/jsp/user/users.jsp";
    public static final String REVIEWS_MODERATION_PAGE_PATH = "/WEB-INF/jsp/admin/moderation.jsp";

    private static Map<Show.ShowType, String> showAddAddressMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(JspPagePath.class);

    public static String getShowAddingAddress(Show.ShowType showType) {
        return showAddAddressMap.get(showType);
    }

    private JspPagePath() {
    }
    static {

        try {
            for (Show.ShowType showType : Show.ShowType.values()) {
                Field field = JspPagePath.class.getField("ADD_" + showType.name() + "_PATH");
                String fieldValue = (String) field.get(null);
                showAddAddressMap.put(showType, fieldValue);
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

