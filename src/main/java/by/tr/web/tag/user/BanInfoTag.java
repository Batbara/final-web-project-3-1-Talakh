package by.tr.web.tag.user;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.domain.BanInfo;
import by.tr.web.domain.User;
import by.tr.web.tag.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class BanInfoTag extends TagSupport {
    private static final long serialVersionUID = 8345449844325563639L;
    private final static Logger logger = Logger.getLogger(BanInfoTag.class);
    private final static String BAN_INFO_TAG = "<p>%s</p>";
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FrontControllerParameter.SIMPLE_DATE_PATTERN);
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        if (user == null) {
            return SKIP_BODY;
        }

        String lang = RequestUtil.getLanguage((HttpServletRequest) pageContext.getRequest());

        String tag = formTag(lang);
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

    private String formTag(String lang) {
        BanInfo banInfo = user.getBanInfo();
        String banInfoTranslated = TypeFormatUtil.translateBanInfo(banInfo, lang);
        return String.format(BAN_INFO_TAG, banInfoTranslated);
    }

}
