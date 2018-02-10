package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.BanReason;
import by.tr.web.domain.User;
import by.tr.web.service.ServiceException;
import by.tr.web.service.ServiceFactory;
import by.tr.web.service.user.UserService;
import by.tr.web.service.user.exception.NoSuchUserException;
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

    /**
     * Command to ban user
     * <p>
     * Method retrieves all needed user data from request, validates it and tries to ban requested user.
     * In case of success, {@link FrontControllerParameter#SUCCESS_RESPONSE} is written to response during AJAX call.
     * If an error occurs, {@link FrontControllerParameter#FAILURE_RESPONSE} will be written to response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType(FrontControllerParameter.TEXT_PLAIN_CONTENT_TYPE);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter out = response.getWriter();

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User userToBan = userService.retrieveUserFromRequest(request, JspAttribute.USER_BAN_ID);

            BanInfo banInfo = formBanInfo(request);
            userToBan.setBanInfo(banInfo);

            userService.banUser(userToBan);

            out.write(FrontControllerParameter.SUCCESS_RESPONSE);
        } catch (ParseException e) {
            logger.error("Cannot parse input time", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (NoSuchUserException e) {
            logger.error("Cannot find user", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        } catch (ServiceException e) {
            logger.error("An error occurred", e);
            out.write(FrontControllerParameter.FAILURE_RESPONSE);
        }

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

        BanInfo banInfo = new BanInfo();

        BanReason banReason = getBanReason(request);
        banInfo.setBanReason(banReason);

        Timestamp banTime = TypeFormatUtil.getTimestampFromString(banTimeParameter, FrontControllerParameter.DEFAULT_TIMESTAMP_PATTERN);
        banInfo.setBanTime(banTime);

        return banInfo;
    }

}
