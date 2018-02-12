package by.tr.web.service.table;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.cookie.CookieManager;
import by.tr.web.cookie.CookieNotFoundException;
import by.tr.web.domain.Table;
import by.tr.web.service.InvalidOrderTypeException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidatorFactory;
import by.tr.web.service.show.ShowValidator;

import javax.servlet.http.HttpServletRequest;

public class TableServiceImpl implements TableService {
    @Override
    public int takeCurrentPage(HttpServletRequest request, String tableName) throws ServiceException {
        Table configuration = TableConfigurationFactory.getInstance().configurationFor(tableName);
        int defaultCurrentPageValue = configuration.getCurrentPage();

        String currentPageString = request.getParameter(TableParameter.PAGE);

        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        try {
            validator.checkForPositive(currentPageString);
            return Integer.parseInt(currentPageString);
        } catch (RequestParameterNotFound e) {
            return determineValueFromCookie(request, defaultCurrentPageValue);
        }
    }

    @Override
    public int takeRecordsOnPage(HttpServletRequest request, String tableName) throws ServiceException {

        Table configuration = TableConfigurationFactory.getInstance().configurationFor(tableName);
        int defaultRecordsOnPage = configuration.getRecordsOnPage();

        String recordsOnPageParameter = request.getParameter(TableParameter.RECORDS_ON_PAGE);

        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        try {
            validator.checkForPositive(recordsOnPageParameter);
            return Integer.parseInt(recordsOnPageParameter);
        } catch (RequestParameterNotFound e) {
            return determineValueFromCookie(request, defaultRecordsOnPage);
        }

    }

    /**
     * Method determines number of records to be displayed to user.
     * <p>
     * Firstly, method calculates number of extra records in case {@code recordsOnPage}
     * is displayed on current page. If there are no extra records, method will return {@code recordsOnPage} value.
     * Otherwise, there are two possible scenarios:
     * <ul>
     * <li>
     * Current page is {@code 1}, which means, that there is only one page at all: {@code numberOfRecords} will be returned.
     * </li>
     * <li>
     * Current page is not {@code 1}. The difference between {@code recordsOnPage} and extra records will be returned.
     * </li>
     * </ul>
     */
    @Override
    public int calcRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords) {

        int recordsToTake;
        int extraRecords = recordsOnPage * currentPage - numberOfRecords;
        if (extraRecords > 0) {
            recordsToTake = (currentPage == 1) ? numberOfRecords : recordsOnPage - extraRecords;
        } else {
            recordsToTake = recordsOnPage;
        }
        return recordsToTake;
    }

    @Override
    public String takeMovieOrderType(HttpServletRequest request) throws TableConfigurationException {

        TableConfigurationFactory factory = TableConfigurationFactory.getInstance();
        Table configuration = factory.configurationFor(TableParameter.MOVIES_TABLE);

        String defaultOrderType = configuration.getOrderType();

        String orderType = request.getParameter(TableParameter.ORDER);

        ShowValidator validator = ValidatorFactory.getInstance().getMovieValidator();
        try {
            validator.checkOrderType(orderType);
        } catch (InvalidOrderTypeException e) {
            try {
                orderType = takeValueFromCookie(request);
            } catch (CookieNotFoundException ex) {
                orderType = defaultOrderType;
            }
        }

        return orderType;
    }

    @Override
    public String takeTvShowOrderType(HttpServletRequest request) throws TableConfigurationException {
        TableConfigurationFactory factory = TableConfigurationFactory.getInstance();
        Table configuration = factory.configurationFor(TableParameter.TV_SHOWS_TABLE);
        String defaultOrderType = configuration.getOrderType();

        String orderType = request.getParameter(TableParameter.ORDER);

        ShowValidator validator = ValidatorFactory.getInstance().getTvShowValidator();
        try {
            validator.checkOrderType(orderType);
        } catch (InvalidOrderTypeException e) {
            try {
                orderType = takeValueFromCookie(request);
            } catch (CookieNotFoundException ex) {
                orderType = defaultOrderType;
            }
        }

        return orderType;
    }

    /**
     * Determine either to take int value from cookie or default one
     * <p>
     * Method tries to retrieve value from cookie. If the cookie doesn't exist, the default value would be returned.
     *
     * @param request      HttpServletRequest object
     * @param defaultValue int default value
     * @return int value
     */
    private int determineValueFromCookie(HttpServletRequest request, int defaultValue) {
        int value;
        try {
            String valueFromCookie = takeValueFromCookie(request);
            value = Integer.parseInt(valueFromCookie);
        } catch (CookieNotFoundException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Take value of cookie with name specified in request.
     *
     * @param request HttpServletRequest object
     * @return String value of requested cookie
     * @throws CookieNotFoundException If cookie with specified name is not found in request
     */
    private String takeValueFromCookie(HttpServletRequest request) throws CookieNotFoundException {
        CookieManager cookieManager = new CookieManager(request);
        String cookieName = (String) request.getAttribute(FrontControllerParameter.COOKIE_NAME);

        String storedValue = cookieManager.takeCookieValue(cookieName);
        return storedValue;
    }

}
