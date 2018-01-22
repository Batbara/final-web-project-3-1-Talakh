<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.info.year" var="yearType"/>
    <fmt:message bundle="${loc}" key="local.info.budget" var="budgetType"/>
    <fmt:message bundle="${loc}" key="local.info.boxoffice" var="boxofficeType"/>
    <fmt:message bundle="${loc}" key="local.info.mpaarating" var="mpaaratingType"/>
    <fmt:message bundle="${loc}" key="local.info.runtime" var="runtimeType"/>
    <fmt:message bundle="${loc}" key="local.info.genre" var="genreType"/>
    <fmt:message bundle="${loc}" key="local.info.country" var="countryType"/>
    <fmt:message bundle="${loc}" key="local.info.premiere" var="premiereType"/>
    <fmt:message bundle="${loc}" key="local.show.sidenav.rating" var="ratingNav"/>
    <fmt:message bundle="${loc}" key="local.show.sidenav.reviews" var="reviewsNav"/>
    <fmt:message bundle="${loc}" key="local.show.sidenav.synopsis" var="synopsisNav"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/layout.css">
    <link rel="stylesheet" type="text/css" href="/css/movie.css">
    <title><jsp:getProperty name = "movie" property = "title"/> | MotionPicture Bank [MPB]</title>
</head>
<body>
<c:import url="/WEB-INF/jsp/header.jsp"/>
<div class="main">
    <nav class="side-nav">
        <ul>
            <li>
                <a href="#rating" class = "nav_button">
                    <c:out value="${ratingNav}"/>
                </a>
            </li>
            <li>
                <a href="#synopsis" class = "nav_button">
                    <c:out value="${synopsisNav}"/>
                </a>
            </li>
            <li>
                <a href="#reviews" class = "nav_button">
                    <c:out value="${reviewsNav}"/>
                </a>
            </li>
        </ul>
    </nav>
    <article>

    </article>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="../js/content.js"></script>
</body>
</html>