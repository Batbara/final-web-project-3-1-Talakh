package by.tr.web.service.impl;

import by.tr.web.dao.AppDAO;
import by.tr.web.dao.factory.DAOFactory;
import by.tr.web.exception.dao.ApplicationDAOException;
import by.tr.web.exception.service.ApplicationServiceException;
import by.tr.web.service.AppService;

public class AppServiceImpl implements AppService{
    @Override
    public void initResources() throws ApplicationServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        AppDAO appDAO = daoFactory.getAppDAO();
        try {
            appDAO.initConnectionPool();
        } catch (ApplicationDAOException e) {
            throw new ApplicationServiceException("Failed to init connection pool",e);
        }
    }
}
