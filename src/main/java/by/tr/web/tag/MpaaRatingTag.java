package by.tr.web.tag;

import by.tr.web.domain.Movie;
import by.tr.web.exception.controller.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MpaaRatingTag extends TagSupport {
    private static final long serialVersionUID = 3834049801750001230L;
    private final static Logger logger = Logger.getLogger(MpaaRatingTag.class);
    private Movie.MPAARating mpaaRating;

    public void setMpaaRating(Movie.MPAARating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    @Override
    public int doStartTag() throws JspException {
        if (mpaaRating == null) {
            return SKIP_BODY;
        }
        String tag = formTag();
        JspWriter out = pageContext.getOut();
        try {
            out.write(tag);
            return SKIP_BODY;
        } catch (IOException e) {
            String message = "Cannot write MPAA rating tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }
    private String formTag(){
        if(mpaaRating == Movie.MPAARating.NONE){
            return "-";
        }
        return mpaaRating.toString().replace("_", "-");
    }
}
