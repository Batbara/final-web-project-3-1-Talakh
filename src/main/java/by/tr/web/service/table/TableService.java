package by.tr.web.service.table;

import by.tr.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface TableService {
    /**
     * Take current page of table user is browsing at the moment
     *
     * @param request   HttpServletRequest object
     * @param tableName Name of the table
     * @return int number of page
     * @throws ServiceException If any error occurs
     */
    int takeCurrentPage(HttpServletRequest request, String tableName) throws ServiceException;

    /**
     * Take number of records per page from request which are set for table user is browsing at the moment
     *
     * @param request   HttpServletRequest object
     * @param tableName Name of the table
     * @return int number of records per page
     * @throws ServiceException If any error occurs
     */
    int takeRecordsOnPage(HttpServletRequest request, String tableName) throws ServiceException;

    /**
     * Calculate number of records to take depending on table parameters.
     *
     * @param recordsOnPage Number of records per page
     * @param currentPage Number of current page
     * @param numberOfRecords Total number of records in table
     * @return int number of records to be displayed
     * @throws ServiceException If any error occurs
     */
    int calcRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords) throws ServiceException;

    /**
     * Take current order type for table of {@link by.tr.web.domain.Movie} entities
     *
     * @param request HttpServletRequest object
     * @return String order type for movie's table records
     * @throws ServiceException If any error occurs
     */
    String takeMovieOrderType(HttpServletRequest request) throws ServiceException;

    /**
     * Take current order type for table of {@link by.tr.web.domain.TvShow} entities
     *
     * @param request HttpServletRequest object
     * @return String order type for tv-show's table records
     * @throws ServiceException If any error occurs
     */
    String takeTvShowOrderType(HttpServletRequest request) throws ServiceException;
}
