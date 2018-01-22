package by.tr.web.tag;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.util.Util;
import by.tr.web.domain.User;
import by.tr.web.exception.controller.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserStatusTag extends TagSupport {
    private static final long serialVersionUID = 3614524300352289876L;
    private final static Logger logger = Logger.getLogger(UserStatusTag.class);
    private User.UserStatus userStatus;

    public void setUserStatus(User.UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public int doStartTag() throws JspException {
        if (userStatus == null) {
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
            String message = "Cannot write tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }
    private String formTag(ResourceBundle resourceBundle){
        String userStatusProperty = LocalizationPropertyKey.getUserStatusProperty(userStatus);
        String localizedStatus = resourceBundle.getString(userStatusProperty);
        return localizedStatus;
    }
}
