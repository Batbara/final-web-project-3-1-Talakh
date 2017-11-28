package by.tr.web.dao;

import by.tr.web.exception.dao.ApplicationDAOException;

public interface AppDAO {
    void initConnectionPool() throws ApplicationDAOException;
}
