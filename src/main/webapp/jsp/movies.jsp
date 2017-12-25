<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/content.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table-style.css">

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/toSubmit.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.movie.order.by" var="orderBy"/>
    <fmt:message bundle="${loc}" key="local.movie.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.movie.order.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.movie.order.year" var="year"/>
    <fmt:message bundle="${loc}" key="local.movie.order.rating" var="rating"/>

    <title>Movie</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="order">
            <form action="${pageContext.request.contextPath}/mpb" method="get" id="orderForm"
                  onchange="submitMoviesOrder()">
                <input type="hidden" name="command" value="take_movie_list">
                <label for="orderSelection"><c:out value="${orderBy}"/></label>
                <select name="order" id="orderSelection">
                    <option value="title"><c:out value="${title}"/></option>
                    <option value="year"><c:out value="${year}"/></option>
                    <option value="rating"><c:out value="${rating}"/></option>
                </select>
                <input type="hidden" name="onPage" value="${requestScope.onPage}">
                <input type="hidden" name="page" value="${requestScope.page}">

            </form>
        </div>
        <div class="onPage">
            <form action="${pageContext.request.contextPath}/mpb" method="get" id="onPageForm"
                  onchange="submitMoviesPerPage();">
                <input type="hidden" name="command" value="take_movie_list">
                <input type="hidden" name="order" value="${requestScope.order}">
                <label for="onPageSelection"><c:out value="${onPage}"/></label>
                <select name = "onPage" id="onPageSelection">
                    <option value="5">5</option>
                    <option value="15">15</option>
                    <option value="25">25</option>
                </select>
                <input type="hidden" name="page" value="${requestScope.page}">

            </form>
        </div>
        <nav class="navigation">
            <ul>
                <c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.currentPage eq i}">
                            <li><input class="currButton" type="submit" name="page" value="${i}"></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <form method="get" action="${pageContext.request.contextPath}/mpb">
                                    <input type="hidden" name="command" value="take_movie_list">
                                    <input type="hidden" name="order" value="${requestScope.order}">
                                    <input type="hidden" name="onPage" value="${requestScope.onPage}">
                                    <input class="pageButton" type="submit" name="page" value="${i}">
                                </form>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
        <div class="movie-table">
            <table cellspacing="0">
                <c:forEach items="${requestScope.movies}" var="currentMovie">
                    <tr>
                        <td><img src="/images${currentMovie.poster}.jpg" class="poster"></td>
                        <td><c:out value="${currentMovie.title}"/></td>
                        <td><c:out value="${currentMovie.year}"/></td>
                        <td class="rate">
                            <div><img src="${pageContext.request.contextPath}/images/star.png" class="star"></div>
                            <div><c:out value="${currentMovie.userRating}"/></div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </article>
    <aside></aside>
</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
