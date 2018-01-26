<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <c:import url="/WEB-INF/jsp/styling.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/starrr.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table-style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.navButton.movies" var="pageTitle"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> ${pageTitle} | MotionPicture Bank [MPB]</title>
</head>
<body>
<c:import url="/WEB-INF/jsp/header/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="fluid-container control">

            <div class="row">
                <div class="col-md-4">
                    <c:import url="/WEB-INF/jsp/table_control/orderForm.jsp">
                        <c:param name="command" value="take_movie_list"/>
                    </c:import>
                </div>
                <div class="col-sm-4">
                    <c:import url="/WEB-INF/jsp/table_control/onPageForm.jsp">
                        <c:param name="command" value="take_movie_list"/>
                    </c:import>
                </div>

                <div class="col-md-4">
                    <nav class="navigation pull-right">
                        <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                            <c:param name="command" value="take_movie_list"/>
                        </c:import>
                    </nav>
                </div>
            </div>
        </div>

        <div class="movie-table center-block">

            <c:import url="/WEB-INF/jsp/table_content/movieListTable.jsp"/>

        </div>
        <c:import url="/WEB-INF/jsp/table_content/ratingDialogs.jsp"/>
    </article>
    <aside>

        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<c:import url="/WEB-INF/jsp/table_control/tableScripts.jsp"/>
<c:import url="/WEB-INF/jsp/table_content/ratingScripts.jsp"/>
</body>
</html>
