package by.tr.web.tag.user;

import by.tr.web.domain.Review;
import by.tr.web.domain.User;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class SetUserReviewStatusTag extends TagSupport {
    private static final long serialVersionUID = -1485640558336378470L;
    private final static Logger logger = Logger.getLogger(SetUserReviewStatusTag.class);
    private int showId;
    private User user;
    private String var;



    public void setVar(String var) {
        this.var = var;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        if(user == null || showId == 0){
            return SKIP_BODY;
        }
        pageContext.setAttribute(var, formAttribute());
        return super.doStartTag();
    }

    private String formAttribute(){

        List<Review> reviews = user.getReviews();
        for(Review review : reviews){
            if(review.getShowId() == showId && review.getReviewContent() != null){
                return review.getReviewStatus().toString();
            }
        }
        return Review.ReviewStatus.NONE.toString();
    }
}
