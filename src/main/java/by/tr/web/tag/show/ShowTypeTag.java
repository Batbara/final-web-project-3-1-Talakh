package by.tr.web.tag.show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.constant.RequestUtil;
import by.tr.web.domain.Show;
import by.tr.web.tag.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowTypeTag extends TagSupport {
    private static final long serialVersionUID = 9149838996198198661L;
    private final static Logger logger = Logger.getLogger(ShowTypeTag.class);
    private Show.ShowType showType;

    public void setShowType(Show.ShowType showType) {
        this.showType = showType;
    }

    @Override
    public int doStartTag() throws JspException {
        if (showType == null) {
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
            String message = "Cannot write show type tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }
    private String formTag(ResourceBundle resourceBundle){
        if(showType == Show.ShowType.TV_SERIES){
            return resourceBundle.getString(LocalizationPropertyKey.TV_SERIES);
        }
        if(showType == Show.ShowType.MOVIE){
            return resourceBundle.getString(LocalizationPropertyKey.MOVIE);
        }
        return "-";
    }
}
