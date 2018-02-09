package by.tr.web.dao.configuration;

import java.util.ResourceBundle;

public class ShowSqlQueryConfig implements Configuration {
    private final static String BUNDLE_NAME = "query.show";
    @Override
    public String getSqlQuery(String name) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        return resourceBundle.getString(name);
    }
}
