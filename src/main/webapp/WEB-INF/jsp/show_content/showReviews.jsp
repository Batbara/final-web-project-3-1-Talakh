<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.show.sidenav.reviews" var="reviewsNav"/>
<fmt:message bundle="${loc}" key="local.show.reviews.total" var="totalReviews"/>
<fmt:message bundle="${loc}" key="local.show.review.content" var="reviewContent"/>
<fmt:message bundle="${loc}" key="local.show.review.title" var="reviewTitle"/>
<fmt:message bundle="${loc}" key="local.show.sidenav.add.review" var="addReview"/>
<fmt:message bundle="${loc}" key="local.message.to.login" var="toLogInMessage"/>
<fmt:message bundle="${loc}" key="local.message.to.register" var="toRegisterMessage"/>
<fmt:message bundle="${loc}" key="local.message.add.review" var="toAddReivewMessage"/>
<fmt:message bundle="${loc}" key="local.message.or" var="orMessage"/>
<fmt:message bundle="${loc}" key="local.message.review.moderated" var="reviewOnModeration"/>
<fmt:message bundle="${loc}" key="local.message.review.posted" var="reviewPosted"/>

<fmt:message bundle="${loc}" key="local.message.success" var="successMessage"/>
<fmt:message bundle="${loc}" key="local.message.reviewSent" var="reviewSentMessage"/>
<fmt:message bundle="${loc}" key="local.message.oops" var="oopsMessage"/>
<fmt:message bundle="${loc}" key="local.message.reviewDenied" var="reviewDeniedMessage"/>
<fmt:message bundle="${loc}" key="local.show.reviews.none" var="noneReviewsMessage"/>
<fmt:message bundle="${loc}" key="local.show.reviews.total" var="totalReviewsNumMessage"/>

<jsp:useBean id="show" class="by.tr.web.domain.Show" type="by.tr.web.domain.Show" scope="request"/>

<div class="reviews">
    <h3 id="reviews"><c:out value="${reviewsNav}"/></h3>

    <div class="reviews-wrapper">
        <div class="reviews-info">
            <c:choose>
                <c:when test="${requestScope.reviewsNumber == 0}">
                    <div>${noneReviewsMessage}</div>
                </c:when>
                <c:otherwise>
                    <div>${totalReviewsNumMessage}: <c:out value="${requestScope.reviewsNumber}"/></div>
                    <nav class="navigation pull-right">
                        <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                            <c:param name="command" value="${param.command}"/>
                        </c:import>
                    </nav>
                </c:otherwise>
            </c:choose>

        </div>
        <c:forEach var="review" items="${show.reviewList}">
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
        </c:forEach>
    </div>
</div>
<div class="addReview">
    <h3 id="addReview"><c:out value="${addReview}"/></h3>

    <mpb:set-review-status showId="${show.showID}" user="${sessionScope.user}" var="userReviewStatus"/>
    <c:choose>
        <c:when test="${userReviewStatus == 'POSTED'}">
            <div class="alert alert-success center-text user-review-info">

                <strong>${reviewPosted}</strong>
            </div>
        </c:when>
        <c:when test="${userReviewStatus == 'MODERATED'}">
            <div class="alert alert-info center-text user-review-info">

                <strong>${reviewOnModeration}</strong>
            </div>
        </c:when>
        <c:otherwise>
            <div class="add-form-wrapper" id="reviewFormWrapper">
                <c:if test="${empty sessionScope.user}">

                    <div class="alert alert-warning">${toAddReivewMessage}
                        <form id="to-login-form" action="${pageContext.request.contextPath}/login" method="post"
                              class="inline-form">

                            <input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                            <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
                            <a href="javascript:{}"
                               onclick="$('#to-login-form').submit(); return false;" class="alert-link">
                                <c:out value="${toLogInMessage}"/>
                            </a>
                        </form>
                            ${orMessage}

                        <a href="${pageContext.request.contextPath}/register" class="alert-link">${toRegisterMessage}</a>.
                    </div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/mpb" id="add-review-form">
                    <input type="hidden" name="command" value="add_review">
                    <input type="hidden" name="showId" value="${show.showID}">

                    <div class="form-group add-review-form center-block">
                        <fieldset id="addReviewFields">
                            <label for="reviewTitle">${reviewTitle}:</label>
                            <input type="text" name="reviewTitle" class="form-control" id="reviewTitle" required>
                            <label for="reviewContent">${reviewContent}:</label>
                            <textarea class="form-control" rows="5" id="reviewContent" name="reviewContent" required></textarea>
                            <button id="addReviewButton" type="submit"
                                    class="btn btn-default center-block add-review-button">${addReview}</button>

                        </fieldset>
                    </div>
                </form>

            </div>
        </c:otherwise>
    </c:choose>

</div>
<div class="modal fade" id="reviewPostSuccessful" role="dialog">
    <div class="modal-dialog modal-md">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;
                </button>
                <h3 class="modal-title">${successMessage}</h3>
            </div>
            <div class="modal-body">
                ${reviewSentMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="reviewPostFailed" role="dialog">
    <div class="modal-dialog modal-md">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;
                </button>
                <h3 class="modal-title">${oopsMessage}</h3>
            </div>
            <div class="modal-body">
                <div class="fluid-container">
                    ${reviewDeniedMessage}
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>