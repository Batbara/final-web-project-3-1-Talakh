<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.table.showType" var="typeHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.title" var="showTitleHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.year" var="yearHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.rating" var="ratingHeader"/>
<fmt:message bundle="${loc}" key="local.admin.user.table.action" var="actionHeader"/>
<fmt:message bundle="${loc}" key="local.admin.review.delete" var="deleteButton"/>
<fmt:message bundle="${loc}" key="local.admin.edit" var="editButton"/>
<c:set var="id" value="${requestScope.onPage*(requestScope.page-1)+1}" scope="page"/>
<table class="table center-text">
    <thead>
    <tr>
        <th>id</th>
        <th>${showTitleHeader}</th>
        <th>${yearHeader}</th>
        <th>${typeHeader}</th>
        <th>${ratingHeader}</th>
        <th>${actionHeader}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.shows}" var="currentShow">
        <tr class="center-text">
            <td class="show-id-cell">
                <div class="show-id ${currentShow.showId}"><b><c:out value="${currentShow.showId}"/></b></div>
            </td>
            <td class="show-title-cell">
                <c:if test="${currentShow.showType == 'MOVIE'}">
                    <a href="${pageContext.request.contextPath}/mpb?command=take_movie&showId=${currentShow.showId}"
                       class="show-title ${currentShow.showId}">
                        <c:out value="${currentShow.title}"/></a>
                </c:if>
                <c:if test="${currentShow.showType == 'TV_SERIES'}">
                    <a href="${pageContext.request.contextPath}/mpb?command=take_tv_show&showId=${currentShow.showId}"
                       class="show-title ${currentShow.showId}">
                        <c:out value="${currentShow.title}"/></a>
                </c:if>
            </td>

            <td class="year-cell"><c:out value="${currentShow.year}"/></td>
            <td class="show-type">
                <mpb:show-type showType="${currentShow.showType}"/>
                </td>
            <td class="rating-cell">
                <div class="rate ${currentShow.showId}"><mpb:show-rating show="${currentShow}"
                                                                          user="${sessionScope.user}"/></div>
            </td>
            <td>
                                <span data-toggle="tooltip" data-placement="bottom" title="${deleteButton}">
                                     <a data-toggle="modal" data-target="#deleteShowDialog"
                                        class="deleteShowButton ${currentShow.showId}" href="#">
                                         <span class="glyphicon glyphicon-trash" style="color: #50434d"></span>
                                        </a>
                                </span>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>