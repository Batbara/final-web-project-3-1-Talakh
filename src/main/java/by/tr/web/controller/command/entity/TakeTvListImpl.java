package by.tr.web.controller.command.entity;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.cookie.CookieManager;
import by.tr.web.cookie.CookieName;
import by.tr.web.domain.TvShow;
import by.tr.web.service.CountingServiceException;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.table.TableParameter;
import by.tr.web.service.table.TableService;
import by.tr.web.service.tv_show.TvShowService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeTvListImpl implements Command {
    private static final Logger logger = Logger.getLogger(TakeTvListImpl.class);
    /**
     * Command to take {@link List} of {@link TvShow} instances.
     *
     * <p>
     * Method retrieves specific number of {@link TvShow} objects from data base, starting from specific position
     * and in specified order.
     * All needed parameters could be sent with request. If not, data would be taken from cookies or
     * default values would be set. Default values are provided by {@link by.tr.web.service.table.TableConfigurationFactory}.
     * In case of success, formed {@code List<Movie>} is put as attribute in request.
     * </p>
     * <p>
     * After that request is forwarded to {@link JspPagePath#TVSHOWS_LIST_PAGE} page.
     * If an error occurs, {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR} will be sent.
     * </p>
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TvShowService tvShowService = ServiceFactory.getInstance().getTvShowService();
        TableService tableService = ServiceFactory.getInstance().getTableService();

        try {

            int numberOfRecords = tvShowService.countTvShow();

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.TVSHOW_CURRENT_PAGE);
            int currentPage = tableService.takeCurrentPage(request, TableParameter.TV_SHOWS_TABLE);
            request.setAttribute(TableParameter.PAGE, currentPage);

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.RECORDS_ON_TVSHOW_PAGE);
            int recordsOnPage = tableService.takeRecordsOnPage(request,TableParameter.TV_SHOWS_TABLE );
            request.setAttribute(TableParameter.RECORDS_ON_PAGE, recordsOnPage);

            request.setAttribute(FrontControllerParameter.COOKIE_NAME, CookieName.TVSHOW_ORDER);
            String orderType = tableService.takeTvShowOrderType(request);
            request.setAttribute(TableParameter.ORDER, orderType);

            int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentPage, numberOfRecords);

            int startRecordNum = (currentPage - 1) * recordsOnPage;
            String lang = RequestUtil.getLanguage(request);

            List<TvShow> tvShows = tvShowService.takeOrderedTvShowList(startRecordNum, recordsToTake, orderType, lang);
            request.setAttribute(JspAttribute.TV_SHOWS, tvShows);

            int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
            request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

            setCookies(request, response, orderType, currentPage, recordsOnPage);

            request.removeAttribute(FrontControllerParameter.COOKIE_NAME);
            request.getRequestDispatcher(JspPagePath.TVSHOWS_LIST_PAGE).forward(request, response);

        } catch (CountingServiceException e) {
            logger.error("Error while counting movies in DB", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ServiceException e) {
            logger.error("Error while getting movie list", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Saves tv-show's table parameters to cookies
     *
     * @param request
     * Request object
     * @param response
     * Response object
     * @param orderType
     * Table order type - one of {@link by.tr.web.domain.TvShow.TvShowOrderType}
     * @param currentPage
     * Number of page user was currently on
     * @param recordsOnPage
     * Total table records displayed on the page
     */
    private void setCookies(HttpServletRequest request, HttpServletResponse response,
                            String orderType, int currentPage, int recordsOnPage) {

        CookieManager cookieManager = new CookieManager(request);
        response.addCookie(cookieManager.makeCookie(CookieName.TVSHOW_ORDER, orderType));

        String currentPageString = Integer.toString(currentPage);
        response.addCookie(cookieManager.makeCookie(CookieName.TVSHOW_CURRENT_PAGE, currentPageString));

        String recordsOnPageString = Integer.toString(recordsOnPage);
        response.addCookie(cookieManager.makeCookie(CookieName.RECORDS_ON_TVSHOW_PAGE, recordsOnPageString));
    }
}
