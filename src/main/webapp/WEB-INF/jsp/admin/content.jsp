<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.user.administration.page" var="administration"/>

    <fmt:message bundle="${loc}" key="local.admin.link.users" var="usersTitle"/>
    <fmt:message bundle="${loc}" key="local.message.delete.successful" var="deleteSuccessful"/>
    <fmt:message bundle="${loc}" key="local.info.user.select.status" var="selectStatusMessage"/>
    <fmt:message bundle="${loc}" key="local.table.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.response.message.error" var="errorMessage"/>
    <fmt:message bundle="${loc}" key="local.admin.review.delete" var="deleteMessage"/>
    <fmt:message bundle="${loc}" key="local.message.change.status.successful" var="changeStatusSuccess"/>
    <fmt:message bundle="${loc}" key="local.response.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.response.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.control.confirm" var="confirm"/>
    <fmt:message bundle="${loc}" key="local.message.delete.confirmation" var="deleteConfirmation"/>
    <fmt:message bundle="${loc}" key="local.control.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.control.change" var="change"/>

    <title>${administration} | ${usersTitle} | MotionPicture Bank [MPB]</title>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table-style.css">

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
        <div class="fluid-container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="onPage pull-left">
                        <c:import url="/WEB-INF/jsp/table_control/onPageForm.jsp">
                            <c:param name="command" value="take_show_list"/>
                        </c:import>
                    </div>
                    <nav class="navigation pull-right">

                        <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                            <c:param name="command" value="take_show_list"/>
                        </c:import>
                    </nav>
                </div>
            </div>
        </div>
        <div class="show-table center-block">
            <c:import url="/WEB-INF/jsp/show/showListTable.jsp"/>

        </div>
        <div class="fluid-container">
            <div class="row">
                <div class="col-sm-12">

                    <nav class="navigation pull-right">

                        <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                            <c:param name="command" value="take_show_list"/>
                        </c:import>
                    </nav>
                </div>
            </div>
        </div>
        <div class="modal fade" id="deleteShowDialog" role="dialog">
            <div class="modal-dialog modal-sm">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h3 class="modal-title">${confirm}</h3>
                    </div>
                    <div class="modal-body">
                        <div id="deleteShowFormWrapper">
                            <div class="col-sm-12">${deleteConfirmation} '<span id="deleteShowTitle"></span>'?</div>
                            <form class="form-horizontal" method="get" action="${pageContext.request.contextPath}/mpb"
                                  id="deleteShowForm">
                                <input type="hidden" name="command" value="delete_show">
                                <input type="hidden" name="showId" value="" id="showIdToDelete">

                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-md-10">
                                        <button type="submit" class="btn btn-default"
                                                data-dismiss="modal">${cancel}</button>
                                        <button type="submit" class="btn btn-primary"
                                                id="deleteShowButton">${deleteMessage}</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="alert alert-danger" id="deleteShowFailureAlert">
                            <strong>${failureMessage}!</strong> ${errorMessage}
                        </div>
                        <div class="alert alert-success" id="deleteShowSuccessAlert">
                            <strong>${successMessage}!</strong> ${deleteSuccessful}
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
<c:import url="/WEB-INF/jsp/table_control/tableScripts.jsp"/>
</body>
</html>
