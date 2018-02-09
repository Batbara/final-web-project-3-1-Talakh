package by.tr.web.dao.configuration;

import java.util.ResourceBundle;

public class MovieSqlQueryConfig implements Configuration {
    private final static String BUNDLE_NAME = "query.movie";
    @Override
    public String getSqlQuery(String name) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        return resourceBundle.getString(name);
    }
}
