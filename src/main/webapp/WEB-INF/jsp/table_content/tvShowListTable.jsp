<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="id" value="${requestScope.onPage*(requestScope.page-1)+1}" scope="page"/>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.show.table.header.poster" var="posterHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.title" var="showTitleHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.year" var="yearHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.season" var="seasonHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.rating" var="ratingHeader"/>
<table class="table center-text">
    <thead>
    <tr>
        <th>&#8470;</th>
        <th>${posterHeader}</th>
        <th>${showTitleHeader}</th>
        <th>${yearHeader}</th>
        <th>${seasonHeader}</th>
        <th>${ratingHeader}</th>
    </tr>
    </thead>
<c:forEach items="${requestScope.tvShows}" var="currentTvShow">
    <tr class="center-text">
        <td class="show-id-cell">
            <div class="show-id ${currentTvShow.showID}"><b><c:out value="${id}"/></b></div>
            <c:set var="id" value="${id+1}"/>
        </td>
        <td class="poster-cell"><img src="/images${currentTvShow.poster}.jpg" class="img-thumbnail poster"></td>
        <td class="show-title-cell">
            <a href="${pageContext.request.contextPath}/mpb?command=take_tv_show&showId=${currentTvShow.showID}">
                <c:out value="${currentTvShow.title}"/></a>
            <mpb:tvShow-status status="${currentTvShow.showStatus}"/>
        </td>
        <td class="year-cell"><c:out value="${currentTvShow.year}"/></td>
        <td class="season"><c:out value="${currentTvShow.seasonsNum}"/></td>
        <td class="rating-cell">
            <div class="starrr ${currentTvShow.showID}"></div>
            <div class="rate ${currentTvShow.showID}"><mpb:show-rating show="${currentTvShow}"
                                                                      user="${sessionScope.user}"/></div>
        </td>
    </tr>
</c:forEach>
</table>