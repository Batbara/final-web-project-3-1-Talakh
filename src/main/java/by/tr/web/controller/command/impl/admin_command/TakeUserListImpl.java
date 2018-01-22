package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.TakeListCommand;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.controller.cookie.CookieManager;
import by.tr.web.controller.cookie.CookieName;
import by.tr.web.controller.util.Util;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.controller.CookieNotFoundException;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.CountingUserException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeUserListImpl implements TakeListCommand {
    private static final Logger logger = Logger.getLogger(TakeUserListImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CookieManager cookieManager = new CookieManager(request);
        int defaultRecordsOnPage = 25;

        int currentPage;
        int recordsOnPage;

        try {
            currentPage = getCurrentPage(cookieManager, request, CookieName.USERS_CURRENT_PAGE);
            recordsOnPage = getRecordsOnPage(cookieManager, request, defaultRecordsOnPage, CookieName.RECORDS_ON_USERS_PAGE);
        } catch (CookieNotFoundException e) {
            logger.error("Cannot find cookie in request", e);
            currentPage = 1;
            recordsOnPage = defaultRecordsOnPage;
        }
        int startRecordNum = (currentPage - 1) * recordsOnPage;
        String lang = Util.getLanguage(request);

        UserService userService = ServiceFactory.getInstance().getUserService();
        List<User> userList;
        int numberOfRecords;
        try {
            numberOfRecords = userService.countUsers();
            int numOfPages = calculateNumberOfPages(numberOfRecords, recordsOnPage);
            int recordsToTake = Util.calcTableRecordsToTake(recordsOnPage, currentPage, numberOfRecords);

            userList = userService.takeUserList(startRecordNum, recordsToTake, lang);
            List<BanReason> banReasonList = takeBanReasonList(lang);

            request.setAttribute(JspAttribute.USER_LIST, userList);
            request.setAttribute(JspAttribute.BAN_REASON_LIST, banReasonList);

            request.setAttribute(TableParameter.PAGE, currentPage);
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            setCookies(response, cookieManager, currentPage, recordsOnPage);
            request.getRequestDispatcher(JspPagePath.ADMINISTRATION_PAGE_PATH).forward(request, response);
        } catch (CountingUserException e) {
            logger.error("Error while counting users", e);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Cannot take user list", e);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }
    }

    private List<BanReason> takeBanReasonList(String lang) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        List<BanReason> banReasonList = userService.takeBanReasonList(lang);
        return banReasonList;
    }

    private void setCookies(HttpServletResponse response, CookieManager cookieManager, int currentPage, int recordsOnPage) {

        String currentPageString = Integer.toString(currentPage);
        response.addCookie(cookieManager.makeCookie(CookieName.USERS_CURRENT_PAGE, currentPageString));

        String recordsOnPageString = Integer.toString(recordsOnPage);
        response.addCookie(cookieManager.makeCookie(CookieName.RECORDS_ON_USERS_PAGE, recordsOnPageString));
    }


    private int calculateNumberOfPages(int numberOfRecords, int recordsOnPage) {
        return (int) Math.ceil((double) numberOfRecords / recordsOnPage);
    }
}
