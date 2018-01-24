package by.tr.web.tag;

import by.tr.web.controller.constant.CustomTagLibParameter;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.User;
import by.tr.web.exception.controller.CustomTagLibException;
import by.tr.web.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class BanInfoTag extends TagSupport {
    private static final long serialVersionUID = 8345449844325563639L;
    private final static Logger logger = Logger.getLogger(BanInfoTag.class);
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        if (user == null) {
            return SKIP_BODY;
        }

        String lang = Util.getLanguage((HttpServletRequest) pageContext.getRequest());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALISATION_BUNDLE_NAME,
                                                                  Locale.forLanguageTag(lang));
        String tag = formTag(resourceBundle);
        JspWriter out = pageContext.getOut();
        try {
            out.write(tag);
            return SKIP_BODY;
        } catch (IOException e) {
            String message = "Cannot write ban info tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }

    private String formTag(ResourceBundle resourceBundle) {
        BanInfo banInfo = user.getBanInfo();
        StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append(CustomTagLibParameter.P_OPEN_TAG);

        String ban = resourceBundle.getString(LocalizationPropertyKey.USER_BAN_INFO);
        String banTime = banInfo.getBanTime().toString();
        appendPair(ban, banTime, tagBuilder);

        String unban = resourceBundle.getString(LocalizationPropertyKey.USER_UNBAN_INFO);
        String unbanTime = banInfo.getUnbanTime().toString();
        appendPair(unban, unbanTime, tagBuilder);

        String reasonMessage = resourceBundle.getString(LocalizationPropertyKey.USER_BAN_REASON);
        String banReason = banInfo.getBanReason().getReason();
        appendPair(reasonMessage, banReason, tagBuilder);

        tagBuilder.append(CustomTagLibParameter.P_CLOSE_TAG);
        return tagBuilder.toString();
    }

    private void appendPair(String message, String value, StringBuilder builder) {
        builder.append(message);
        builder.append(CustomTagLibParameter.COLON_DELIMETER);
        builder.append(value);
        builder.append(CustomTagLibParameter.BREAK_LINE_TAG);
    }
}
