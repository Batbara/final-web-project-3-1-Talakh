package by.tr.web.dao.impl;

import java.util.Locale;
import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle =
            ResourceBundle.getBundle("db", Locale.getDefault());

    public static DBResourceManager getInstance() {
        return instance;
    }
    public String getValue(String key){
        return bundle.getString(key);
    }

}
