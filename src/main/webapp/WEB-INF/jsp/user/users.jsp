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
    <fmt:message bundle="${loc}" key="local.info.user.banReason" var="banReason"/>
    <fmt:message bundle="${loc}" key="local.info.user.ban" var="ban"/>
    <fmt:message bundle="${loc}" key="local.info.user.unban" var="unban"/>
    <fmt:message bundle="${loc}" key="local.info.user.banTime" var="banTime"/>
    <fmt:message bundle="${loc}" key="local.info.user.select.status" var="selectStatusMessage"/>
    <fmt:message bundle="${loc}" key="local.table.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.response.message.error" var="errorMessage"/>
    <fmt:message bundle="${loc}" key="local.response.message.ban.success" var="banSuccesful"/>
    <fmt:message bundle="${loc}" key="local.message.change.status.successful" var="changeStatusSuccess"/>
    <fmt:message bundle="${loc}" key="local.response.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.response.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.control.confirm" var="confirm"/>
    <fmt:message bundle="${loc}" key="local.question.unban.confirmation" var="unbanConfirmation"/>
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
                            <c:param name="command" value="take_user_list"/>
                        </c:import>
                    </div>
                    <nav class="navigation pull-right">

                        <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                            <c:param name="command" value="take_user_list"/>
                        </c:import>
                    </nav>
                </div>
            </div>
        </div>
        <div class="table-responsive users-table">
            <c:import url="/WEB-INF/jsp/user/usersTable.jsp"/>

        </div>
        <div class="fluid-container">
            <div class="row">
                <div class="col-sm-12">

                    <nav class="navigation pull-right">

                        <c:import url="/WEB-INF/jsp/table_control/paging.jsp">
                            <c:param name="command" value="take_user_list"/>
                        </c:import>
                    </nav>
                </div>
            </div>
        </div>
        <div class="modal fade" id="banDialog" role="dialog">
            <div class="modal-dialog">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h5 class="modal-title" id="banUserTitle"></h5>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" method="get" action="${pageContext.request.contextPath}/mpb"
                              id="banForm">
                            <input type="hidden" name="command" value="ban_user">
                            <input type="hidden" name="userBanId" value="" id="userBanId">
                            <c:set var="banReasonList" value="${requestScope.banReasonList}" scope="session"/>
                            <c:set var="userList" value="${requestScope.userList}" scope="session"/>
                            <div class="form-group">
                                <label class="control-label col-sm-4"
                                       for="banTime">${banTime}:</label>
                                <div class="col-sm-6">
                                    <input type="datetime-local" class="form-control" id="banTime" name="banTime">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" for="sel1">${banReason}:</label>
                                <div class="col-xs-5">
                                    <select class="form-control" id="sel1" name="banReason">
                                        <c:forEach items="${requestScope.banReasonList}" var="currBanReason">
                                            <option>${currBanReason.reason}</option>
                                        </c:forEach>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-1">
                                    <button type="submit" class="btn btn-default" id="banButton">${ban}</button>
                                </div>
                            </div>
                        </form>
                        <div class="alert alert-success" id="banSuccessAlert">
                            <strong>${successMessage}!</strong> ${banSuccesful}
                        </div>
                        <div class="alert alert-danger" id="banFailureAlert">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong>${failureMessage}!</strong> ${errorMessage}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="unbanDialog" role="dialog">
            <div class="modal-dialog modal-sm">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h3 class="modal-title">${confirm}</h3>
                    </div>
                    <div class="modal-body">
                        <div class="col-sm-12">${unbanConfirmation} <span id="unbanUserName"></span>?</div>
                        <form class="form-horizontal" method="get" action="${pageContext.request.contextPath}/mpb"
                              id="unbanForm">
                            <input type="hidden" name="command" value="unban_user">
                            <input type="hidden" name="userUnbanId" value="" id="userUnbanId">
                            <div class="alert alert-danger alert-dismissable" id="unbanFailureAlert">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>${failureMessage}!</strong> ${errorMessage}
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-md-10">
                                    <button type="submit" class="btn btn-default"
                                            data-dismiss="modal">${cancel}</button>
                                    <button type="submit" class="btn btn-primary">${unban}</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="changeStatusDialog" role="dialog">
            <div class="modal-dialog">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h5 class="modal-title" id="changeUserStatusTitle"></h5>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" method="get" action="${pageContext.request.contextPath}/mpb"
                              id="changeStatusForm">
                            <input type="hidden" name="command" value="change_user_status">
                            <input type="hidden" name="userChangeStatusId" value="" id="userChangeStatusId">

                            <c:set var="banReasonList" value="${requestScope.banReasonList}" scope="session"/>
                            <c:set var="userList" value="${requestScope.userList}" scope="session"/>
                            <c:set var="userStatusList" value="${requestScope.userStatusList}" scope="session"/>


                            <div class="form-group">
                                <label class="control-label col-sm-4"
                                       for="statusSelection">${selectStatusMessage}:</label>
                                <div class="col-xs-5">
                                    <select class="form-control" id="statusSelection" name="newUserStatus">
                                        <c:forEach items="${userStatusList}" var="currUserStatus">
                                            <option value="<c:out value="${currUserStatus}"/>">
                                                <mpb:user-status userStatus="${currUserStatus}"/></option>
                                        </c:forEach>

                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-1">
                                    <button type="submit" class="btn btn-primary">${change}</button>
                                </div>
                            </div>
                        </form>
                        <div class="alert alert-success" id="statusChangeSuccessAlert">
                            <strong>${successMessage}!</strong> ${changeStatusSuccess}
                        </div>
                        <div class="alert alert-danger" id="statusChangeFailureAlert">
                            <strong>${failureMessage}!</strong> ${errorMessage}
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
