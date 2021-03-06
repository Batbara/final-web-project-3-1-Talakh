package by.tr.web.tag.show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.LocalizationPropertyKey;
import by.tr.web.controller.util.RequestUtil;
import by.tr.web.domain.Show;
import by.tr.web.domain.User;
import by.tr.web.tag.CustomTagLibException;
import by.tr.web.tag.CustomTagLibParameter;
import by.tr.web.tag.user.BanInfoTag;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowRatingTag extends TagSupport {

    private static final long serialVersionUID = -1598688813014842294L;
    private final static Logger logger = Logger.getLogger(BanInfoTag.class);
    private User user;
    private Show show;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public int doStartTag() throws JspException {
        if (show == null) {
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
            String message = "Cannot write show rating tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }

    private String formTag(ResourceBundle resourceBundle) {
        double userRating = show.getUserRating();

        String formattedRating = Double.toString(userRating);
        String totalRating = resourceBundle.getString(LocalizationPropertyKey.SHOW_RATING);

        StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append(totalRating + CustomTagLibParameter.COLON_DELIMITER);
        tagBuilder.append(formattedRating);

        if (user != null) {
            appendUserRating(tagBuilder, resourceBundle);
        }
        return tagBuilder.toString();
    }

    private void appendUserRating(StringBuilder tagBuilder, ResourceBundle resourceBundle) {
        int showId = show.getShowId();
        int userRate = user.getUserRateForShow(showId);
        if (userRate != 0) {
            tagBuilder.append(CustomTagLibParameter.BREAK_LINE_TAG);
            String userRateLine = resourceBundle.getString(LocalizationPropertyKey.USER_CUSTOM_RATE);
            tagBuilder.append(userRateLine + CustomTagLibParameter.COLON_DELIMITER);
            tagBuilder.append(userRate);
        }
    }


}
