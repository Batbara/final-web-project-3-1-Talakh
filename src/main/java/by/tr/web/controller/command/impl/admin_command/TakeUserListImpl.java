package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JSPAttribute;
import by.tr.web.controller.constant.JSPPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.controller.constant.Util;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
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

public class TakeUserListImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeUserListImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int currentPage = getCurrentPage(request);
        int recordsOnPage = getRecordsOnPage(request);
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

            request.setAttribute(JSPAttribute.USER_LIST, userList);
            request.setAttribute(JSPAttribute.BAN_REASON_LIST, banReasonList);

            request.setAttribute(TableParameter.PAGE, currentPage);
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            request.getRequestDispatcher(JSPPagePath.ADMINISTRATION_PAGE_PATH).forward(request, response);
        } catch (CountingUserException e) {
            logger.error("Error while counting users", e);
            request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Cannot take user list", e);
            request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }
    }

    private List<BanReason> takeBanReasonList(String lang) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        List<BanReason> banReasonList = userService.takeBanReasonList(lang);
        return banReasonList;
    }

    private int getCurrentPage(HttpServletRequest request) {
        String currentPageString = request.getParameter(TableParameter.PAGE);
        int currentPage = 1;
        if (currentPageString != null) {
            currentPage = Integer.parseInt(currentPageString);
        }
        return currentPage;
    }

    private int getRecordsOnPage(HttpServletRequest request) {
        int recordsOnPage = 25;
        String recordsOnPageString = request.getParameter(TableParameter.RECORDS_ON_PAGE);
        if (recordsOnPageString != null) {
            recordsOnPage = Integer.parseInt(recordsOnPageString);
        }
        return recordsOnPage;
    }

    private int calculateNumberOfPages(int numberOfRecords, int recordsOnPage) {
        return (int) Math.ceil((double) numberOfRecords / recordsOnPage);
    }
}
