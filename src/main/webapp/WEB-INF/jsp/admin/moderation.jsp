<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.user.administration.page" var="administration"/>
    <fmt:message bundle="${loc}" key="local.admin.link.reviews" var="reviewsModer"/>

    <fmt:message bundle="${loc}" key="local.info.user.banReason" var="banReason"/>
    <fmt:message bundle="${loc}" key="local.info.user.ban" var="ban"/>
    <fmt:message bundle="${loc}" key="local.info.user.unban" var="unban"/>
    <fmt:message bundle="${loc}" key="local.info.user.banTime" var="banTime"/>
    <fmt:message bundle="${loc}" key="local.info.user.select.status" var="selectStatusMessage"/>
    <fmt:message bundle="${loc}" key="local.table.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.response.message.error" var="errorMessage"/>
    <fmt:message bundle="${loc}" key="local.message.delete.review.success" var="deleteReviewSuccessfully"/>
    <fmt:message bundle="${loc}" key="local.message.post.review.success" var="postReviewSuccessfully"/>
    <fmt:message bundle="${loc}" key="local.response.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.response.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.control.confirm" var="confirm"/>
    <fmt:message bundle="${loc}" key="local.control.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.control.change" var="change"/>
    <fmt:message bundle="${loc}" key="local.admin.review.post" var="postReview"/>
    <fmt:message bundle="${loc}" key="local.admin.review.delete" var="deleteReview"/>
    <fmt:message bundle="${loc}" key="local.message.no.reviews" var="noNewReviewsMessage"/>

    <title>${administration} | ${reviewsModer} | MotionPicture Bank [MPB]</title>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table-style.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/review.css">
</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<div class="main">
    <nav>
        <div data-spy="affix">
            <c:import url="/WEB-INF/jsp/admin/adminNav.jsp"/>
        </div>

    </nav>
    <article>

        <c:choose>
            <c:when test="${requestScope.reviewsOnModerationList == null}">
                <div class="fluid-container center-block">
                    <div class="col-md-5 col-sm-offset-4">
                        <div class="alert alert-info text-center" id="noReviewsMessage" style="margin-top: 50px">
                            ${noNewReviewsMessage}
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="fluid-container">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="onPage pull-left">
                                <c:import url="/WEB-INF/jsp/table_control/onPageForm.jsp">
                                    <c:param name="command" value="take_reviews_moder"/>
                                </c:import>
                            </div>
                            <nav class="navigation pull-right">

                                <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                                    <c:param name="command" value="take_reviews_moder"/>
                                </c:import>
                            </nav>
                        </div>
                    </div>
                </div>
                <c:forEach var="review" items="${requestScope.reviewsOnModerationList}">
                    <div class="${review.showId}${review.user.id}">
                        <div class="review-row">

                            <div class="review overlay">
                                <div class="post-info">
                                    <div class="author">

                        <span data-toggle="tooltip" data-placement="left"
                              title="<mpb:user-status userStatus="${review.user.userStatus}"/>"
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

                        </div>
                        <div class="review-control text-center">

                            <form class="form-inline"
                                  method="post" action="${pageContext.request.contextPath}/mpb"
                                  id="postReviewForm${review.showId}${review.user.id}">
                                <input type="hidden" name="command" value="post_review">
                                <input type="hidden" name="showId" value="${review.showId}" id="showId">
                                <input type="hidden" name="userId" value="${review.user.id}" id="userId">
                                <span data-toggle="tooltip" data-placement="bottom" title="${postReview}">
                            <a data-toggle="modal" id="postReviewButton" onclick="postReview(this)"
                               class="postReview ${review.showId}${review.user.id}" href="javascript:{}">
                                 <span class="glyphicon glyphicon-ok"></span>
                            </a>
                    </span>
                            </form>
                            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/mpb"
                                  id="deleteReviewForm${review.showId}${review.user.id}">
                                <input type="hidden" name="command" value="delete_review">
                                <input type="hidden" name="showId" value="${review.showId}">
                                <input type="hidden" name="userId" value="${review.user.id}">
                                <span data-toggle="tooltip" data-placement="bottom" title="${deleteReview}">
                            <a data-toggle="modal" id="deleteReviewButton" onclick="deleteReview(this)"
                               class="deleteReview ${review.showId}${review.user.id}" href="javascript:{}">
                                 <span class="glyphicon glyphicon-remove"></span>
                            </a>
                    </span>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                <div class="fluid-container">
                    <div class="fluid-row">
                        <div class="col-sm-12">
                            <nav class="navigation pull-right">

                                <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                                    <c:param name="command" value="take_reviews_moder"/>
                                </c:import>
                            </nav>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
<c:import url="/WEB-INF/jsp/table_control/tableScripts.jsp"/>

<script src="${pageContext.request.contextPath}/js/content.js"></script>

<script src="${pageContext.request.contextPath}/js/review-submitter.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#noReviewsMessage').show();
    })
</script>
<div class="alert alert-danger alert-dismissable on-top-message" id="reviewFailureAlert">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>${failureMessage}!</strong> ${errorMessage}
</div>
<div class="alert alert-success alert-dismissable on-top-message" id="postSuccessAlert">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>${successMessage}!</strong> ${postReviewSuccessfully}
</div>
<div class="alert alert-success alert-dismissable on-top-message" id="deleteReviewSuccessAlert">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>${successMessage}!</strong> ${deleteReviewSuccessfully}
</div>
</body>
</html>
