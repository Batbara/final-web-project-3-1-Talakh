package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JSPAttribute;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BanUserImpl implements Command {

    private static final Logger logger = Logger.getLogger(BanUserImpl.class);
    private String TIME_PATTERN = "yyyy-MM-dd'T'hh:mm:ss";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String banTime = request.getParameter(JSPAttribute.BAN_TIME);
        String unbanTime = request.getParameter(JSPAttribute.UNBAN_TIME);

        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        try {
            User userToBan = retrieveUserToBan(request);
            BanReason banReason = getBanReason(request);
            BanInfo banInfo = new BanInfo(getTimeFromString(banTime), getTimeFromString(unbanTime), banReason);

            userToBan.setBanInfo(banInfo);
            UserService userService = ServiceFactory.getInstance().getUserService();

            userService.banUser(userToBan);

            out.print(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ParseException e) {
            logger.error("Cannot parse input time", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (NoSuchUserException e) {
            logger.error("Cannot find user", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred", e);
            out.print(FrontControllerParameter.FAILURE_RESPONSE);
        }

    }

    private User retrieveUserToBan(HttpServletRequest request) throws ServiceException {
        List<User> userList = (List<User>) request.getSession().getAttribute(JSPAttribute.USER_LIST);
        int userBanID = Integer.parseInt(request.getParameter(JSPAttribute.USER_BAN_ID));
        for (User user : userList) {
            if (user.getId() == userBanID) {
                return user;
            }
        }
        throw new NoSuchUserException("Cannot find user with id " + userBanID);
    }

    private BanReason getBanReason(HttpServletRequest request) throws ServiceException {
        String inputReason = request.getParameter(JSPAttribute.BAN_REASON);
        List<BanReason> banReasonList = (List<BanReason>) request.getSession().getAttribute(JSPAttribute.BAN_REASON_LIST);
        for (BanReason banReason : banReasonList) {
            if (banReason.getReason().equals(inputReason)) {
                return banReason;
            }
        }
        throw new ServiceException("Cannot get ban reason");
    }

    private Timestamp getTimeFromString(String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_PATTERN);

        Date parsedTimeStamp = dateFormat.parse(time);

        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        return timestamp;
    }

}
