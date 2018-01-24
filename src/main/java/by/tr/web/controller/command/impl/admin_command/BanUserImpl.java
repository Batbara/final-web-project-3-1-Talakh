package by.tr.web.controller.command.impl.admin_command;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.exception.service.user.NoSuchUserException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;
import by.tr.web.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public class BanUserImpl implements Command {

    private static final Logger logger = Logger.getLogger(BanUserImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType(FrontControllerParameter.TEXT_HTML_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        try {
            User userToBan = retrieveUserToBan(request);

            BanInfo banInfo = formBanInfo(request);
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
        List<User> userList = (List<User>) request.getSession().getAttribute(JspAttribute.USER_LIST);
        int userBanID = Integer.parseInt(request.getParameter(JspAttribute.USER_BAN_ID));
        for (User user : userList) {
            if (user.getId() == userBanID) {
                return user;
            }
        }
        throw new NoSuchUserException("Cannot find user with id " + userBanID);
    }

    private BanReason getBanReason(HttpServletRequest request) throws ServiceException {
        String inputReason = request.getParameter(JspAttribute.BAN_REASON);
        List<BanReason> banReasonList = (List<BanReason>) request.getSession().getAttribute(JspAttribute.BAN_REASON_LIST);
        for (BanReason banReason : banReasonList) {
            if (banReason.getReason().equals(inputReason)) {
                return banReason;
            }
        }
        throw new ServiceException("Cannot get ban reason");
    }

    private BanInfo formBanInfo(HttpServletRequest request) throws ServiceException, ParseException {
        String banTimeParameter = request.getParameter(JspAttribute.BAN_TIME);
        String unbanTimeParameter = request.getParameter(JspAttribute.UNBAN_TIME);

        BanInfo banInfo = new BanInfo();

        BanReason banReason = getBanReason(request);
        banInfo.setBanReason(banReason);

        Timestamp banTime = Util.getTimeFromString(banTimeParameter, FrontControllerParameter.DEFAULT_TIME_PATTERN);
        banInfo.setBanTime(banTime);

        Timestamp unbanTime = Util.getTimeFromString(unbanTimeParameter, FrontControllerParameter.DEFAULT_TIME_PATTERN);
        banInfo.setUnbanTime(unbanTime);

        return banInfo;
    }

}
