package by.tr.web.service.table;

import by.tr.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface TableService {
    int takeCurrentPage(HttpServletRequest request, String tableName) throws ServiceException;

    int takeRecordsOnPage(HttpServletRequest request, String tableName) throws ServiceException;

    int calcRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords) throws ServiceException;

    String takeMovieOrderType(HttpServletRequest request, String tableName) throws ServiceException;

    String takeTvShowOrderType(HttpServletRequest request, String tableName) throws ServiceException;
}
