package by.tr.web.controller.constant;

import by.tr.web.service.input_validator.RequestParameterNotFound;

import java.sql.Time;
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

    public static Timestamp getTimestampFromString(String time, String timePattern) throws ParseException, RequestParameterNotFound {
        Date date = parseToDate(time, timePattern);
        return new Timestamp(date.getTime());
    }
    public static Time getTimeFromString(String time, String timePattern) throws ParseException, RequestParameterNotFound {

        Date date = parseToDate(time, timePattern);
        return new Time(date.getTime());
    }
    public static java.sql.Date getDateFromString (String input, String datePattern) throws ParseException, RequestParameterNotFound {
        java.util.Date date = parseToDate(input, datePattern);
        return new java.sql.Date(date.getTime());
    }
    private static java.util.Date parseToDate (String input, String patten) throws ParseException, RequestParameterNotFound {
        if(input == null){
            throw new RequestParameterNotFound("Date input is empty");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(patten);

        return dateFormat.parse(input);
    }
}
