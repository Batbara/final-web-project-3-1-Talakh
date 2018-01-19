package by.tr.web.tag;

import by.tr.web.domain.User;
import by.tr.web.exception.controller.CustomTagLibException;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RegistrationDateTag extends TagSupport {
    private static final long serialVersionUID = 5173190424017764710L;
    private final static Logger logger = Logger.getLogger(RegistrationDateTag.class);
    private User user;
    private static String pattern = "dd.MM.yyyy";

    public void setUser(User user) {
        this.user = user;
    }

    public static void setPattern(String pattern) {
        RegistrationDateTag.pattern = pattern;
    }

    @Override
    public int doStartTag() throws JspException {
        if (user == null) {
            return SKIP_BODY;
        }

        JspWriter out = pageContext.getOut();
        try {
            String tag = formTag();
            out.write(tag);
            return SKIP_BODY;
        } catch (IOException e) {
            String message = "Cannot write tag to page";
            logger.error(message, e);
            throw new CustomTagLibException(message, e);
        }
    }

    private String formTag() {
        Timestamp regDate = user.getRegistrationDate();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(regDate);
    }
}
