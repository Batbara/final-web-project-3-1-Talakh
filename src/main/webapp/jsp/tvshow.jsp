<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>

    <jsp:useBean id="show" class="by.tr.web.domain.TvShow" type="by.tr.web.domain.TvShow" scope="request"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tv-show.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/show.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/review.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/starrr.css">
    <title>
        <jsp:getProperty name="show" property="title"/>
        | MotionPicture Bank [MPB]</title>
</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<div class="main">
    <nav>
        <div data-spy="affix">
            <c:import url="/WEB-INF/jsp/show/showSideNav.jsp"/>
        </div>

    </nav>
    <article>
        <div class="fluid-container">

            <div class="fluid-container infoWrapper" id="info">

                <div class="col-md-4">
                <span>
                    <a class="title"
                       href="${pageContext.request.contextPath}/mpb?command=take_tv_show&showId=<jsp:getProperty name="show" property="showId"/>">
                       <mpb:tv-show-ref tvShow="${show}"/>
                    </a>
                </span>
                    <div class="posterBlock"><img src="${pageContext.request.contextPath}<jsp:getProperty name = "show" property = "poster"/>"
                                                  class="poster"></div>
                </div>

                <div class="infoTable">

                    <table class="table table-hover">
                        <c:import url="/WEB-INF/jsp/show/showBasicInfo.jsp"/>
                        <c:import url="/WEB-INF/jsp/tv_show/tvShowInfo.jsp"/>
                        <c:import url="/WEB-INF/jsp/show/showTimeInfo.jsp"/>
                    </table>
                </div>

            </div>

            <c:import url="/WEB-INF/jsp/show/showRating.jsp"/>
            <c:import url="/WEB-INF/jsp/show/showSynopsis.jsp"/>
            <c:import url="/WEB-INF/jsp/show/showReviews.jsp">
                <c:param name="command" value="take_tv_show"/>
            </c:import>
        </div>


    </article>
    <aside>

        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
<c:import url="/WEB-INF/jsp/show/ratingScripts.jsp"/>
<script src="${pageContext.request.contextPath}/js/content.js"></script>

<script src="${pageContext.request.contextPath}/js/review-submitter.js"></script>

</body>
</html>
