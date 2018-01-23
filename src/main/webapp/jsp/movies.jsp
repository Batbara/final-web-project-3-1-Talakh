<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link href="${pageContext.request.contextPath}//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/starrr.css">

    <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layout.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.movie.order.by" var="orderBy"/>
    <fmt:message bundle="${loc}" key="local.movie.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.movie.order.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.movie.order.year" var="year"/>
    <fmt:message bundle="${loc}" key="local.movie.order.rating" var="rating"/>
    <fmt:message bundle="${loc}" key="local.navButton.movies" var="pageTitle"/>
    <fmt:message bundle="${loc}" key="local.message.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.message.rateApproved" var="rateApprovedMessage"/>
    <fmt:message bundle="${loc}" key="local.message.oops" var="oopsMessage"/>
    <fmt:message bundle="${loc}" key="local.message.rateDenied" var="rateDeniedMessage"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> ${pageTitle} | MotionPicture Bank [MPB]</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="fluid-container control">

            <div class="row">
                <div class="col-sm-4">
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
                            <input type="hidden" name="page" value="1">

                        </form>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="onPage">
                        <form action="${pageContext.request.contextPath}/mpb" method="get" id="onPageForm"
                              onchange="submitPerPage();">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <label for="onPageSelection"><c:out value="${onPage}"/></label>
                            <select name="onPage" id="onPageSelection">
                                <option value="5">5</option>
                                <option value="15">15</option>
                                <option value="25">25</option>
                            </select>
                            <input type="hidden" name="page" value="1">

                        </form>
                    </div>
                </div>

                <div class="col-sm-4">
                    <nav class="navigation pull-right">
                        <c:import url="/WEB-INF/jsp/paging/paging.jsp"/>
                    </nav>
                </div>
            </div>
        </div>

        <div class="movie-table center-block">
            <table class="table center-text">
                <c:set var="id" value="${requestScope.onPage*(requestScope.page-1)+1}" scope="page"/>
                <c:forEach items="${requestScope.movies}" var="currentMovie">
                    <tr class="center-text">
                        <td class="movieID">
                            <div class="movieID ${currentMovie.showID}"><b><c:out value="${id}"/></b></div>
                            <c:set var="id" value="${id+1}"/>
                        </td>
                        <td><img src="/images${currentMovie.poster}.jpg" class="img-thumbnail poster"></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/mpb?command=take_movie&id=${currentMovie.showID}"><c:out
                                    value="${currentMovie.title}"/></a></td>
                        <td><c:out value="${currentMovie.year}"/></td>
                        <td>
                            <div class="starrr ${currentMovie.showID}"></div>
                            <div class="rate ${currentMovie.showID}"><mpb:show-rating show="${currentMovie}"
                                                                                      user="${sessionScope.user}"/></div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="modal fade" id="ratingSetSuccessful" role="dialog">
            <div class="modal-dialog modal-md">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h3 class="modal-title">${successMessage}</h3>
                    </div>
                    <div class="modal-body">

                                ${rateApprovedMessage}

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="ratingSetFailed" role="dialog">
            <div class="modal-dialog modal-md">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h3 class="modal-title">${oopsMessage}</h3>
                    </div>
                    <div class="modal-body">
                        <div class="fluid-container">


                                ${rateDeniedMessage}

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">OK</button>
                    </div>
                </div>
            </div>
        </div>
    </article>
    <aside>

        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/toSubmit.js"></script>
<script src="${pageContext.request.contextPath}/js/cookieHandler.js"></script>

<script src="${pageContext.request.contextPath}/js/starrr.js"></script>
<script src="${pageContext.request.contextPath}/js/table.js"></script>
<script src="${pageContext.request.contextPath}/js/content.js"></script>
<script>
    $(document).ready(function(){
        $('a.fa').tooltip();
    });
</script>
</body>
</html>
