package by.tr.web.dao.configuration;

import java.util.ResourceBundle;

public class QueryPropertiesAccessor {

    public static String getSQLQuery(String queryName) {
        ResourceBundle bundle = ResourceBundle.getBundle(DBParameter.QUERY_PROPERTIES);
        return bundle.getString(queryName);
    }

}
