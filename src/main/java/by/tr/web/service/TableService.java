package by.tr.web.service;

import by.tr.web.exception.service.common.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface TableService {
    int takeCurrentPage(HttpServletRequest request) throws ServiceException;

    int takeRecordsOnPage(HttpServletRequest request) throws ServiceException;

    int calcRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords) throws ServiceException;

    String takeMovieOrderType(HttpServletRequest request) throws ServiceException;

    String takeTvShowOrderType(HttpServletRequest request) throws ServiceException;
}
