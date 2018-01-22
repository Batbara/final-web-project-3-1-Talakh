package by.tr.web.controller.command;

import by.tr.web.controller.constant.TableParameter;
import by.tr.web.controller.cookie.CookieManager;
import by.tr.web.exception.controller.CookieNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface TakeListCommand extends Command {
    default int getCurrentPage(CookieManager cookieManager, HttpServletRequest request, String cookieName)
            throws CookieNotFoundException {
        int currentPage = 1;
        String currentPageString = request.getParameter(TableParameter.PAGE);

        if (currentPageString != null) {
            return Integer.parseInt(currentPageString);
        }

        if (cookieManager.isCookieInRequest(cookieName)) {
            String currentPageStored = cookieManager.takeCookieValue(cookieName);
            return Integer.parseInt(currentPageStored);
        }

        return currentPage;
    }

    default int getRecordsOnPage(CookieManager cookieManager, HttpServletRequest request, int defaultValue, String cookieName)
            throws CookieNotFoundException {
        String currentPageString = request.getParameter(TableParameter.RECORDS_ON_PAGE);

        if (currentPageString != null) {
            return Integer.parseInt(currentPageString);
        }

        if (cookieManager.isCookieInRequest(cookieName)) {
            String currentPageStored = cookieManager.takeCookieValue(cookieName);
            return Integer.parseInt(currentPageStored);
        }

        return defaultValue;
    }

}
