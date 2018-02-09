package by.tr.web.tag.tv_show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.constant.RequestUtil;
import by.tr.web.domain.TvShow;
import by.tr.web.tag.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TvShowStatusTag extends TagSupport {
    private static final long serialVersionUID = -5454557719008511509L;
    private final static Logger logger = Logger.getLogger(TvShowStatusTag.class);

    private static final String STATUS_TAG = "<sup title=\"%s\" class=\"status %s\"></sup>";
    private TvShow.ShowStatus status;

    public void setStatus(TvShow.ShowStatus tvShowStatus) {
        this.status = tvShowStatus;

    }

    @Override
    public int doStartTag() throws JspException {
        if (status == null) {
            return SKIP_BODY;
        }

        String lang = RequestUtil.getLanguage((HttpServletRequest) pageContext.getRequest());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALIZATION_BUNDLE_NAME,
                Locale.forLanguageTag(lang));
        String tag = formTag(resourceBundle);
        JspWriter out = pageContext.getOut();
        try {
            out.write(tag);
            return SKIP_BODY;
        } catch (IOException e) {
            String message = "Cannot write tv-show status tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }

    private String formTag(ResourceBundle resourceBundle) {
        String tvShowStatusProperty = LocalizationPropertyKey.getTvShowStatusProperty(status);
        String localizedTitle = resourceBundle.getString(tvShowStatusProperty);
        String cssSelectorName = status.toString().toLowerCase();
        return String.format(STATUS_TAG, localizedTitle, cssSelectorName);
    }
}
