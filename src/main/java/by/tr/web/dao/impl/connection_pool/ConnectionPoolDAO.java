package by.tr.web.dao.impl.connection_pool;

import by.tr.web.exception.dao.ConnectionPoolException;

public interface ConnectionPoolDAO {
    void initConnectionPool() throws ConnectionPoolException;
}
