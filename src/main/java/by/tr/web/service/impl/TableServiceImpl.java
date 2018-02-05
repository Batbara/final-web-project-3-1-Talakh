package by.tr.web.service.impl;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.cookie.CookieManager;
import by.tr.web.cookie.CookieNotFoundException;
import by.tr.web.cookie.NoSuchCookieInRequest;
import by.tr.web.domain.Movie;
import by.tr.web.domain.TvShow;
import by.tr.web.exception.service.common.EmptyParameterException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.show.InvalidOrderTypeException;
import by.tr.web.service.TableService;
import by.tr.web.service.factory.ValidatorFactory;
import by.tr.web.service.validation.DataTypeValidator;
import by.tr.web.service.validation.ShowValidator;

import javax.servlet.http.HttpServletRequest;

public class TableServiceImpl implements TableService {
    @Override
    public int takeCurrentPage(HttpServletRequest request) throws ServiceException {
        int defaultCurrentPageValue = 1;

        String currentPageString = request.getParameter(TableParameter.PAGE);

        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        try {
            validator.checkForPositive(currentPageString);
            return Integer.parseInt(currentPageString);
        } catch (EmptyParameterException e) {
            return determineValueFromCookie(request, defaultCurrentPageValue);
        }
    }

    @Override
    public int takeRecordsOnPage(HttpServletRequest request) throws ServiceException {
        int defaultRecordsOnPage = (Integer) request.getAttribute(FrontControllerParameter.DEFAULT_RECORDS_ON_PAGE);

        String recordsOnPageParameter = request.getParameter(TableParameter.RECORDS_ON_PAGE);

        DataTypeValidator validator = ValidatorFactory.getInstance().getDataTypeValidator();
        try {
            validator.checkForPositive(recordsOnPageParameter);
            return Integer.parseInt(recordsOnPageParameter);
        } catch (EmptyParameterException e) {
            return determineValueFromCookie(request, defaultRecordsOnPage);
        }

    }

    @Override
    public int calcRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords) {
        int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
        int recordsToTake = recordsOnPage;
        if (recordsOnPage * currentPage > numberOfRecords) {
            if (numOfPages == currentPage) {
                recordsToTake = numberOfRecords;
            } else {
                recordsToTake = recordsOnPage * currentPage - numberOfRecords;
            }
        }
        return recordsToTake;
    }

    @Override
    public String takeMovieOrderType(HttpServletRequest request) {
        String defaultOrderType = Movie.MovieOrderType.TITLE.toString().toLowerCase();
        String orderType = request.getParameter(TableParameter.ORDER);

        ShowValidator validator = ValidatorFactory.getInstance().getMovieValidator();
        try {
            validator.checkOrderType(orderType);
        } catch (InvalidOrderTypeException e) {
            try {
                orderType = takeValueFromCookie(request);
            } catch (NoSuchCookieInRequest | CookieNotFoundException noSuchCookieInRequest) {
                orderType = defaultOrderType;
            }
        }

        return orderType;
    }

    @Override
    public String takeTvShowOrderType(HttpServletRequest request) {
        String defaultOrderType = TvShow.TvShowOrderType.TITLE.toString().toLowerCase();
        String orderType = request.getParameter(TableParameter.ORDER);

        ShowValidator validator = ValidatorFactory.getInstance().getTvShowValidator();
        try {
            validator.checkOrderType(orderType);
        } catch (InvalidOrderTypeException e) {
            try {
                orderType = takeValueFromCookie(request);
            } catch (NoSuchCookieInRequest | CookieNotFoundException noSuchCookieInRequest) {
                orderType = defaultOrderType;
            }
        }

        return orderType;
    }

    private int determineValueFromCookie(HttpServletRequest request, int defaultValue) {
        int currentPage;
        try {
            String valueFromCookie = takeValueFromCookie(request);
            currentPage = Integer.parseInt(valueFromCookie);
        } catch (NoSuchCookieInRequest | CookieNotFoundException e) {
            currentPage = defaultValue;
        }
        return currentPage;
    }

    private String takeValueFromCookie(HttpServletRequest request) throws NoSuchCookieInRequest, CookieNotFoundException {
        CookieManager cookieManager = new CookieManager(request);
        String cookieName = (String) request.getAttribute(FrontControllerParameter.COOKIE_NAME);

        if (cookieManager.isCookieInRequest(cookieName)) {
            String storedValue = cookieManager.takeCookieValue(cookieName);
            return storedValue;
        } else {
            throw new NoSuchCookieInRequest("Cookie '" + cookieName + "' not found");
        }

    }
}
