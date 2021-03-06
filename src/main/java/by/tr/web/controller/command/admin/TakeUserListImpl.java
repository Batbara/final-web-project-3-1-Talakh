package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.cookie.CookieManager;
import by.tr.web.cookie.CookieName;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.table.TableParameter;
import by.tr.web.service.table.TableService;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.exception.CountingUserException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TakeUserListImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeUserListImpl.class);

    /**
     * Command to take {@link List} of {@link User} instances.
     *
     * <p>
     * Method retrieves specific number of {@link User} objects from data base, starting from specific position.
     * All needed parameters could be specified in request. If not, data would be taken from cookies or
     * default values would be set. Default values are provided by {@link by.tr.web.service.table.TableConfigurationFactory}.
     * In case of success, {@code List<Show>} is put as attribute in request.
     * </p>
     * <p>
     * After that request is forwarded to
     * {@link JspPagePath#REVIEWS_MODERATION_PAGE_PATH} page.
     * If an error occurs, {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR} will be sent.
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String lang = RequestUtil.getLanguage(request);
        UserService userService = ServiceFactory.getInstance().getUserService();
        TableService tableService = ServiceFactory.getInstance().getTableService();

        try {

            int numberOfRecords = userService.countUsers();

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.USERS_CURRENT_PAGE);
            int currentPage = tableService.takeCurrentPage(request, TableParameter.USERS_TABLE);
            request.setAttribute(TableParameter.PAGE, currentPage);

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.RECORDS_ON_USERS_PAGE);
            int recordsOnPage = tableService.takeRecordsOnPage(request, TableParameter.USERS_TABLE);
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);

            int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentPage, numberOfRecords);
            int startRecordNum = (currentPage - 1) * recordsOnPage;

            List<User> userList = userService.takeUserList(startRecordNum, recordsToTake, lang);
            request.setAttribute(JspAttribute.USER_LIST, userList);

            List<BanReason> banReasonList = takeBanReasonList(lang);
            request.setAttribute(JspAttribute.BAN_REASON_LIST, banReasonList);

            List<User.UserStatus> userStatusList = Arrays.asList(User.UserStatus.values());
            request.setAttribute(JspAttribute.USER_STATUS_LIST, userStatusList);

            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            setCookies(request, response, currentPage, recordsOnPage);

            request.removeAttribute(FrontControllerParameter.COOKIE_NAME);
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

    /**
     * Saves user's table parameters to cookies
     *
     * @param request
     * Request object
     * @param response
     * Response object
     * @param currentPage
     * Number of page user was currently on
     * @param recordsOnPage
     * Total table records displayed on the page
     */
    private void setCookies(HttpServletRequest request, HttpServletResponse response, int currentPage, int recordsOnPage) {
        CookieManager cookieManager = new CookieManager(request);
        String currentPageString = Integer.toString(currentPage);
        response.addCookie(cookieManager.makeCookie(CookieName.USERS_CURRENT_PAGE, currentPageString));

        String recordsOnPageString = Integer.toString(recordsOnPage);
        response.addCookie(cookieManager.makeCookie(CookieName.RECORDS_ON_USERS_PAGE, recordsOnPageString));
    }


}
