package by.tr.web.dao;

import by.tr.web.exception.dao.ApplicationDAOException;

public interface AppDAO {// что это за странное и некорректное название интерфейса и исключения - как по названию интерфейса догадаться для чего он предназначен?
    void initConnectionPool() throws ApplicationDAOException;
}
