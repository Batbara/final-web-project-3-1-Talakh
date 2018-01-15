package by.tr.web.controller.constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class Util {
    private Util(){}
    public static int calcTableRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords){
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
    public static String getLanguage (HttpServletRequest request){
        HttpSession session = request.getSession();
        String lang = request.getLocale().getLanguage();
        if (session.getAttribute(FrontControllerParameter.LOCALE) != null) {
            lang = (String) session.getAttribute(FrontControllerParameter.LOCALE);
        }
        return lang;
    }
}
