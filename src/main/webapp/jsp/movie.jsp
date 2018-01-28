<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/WEB-INF/jsp/styling.jsp"/>

    <jsp:useBean id="show" class="by.tr.web.domain.Movie" type="by.tr.web.domain.Movie" scope="request"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movie.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/starrr.css">
    <title>
        <jsp:getProperty name="show" property="title"/>
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
        <div class="fluid-container">

            <div class="fluid-container infoWrapper">


                <div class="col-md-4">
                <span>
                    <a class="title"
                       href="${pageContext.request.contextPath}/mpb?command=take_movie&showId=<jsp:getProperty name="show" property="showID"/>&reviewStatus=posted">
                        <jsp:getProperty name="show" property="title"/> (<jsp:getProperty name="show"
                                                                                          property="year"/>)</a>
                </span>
                    <div class="posterBlock"><img src="/images<jsp:getProperty name = "show" property = "poster"/>.jpg"
                                                  class="poster"></div>
                </div>

                <div class="infoTable">

                    <table class="table table-hover">
                        <c:import url="/WEB-INF/jsp/show_content/showBasicInfo.jsp"/>
                        <c:import url="/WEB-INF/jsp/show_content/movieInfo.jsp"/>
                        <c:import url="/WEB-INF/jsp/show_content/showTimeInfo.jsp"/>
                    </table>
                </div>

            </div>

            <c:import url="/WEB-INF/jsp/show_content/showRating.jsp"/>
            <c:import url="/WEB-INF/jsp/show_content/showSynopsis.jsp"/>
            <c:import url="/WEB-INF/jsp/show_content/showReviews.jsp">
                <c:param name="command" value="take_movie"/>
            </c:import>
        </div>


    </article>
    <aside>

        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<c:import url="/WEB-INF/jsp/table_content/ratingScripts.jsp"/>
<script src="${pageContext.request.contextPath}/js/content.js"></script>

<script src="${pageContext.request.contextPath}/js/review-submitter.js"></script>

</body>
</html>
