package by.tr.web.dao.configuration;

import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.exception.SqlQueryConfigurationError;

import java.lang.reflect.Field;

public class ConfigurationFactory {
    private static final ConfigurationFactory instance = new ConfigurationFactory();
    private Configuration userQueryConfig = new UserSqlQueryConfig();
    private Configuration movieQueryConfig = new MovieSqlQueryConfig();
    private Configuration showQueryConfig = new ShowSqlQueryConfig();
    private Configuration tvShowConfig = new TvShowSqlQueryConfig();

    public static ConfigurationFactory getInstance() {
        return instance;
    }

    public Configuration getConfiguration(String name) throws DAOException {

        try {
            Field field = ConfigurationFactory.class.getField(name);
            Configuration configValue = (Configuration) field.get(instance);
            return configValue;
        } catch (NoSuchFieldException e) {
            throw new SqlQueryConfigurationError("No such configuration as "+name, e);
        } catch (IllegalAccessException e) {
           throw new SqlQueryConfigurationError("Cannot access class field", e);
        }
    }

    public Configuration getTvShowConfig() {
        return tvShowConfig;
    }

    public Configuration getShowQueryConfig() {
        return showQueryConfig;
    }

    public Configuration getUserQueryConfig() {
        return userQueryConfig;
    }

    public Configuration getMovieQueryConfig() {
        return movieQueryConfig;
    }

    private ConfigurationFactory(){}
}
