package by.tr.web.controller.constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static Timestamp getTimeFromString(String time, String timePattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);

        Date parsedTimeStamp = dateFormat.parse(time);

        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        return timestamp;
    }
}
