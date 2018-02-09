package by.tr.web.tag.movie;

import by.tr.web.domain.Movie;
import by.tr.web.tag.CustomTagLibException;
import by.tr.web.tag.CustomTagLibParameter;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MpaaSelectionListTag extends TagSupport {
    private static final long serialVersionUID = 2977350320360175039L;

    private final static Logger logger = Logger.getLogger(MpaaSelectionListTag.class);

    @Override
    public int doStartTag() throws JspException {
        String tag = formTag();
        JspWriter out = pageContext.getOut();
        try {
            out.write(tag);
            return SKIP_BODY;
        } catch (IOException e) {
            String message = "Cannot write MPAA rating list to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }
    private String formTag(){
        StringBuilder tag = new StringBuilder();
        Movie.MPAARating[] mpaaRatings = Movie.MPAARating.values();
        for (Movie.MPAARating rating : mpaaRatings){
            String optionValue = rating.toString();
            String optionFormatted = formatMpaaRating(rating);
            tag.append(String.format(CustomTagLibParameter.OPTION_TAG, optionValue, optionFormatted));
        }
        return tag.toString();
    }
    private String formatMpaaRating(Movie.MPAARating rating){

        return rating.toString().replace("_", "-");
    }

}
