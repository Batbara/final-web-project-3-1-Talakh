package by.tr.web.dao.configuration;

import java.util.ResourceBundle;

public class UserSqlQueryConfig implements Configuration {
    private static final String BUNDLE_NAME = "query.user";
    @Override
    public String getSqlQuery(String name) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        return resourceBundle.getString(name);
    }
}
