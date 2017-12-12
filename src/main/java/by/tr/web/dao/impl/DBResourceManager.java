package by.tr.web.dao.impl;

import java.util.Locale;
import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle =
            ResourceBundle.getBundle(DBParameter.BASE_NAME, Locale.getDefault());

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getDBParameter(String paramName) {
        return bundle.getString(paramName);
    }

}
