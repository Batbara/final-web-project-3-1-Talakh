package by.tr.web.controller.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    public static int getYearFromDate(java.sql.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        return year;
    }

    public static Timestamp getTimeFromString(String time, String timePattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);

        Date parsedTimeStamp = dateFormat.parse(time);

        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        return timestamp;
    }
}
