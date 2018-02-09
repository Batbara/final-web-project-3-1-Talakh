<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.admin.add.tv.show" var="addTvShowTitle"/>
    <fmt:message bundle="${loc}" key="local.show.table.header.title" var="showTitle"/>

    <fmt:message bundle="${loc}" key="local.response.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.response.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.control.confirm" var="confirm"/>

    <fmt:message bundle="${loc}" key="local.show.table.header.poster" var="posterLabel"/>
    <fmt:message bundle="${loc}" key="local.question.unban.confirmation" var="unbanConfirmation"/>
    <fmt:message bundle="${loc}" key="local.control.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.admin.add" var="addButton"/>

    <fmt:message bundle="${loc}" key="local.lang.english.full" var="englishTitlePlaceholder"/>
    <fmt:message bundle="${loc}" key="local.lang.russian.full" var="russianTitlePlaceholder"/>

    <title>${addTvShowTitle} | MotionPicture Bank [MPB]</title>

</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<div class="main">
    <nav>
        <div data-spy="affix">
            <c:import url="/WEB-INF/jsp/admin/adminNav.jsp"/>
        </div>

    </nav>
    <article>
        <div class="add-show-form-wrapper">
            <form class="form-horizontal adding-form" accept-charset="UTF-8" enctype="multipart/form-data"
                  data-toggle="validator" role="form" id="addTvShowForm"
                  action="${pageContext.request.contextPath}/upload?command=add_tv_show" method="post">

                <c:import url="/WEB-INF/jsp/show/addShowForm.jsp"/>
                <c:import url="/WEB-INF/jsp/tv_show/tvShowFormGroup.jsp"/>
                <c:import url="/WEB-INF/jsp/show/posterFormGroup.jsp"/>
                <div class="form-group">
                    <div class="col-sm-offset-5 col-sm-1">
                        <button type="submit" class="btn btn-default" id="addButton">${addButton}</button>
                    </div>
                </div>
            </form>
        </div>
    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-data-loader.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

        $('#finishedYearFormGroup').hide();
    });
</script>
</body>
</html>
