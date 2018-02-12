package by.tr.web.controller.constant;

public final class FrontControllerParameter {
    public static final String COMMAND = "command";
    public static final String QUERY = "query";
    public static final String LOCALE = "local";
    public static final String COOKIE_NAME = "cookieName";
    public static final String DEFAULT_RECORDS_ON_PAGE = "defaultRecordsOnPage";

    public static final String SUCCESS_RESPONSE = "success";
    public static final String FAILURE_RESPONSE = "failure";

    public static final String TEXT_HTML_CONTENT_TYPE = "text/html";
    public static final String TEXT_PLAIN_CONTENT_TYPE = "text/plain";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String JSON_CONTENT_TYPE = "application/json";

    public static final String LOCALIZATION_BUNDLE_NAME = "localization.local";
    public static final String DEFAULT_TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FILENAME_TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH-mm-ss";
    public static final String SIMPLE_DATE_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String SQL_DATE_PATTERN = "yyyy-MM-dd";
    public static final String ONLY_TIME_PATTERN = "HH:mm";

    public static final String USER_IS_BANNED = "userIsBanned";
    public static final String REGISTER_ERROR = "registerError";
    public static final String LOGIN_ERROR = "loginError";
    public static final String ADD_SHOW_ERROR = "addShowError";
    public static final String REDIRECT_COMMAND = "redirect";

    public static final String DESCENDING_ORDER = " desc";
    public static final String POSTER_UPLOAD_FOLDER = "posterUploadFolder";

    public enum Language {
        RU, EN;

        public static String getDefault() {
            return EN.toString().toLowerCase();
        }
    }

    private FrontControllerParameter() {
    }


}
