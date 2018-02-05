<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <c:import url="/WEB-INF/jsp/styling.jsp"/>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.admin.add.movie" var="addMovieTitle"/>
    <fmt:message bundle="${loc}" key="local.show.table.header.title" var="showTitle"/>


    <fmt:message bundle="${loc}" key="local.message.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.message.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.message.confirm" var="confirm"/>

    <fmt:message bundle="${loc}" key="local.show.table.header.poster" var="posterLabel"/>
    <fmt:message bundle="${loc}" key="local.message.unbanConfirmation" var="unbanConfirmation"/>
    <fmt:message bundle="${loc}" key="local.message.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.message.change" var="change"/>

    <fmt:message bundle="${loc}" key="local.info.english" var="englishTitlePlaceholder"/>
    <fmt:message bundle="${loc}" key="local.info.russian" var="russianTitlePlaceholder"/>

    <title>${addMovieTitle} | MotionPicture Bank [MPB]</title>

</head>
<body>
<c:import url="/WEB-INF/jsp/header/header.jsp"/>
<div class="main">
    <nav>
        <div data-spy="affix">
            <c:import url="/WEB-INF/jsp/admin/adminNav.jsp"/>
        </div>

    </nav>
    <article>
        <div class="add-show-form-wrapper">
        <form class="form-horizontal" accept-charset="UTF-8" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/upload?command=add_movie" method="post" id="addMovieForm" >

            <c:import url="/WEB-INF/jsp/admin/addShowForm.jsp"/>
            <c:import url="/WEB-INF/jsp/admin/movieInfoForm.jsp"/>
            <label for="poster_en" class="control-label">${posterLabel}</label>
            <input type="file" id="poster_en" name="poster_en" required placeholder="${englishTitlePlaceholder}"/>
            <input type="file" id="poster_ru" name="poster_ru" placeholder="${russianTitlePlaceholder}" required/>
            <div class="form-group">
                <div class="col-sm-offset-5 col-sm-1">
                    <button type="submit" class="btn btn-default">send</button>
                </div>
            </div>
        </form>
        </div>
    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-data-loader.js"></script>

</body>
</html>
