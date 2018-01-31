<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <c:import url="/WEB-INF/jsp/styling.jsp"/>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.navButton.admin" var="title"/>
    <fmt:message bundle="${loc}" key="local.info.user.banReason" var="banReason"/>
    <fmt:message bundle="${loc}" key="local.info.user.ban" var="ban"/>
    <fmt:message bundle="${loc}" key="local.info.user.unban" var="unban"/>
    <fmt:message bundle="${loc}" key="local.info.user.banTime" var="banTime"/>
    <fmt:message bundle="${loc}" key="local.info.user.select.status" var="selectStatusMessage"/>
    <fmt:message bundle="${loc}" key="local.table.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.message.errorMessage" var="errorMessage"/>
    <fmt:message bundle="${loc}" key="local.message.banSuccessful" var="banSuccesful"/>
    <fmt:message bundle="${loc}" key="local.message.change.status.successful" var="changeStatusSuccess"/>
    <fmt:message bundle="${loc}" key="local.message.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.message.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.message.confirm" var="confirm"/>
    <fmt:message bundle="${loc}" key="local.message.unbanConfirmation" var="unbanConfirmation"/>
    <fmt:message bundle="${loc}" key="local.message.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.message.change" var="change"/>

    <title>${title} | MotionPicture Bank [MPB]</title>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table-style.css">

</head>
<body>
<c:import url="/WEB-INF/jsp/header/header.jsp"/>
<div class="main">
    <nav>
        <div data-spy="affix">
            <c:import url="/WEB-INF/jsp/admin/adminNav.jsp"/>
        </div>

    </nav>
    <article>
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
        <div class="table-responsive">
            <table class="table">
                <c:forEach var="review" items="${requestScope.reviewsOnModerationList}">
                    <tr>
                        <td>
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
                        </td>
                        <td>

                        </td>
                    </tr>

                </c:forEach>
            </table>
        </div>

    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<c:import url="/WEB-INF/jsp/table_control/tableScripts.jsp"/>
</body>
</html>
