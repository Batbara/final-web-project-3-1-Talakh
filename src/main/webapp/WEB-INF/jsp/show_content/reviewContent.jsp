<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<div class="review">
    <div class="post-info">
        <div class="author">

                        <span data-toggle="tooltip" data-placement="left" title="<mpb:user-status userStatus="${review.user.userStatus}"/>"
                              class="glyphicon glyphicon-user"></span>
            <span class="name"><c:out value="${review.user.userName}"/></span>

        </div>

        <div class="postDate">
            <fmt:formatDate type="both"
                            dateStyle="short" timeStyle="short" value="${review.postDate}"/>

        </div>
    </div>

    <h4 class="review-title">${review.reviewTitle}</h4>
    <div class="reviewContent">
        <p>
            <c:out value="${review.reviewContent}"/>
        </p>
    </div>

</div>