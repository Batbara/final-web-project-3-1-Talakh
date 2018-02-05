package by.tr.web.tag;

import by.tr.web.controller.constant.CustomTagLibParameter;
import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.TvShow;
import by.tr.web.exception.controller.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TvShowRefTag extends TagSupport {
    private static final long serialVersionUID = -1563756686967948695L;

    private final static Logger logger = Logger.getLogger(TvShowRefTag.class);
    private final static String TVSHOW_REF_TAG = "<span>%s<span>";
    private TvShow tvShow;

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

    @Override
    public int doStartTag() throws JspException {
        if (tvShow == null) {
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
            String message = "Cannot write tv-show ref naming tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }

    private String formTag(ResourceBundle resourceBundle) {
        String tvSeriesType = resourceBundle.getString(LocalizationPropertyKey.TV_SERIES);

        StringBuilder refNameBuilder = new StringBuilder();

        refNameBuilder.append(tvShow.getTitle()+CustomTagLibParameter.SPACE_DELIMITER);
        refNameBuilder.append(CustomTagLibParameter.OPEN_BRACKET + tvSeriesType);
        appendTvShowYears(refNameBuilder);

        return refNameBuilder.toString();
    }

    private void appendTvShowYears(StringBuilder builder) {
        builder.append(CustomTagLibParameter.COMMA_DELIMITER + tvShow.getYear());

        builder.append(CustomTagLibParameter.SPACE_DELIMITER +
                CustomTagLibParameter.DASH_DELIMITER +
                CustomTagLibParameter.SPACE_DELIMITER);

        int finishingYear = tvShow.getFinishedYear();

        if (finishingYear == 0) {
            builder.append(CustomTagLibParameter.THREE_DOTS + CustomTagLibParameter.CLOSE_BRACKET);
        } else {
            builder.append(finishingYear + CustomTagLibParameter.CLOSE_BRACKET);
        }
    }

}
