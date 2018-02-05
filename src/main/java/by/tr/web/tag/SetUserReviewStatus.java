package by.tr.web.tag;

import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class SetUserReviewStatus extends TagSupport {
    private static final long serialVersionUID = -1485640558336378470L;
    private final static Logger logger = Logger.getLogger(SetUserReviewStatus.class);
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

        List<UserReview> userReviews = user.getUserReviews();
        for(UserReview review : userReviews){
            if(review.getShowId() == showId && review.getReviewContent() != null){
                return review.getReviewStatus().toString();
            }
        }
        return UserReview.ReviewStatus.NONE.toString();
    }
}
