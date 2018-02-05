<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.show.table.header.poster" var="posterHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.title" var="showTitleHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.year" var="yearHeader"/>
<fmt:message bundle="${loc}" key="local.show.table.header.rating" var="ratingHeader"/>
<c:set var="id" value="${requestScope.onPage*(requestScope.page-1)+1}" scope="page"/>
<table class="table center-text">
    <thead>
    <tr>
        <th>&#8470;</th>
        <th>${posterHeader}</th>
        <th>${showTitleHeader}</th>
        <th>${yearHeader}</th>
        <th>${ratingHeader}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.movies}" var="currentMovie">
        <tr class="center-text">
            <td class="show-id-cell">
                <div class="show-id ${currentMovie.showID}"><b><c:out value="${id}"/></b></div>
                <c:set var="id" value="${id+1}"/>
            </td>
            <td class="poster-cell"><img src="${pageContext.request.contextPath}${currentMovie.poster}" class="img-thumbnail poster"></td>
            <td class="show-title-cell">
                <a href="${pageContext.request.contextPath}/mpb?command=take_movie&showId=${currentMovie.showID}">
                    <c:out value="${currentMovie.title}"/></a></td>
            <td class="year-cell"><c:out value="${currentMovie.year}"/></td>
            <td class="rating-cell">
                <div class="starrr ${currentMovie.showID}"></div>
                <div class="rate ${currentMovie.showID}"><mpb:show-rating show="${currentMovie}"
                                                                          user="${sessionScope.user}"/></div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>