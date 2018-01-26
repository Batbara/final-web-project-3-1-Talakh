<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
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
    <c:import url="/WEB-INF/jsp/styling.jsp"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movie.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/starrr.css">
    <title>
        <jsp:getProperty name="movie" property="title"/>
        | MotionPicture Bank [MPB]</title>
</head>
<body>
<c:import url="/WEB-INF/jsp/header/header.jsp"/>
<div class="main">
    <nav>
        <div data-spy="affix">
            <c:import url="/WEB-INF/jsp/show_content/showSideNav.jsp"/>
        </div>

    </nav>
    <article>
        <jsp:useBean id="movie" class="by.tr.web.domain.Movie" type="by.tr.web.domain.Movie" scope="request"/>
        <div class="fluid-container">

            <div class="fluid-container infoWrapper">


                <div class="col-md-4">
                <span>
                    <a class="title"
                       href="${pageContext.request.contextPath}/mpb?command=take_movie&id=<jsp:getProperty name="movie" property="showID"/>">
                        <jsp:getProperty name="movie" property="title"/> (<jsp:getProperty name="movie" property="year"/>)</a>
                </span>
                    <div class="posterBlock"><img src="/images<jsp:getProperty name = "movie" property = "poster"/>.jpg"
                                                  class="poster"></div>
                </div>

                <div class="infoTable">

                    <table class="table table-hover">
                        <tr>
                            <td class="type">
                                <c:out value="${yearType}"/>
                            </td>
                            <td>
                                <jsp:getProperty name="movie" property="year"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${countryType}"/>
                            </td>
                            <td>
                                <c:out value="${fn:join(movie.countries, ', ')}"/>

                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${genreType}"/>
                            </td>
                            <td>
                                <c:out value="${fn:join(movie.genres, ', ')}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${boxofficeType}"/>
                            </td>
                            <td>
                                <jsp:getProperty name="movie" property="boxOffice"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${budgetType}"/>
                            </td>
                            <td>
                                <jsp:getProperty name="movie" property="budget"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${mpaaratingType}"/>
                            </td>
                            <td>
                                <mpb:mpaa-rating mpaaRating="${movie.mpaaRating}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${premiereType}"/>
                            </td>
                            <td>
                                <jsp:getProperty name="movie" property="premiereDate"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${runtimeType}"/>
                            </td>
                            <td>
                                <jsp:getProperty name="movie" property="runtime"/>
                            </td>
                        </tr>
                    </table>
                </div>

            </div>


            <div class="rating">
                <h3 id="rating"><c:out value="${ratingNav}"/></h3>
                <div class="rating-content">

                    <div class="starrr ${movie.showID}"></div>
                    <div class="rate ${movie.showID}"><mpb:show-rating show="${movie}"
                                                                       user="${sessionScope.user}"/></div>
                </div>

            </div>
            <div class="synopsis">
                <h3 id="synopsis"><c:out value="${synopsisNav}"/></h3>
                <div class="synopsis-content">
                    <jsp:getProperty name="movie" property="synopsis"/>
                </div>

            </div>
            <div class="reviews">
                <h3 id="reviews"><c:out value="${reviewsNav}"/></h3>
                <c:forEach var="review" items="${movie.reviewList}">
                    <c:if test="${review.reviewContent != null}">
                        <div class="review">
                            <div class="author">
                                <c:out value="${review.user.userName}"/>
                            </div>
                            <div class="postDate">
                                <c:out value="${review.postDate}"/>
                            </div>
                            <div class="reviewContent">
                                <p>
                                    <c:out value="${review.reviewContent}"/>
                                </p>
                            </div>

                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="addReview">
            <form>
            </form>
        </div>

    </article>
    <aside>

        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<c:import url="/WEB-INF/jsp/table_content/ratingScripts.jsp"/>
<script src="${pageContext.request.contextPath}/js/content.js"></script>

</body>
</html>
