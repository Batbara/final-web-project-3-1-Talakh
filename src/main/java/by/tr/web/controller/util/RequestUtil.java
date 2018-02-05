package by.tr.web.controller.util;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.controller.constant.TableParameter;
import by.tr.web.domain.Show;
import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.ShowBuilder;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.ShowBuildService;
import by.tr.web.service.ShowService;
import by.tr.web.service.TableService;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.service.validation.DataTypeValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class RequestUtil {
    private RequestUtil() {
    }

    public static String getLanguage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String lang = request.getLocale().getLanguage();
        if (session.getAttribute(FrontControllerParameter.LOCALE) != null) {
            lang = (String) session.getAttribute(FrontControllerParameter.LOCALE);
        }
        return lang;
    }

    public static String formRedirectAddress(HttpServletRequest request) {
        String address = request.getParameter(JspAttribute.ADDRESS);
        if (address.isEmpty()) {
            return JspPagePath.INDEX;
        }
        String query = request.getParameter(FrontControllerParameter.QUERY);
        StringBuilder addressConstructor = new StringBuilder();
        if (!query.isEmpty()) {
            addressConstructor.append(JspPagePath.FRONT_CONTROLLER);
            addressConstructor.append("?");
            addressConstructor.append(query);
        } else {
            addressConstructor.append(address);
        }
        return addressConstructor.toString();
    }

    public static List<UserReview> takeReviewListForShow(HttpServletRequest request, int numberOfReviews, int showId)
            throws ServiceException {
        ShowService showService = ServiceFactory.getInstance().getShowService();
        TableService tableService = ServiceFactory.getInstance().getTableService();

        int currentReviewPage = tableService.takeCurrentPage(request);
        request.setAttribute(TableParameter.PAGE, currentReviewPage);

        int recordsOnPage = 5;
        int recordsToTake = tableService.calcRecordsToTake(recordsOnPage, currentReviewPage, numberOfReviews);


        int startRecordNum = (currentReviewPage - 1) * recordsOnPage;

        List<UserReview> showReviews = showService.takeShowReviewList(startRecordNum, recordsToTake,
                showId);

        int numOfPages = (int) Math.ceil((double) numberOfReviews / recordsOnPage);
        request.setAttribute(TableParameter.NUMBER_OF_PAGES, numOfPages);

        return showReviews;
    }

    public static void updateUserReviewList(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        if (session.getAttribute(JspAttribute.USER) == null) {
            return;
        }
        User user = (User) session.getAttribute(JspAttribute.USER);

        UserService userService = ServiceFactory.getInstance().getUserService();
        User updatedUser = userService.updateReviewList(user);

        session.setAttribute(JspAttribute.USER, updatedUser);
    }

    public static Show getShowTranslation(HttpServletRequest request, DataTypeValidator.Language language) throws ServiceException, IOException, ServletException {
        ShowBuildService buildService = ServiceFactory.getInstance().getShowBuildService();
        ShowBuilder translationShowBuilder = buildService.retrieveShowInfoTranslation(request, new MovieBuilder(),
                language);
        return translationShowBuilder.create();
    }


    public static String generateUniqueFileName(String fileName, DataTypeValidator.Language language) {
        String extension = getFileExtension(fileName);
        long currTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FrontControllerParameter.DEFAULT_TIMESTAMP_PATTERN);
        return language.toString()+dateFormat.format(new Date(currTime)) + extension;
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
