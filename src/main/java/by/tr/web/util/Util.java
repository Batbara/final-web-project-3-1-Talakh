package by.tr.web.util;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Util {
    private Util(){}

    public static String getLanguage (HttpServletRequest request){
        HttpSession session = request.getSession();
        String lang = request.getLocale().getLanguage();
        if (session.getAttribute(FrontControllerParameter.LOCALE) != null) {
            lang = (String) session.getAttribute(FrontControllerParameter.LOCALE);
        }
        return lang;
    }
    public static String formRedirectAddress(HttpServletRequest request) {
        String address = request.getParameter(JspAttribute.ADDRESS);
        if(address.isEmpty()){
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
    public static Timestamp getTimeFromString(String time, String timePattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);

        Date parsedTimeStamp = dateFormat.parse(time);

        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        return timestamp;
    }
}
