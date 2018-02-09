package by.tr.web.tag.tv_show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.constant.RequestUtil;
import by.tr.web.domain.TvShow;
import by.tr.web.tag.CustomTagLibException;
import by.tr.web.tag.CustomTagLibParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TvShowStatusListTag extends TagSupport {
    private static final long serialVersionUID = -6190204393295677155L;
    private final static Logger logger = Logger.getLogger(TvShowStatusListTag.class);

    @Override
    public int doStartTag() throws JspException {
        String tag = formTag();
        JspWriter out = pageContext.getOut();
        try {
            out.write(tag);
            return SKIP_BODY;
        } catch (IOException e) {
            String message = "Cannot write tv-show status list to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }
    private String formTag(){
        String lang = RequestUtil.getLanguage((HttpServletRequest) pageContext.getRequest());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FrontControllerParameter.LOCALIZATION_BUNDLE_NAME,
                Locale.forLanguageTag(lang));

        StringBuilder tag = new StringBuilder();
        TvShow.ShowStatus[] showStatuses = TvShow.ShowStatus.values();
        for (TvShow.ShowStatus status : showStatuses){

            String translatedStatus = getTranslation(status, resourceBundle);
            String optionValue = status.toString();

            tag.append(String.format(CustomTagLibParameter.OPTION_TAG, optionValue, translatedStatus));
        }
        return tag.toString();
    }
    private String getTranslation(TvShow.ShowStatus status, ResourceBundle resourceBundle) {
        String userStatusProperty = LocalizationPropertyKey.getTvShowStatusProperty(status);
        String localizedStatus = resourceBundle.getString(userStatusProperty);
        return localizedStatus;
    }
}
