package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.cookie.CookieManager;
import by.tr.web.cookie.CookieName;
import by.tr.web.domain.Show;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.show.ShowService;
import by.tr.web.service.table.TableService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeShowListImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeShowListImpl.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ShowService showService = ServiceFactory.getInstance().getShowService();
        TableService tableService = ServiceFactory.getInstance().getTableService();

        try {

            int numberOfRecords = showService.countAllShows();

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.SHOWS_CURRENT_PAGE);
            int currentPage = tableService.takeCurrentPage(request);
            request.setAttribute(TableParameter.PAGE, currentPage);

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.RECORDS_ON_SHOWS_PAGE);
            request.setAttribute(FrontControllerParameter.DEFAULT_RECORDS_ON_PAGE,25);
            int recordsOnPage = tableService.takeRecordsOnPage(request);
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);

            int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentPage, numberOfRecords);

            int startRecordNum = (currentPage - 1) * recordsOnPage;
            String lang = RequestUtil.getLanguage(request);
            List<Show> shows = showService.takeSortedShowList(startRecordNum, recordsToTake, lang);
            request.setAttribute(JspAttribute.SHOWS, shows);

            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            setCookies(request,response, currentPage, recordsOnPage);

            request.removeAttribute(FrontControllerParameter.COOKIE_NAME);
            request.getRequestDispatcher(JspPagePath.SHOW_LIST_PAGE_PATH).forward(request, response);

        } catch (CountingServiceException e) {
            logger.error("Error while counting movies in DB", e);
           // response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error("Error while getting movie list", e);
            //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.getRequestDispatcher(JspPagePath.INTERNAL_ERROR_PAGE).forward(request, response);
        }
    }
    private void setCookies(HttpServletRequest request, HttpServletResponse response,
                            int currentPage, int recordsOnPage) {

        CookieManager cookieManager = new CookieManager(request);

        String currentPageString = Integer.toString(currentPage);
        response.addCookie(cookieManager.makeCookie(CookieName.SHOWS_CURRENT_PAGE, currentPageString));

        String recordsOnPageString = Integer.toString(recordsOnPage);
        response.addCookie(cookieManager.makeCookie(CookieName.RECORDS_ON_SHOWS_PAGE, recordsOnPageString));
    }

}
