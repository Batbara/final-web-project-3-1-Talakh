package by.tr.web.controller.util;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.domain.BanInfo;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.tag.CustomTagLibParameter;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class TypeFormatUtil {
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

    public static String translateBanInfo(BanInfo banInfo, String lang){

        ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALIZATION_BUNDLE_NAME,
                Locale.forLanguageTag(lang));

        StringBuilder banInfoBuilder = new StringBuilder();

        String banMessage = resourceBundle.getString(LocalizationPropertyKey.USER_BAN_INFO);

        SimpleDateFormat dateFormat = new SimpleDateFormat(FrontControllerParameter.SIMPLE_DATE_PATTERN);
        String banTime = dateFormat.format(banInfo.getBanTime());

        appendPair(banMessage, banTime, banInfoBuilder);

        String reasonMessage = resourceBundle.getString(LocalizationPropertyKey.USER_BAN_REASON);
        String banReason = banInfo.getBanReason().getReason();
        appendPair(reasonMessage, banReason, banInfoBuilder);
        return banInfoBuilder.toString();
    }
    public static String generateUniqueFileName(String fileName, DataTypeValidator.Language language) {
        String extension = getFileExtension(fileName);
        long currTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FrontControllerParameter.FILENAME_TIMESTAMP_PATTERN);
        return language.toString()+dateFormat.format(new Date(currTime)) + extension;
    }
    public static String getSourcePath(String path) {

        ClassLoader classLoader = TypeFormatUtil.class.getClassLoader();
        URL sourceURL = classLoader.getResource(path);
        assert sourceURL != null;
        String rawPath = sourceURL.getPath();

        String incorrectDriveLetterRegEx = "^/(.:/)";
        String capturedGroup = "$1";
        String correctPath = rawPath.replaceFirst(incorrectDriveLetterRegEx, capturedGroup);

        return correctPath;

    }
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    private static void appendPair(String message, String value, StringBuilder builder) {
        builder.append(message);
        builder.append(CustomTagLibParameter.COLON_DELIMITER);
        builder.append(value);
        builder.append(CustomTagLibParameter.BREAK_LINE_TAG);
    }
}
