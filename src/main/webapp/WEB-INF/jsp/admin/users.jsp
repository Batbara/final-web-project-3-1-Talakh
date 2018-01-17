<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.navButton.admin" var="title"/>
    <fmt:message bundle="${loc}" key="local.info.user.banReason" var="banReason"/>
    <fmt:message bundle="${loc}" key="local.info.user.ban" var="ban"/>
    <fmt:message bundle="${loc}" key="local.info.user.unban" var="unban"/>
    <fmt:message bundle="${loc}" key="local.info.user.unbanTime" var="unbanTime"/>
    <fmt:message bundle="${loc}" key="local.info.user.banTime" var="banTime"/>
    <fmt:message bundle="${loc}" key="local.movie.onPage" var="onPage"/>
    <fmt:message bundle="${loc}" key="local.admin.usertable.action" var="actionHeader"/>
    <fmt:message bundle="${loc}" key="local.admin.usertable.name" var="usernameHeader"/>
    <fmt:message bundle="${loc}" key="local.admin.usertable.status" var="statusHeader"/>
    <fmt:message bundle="${loc}" key="local.message.errorMessage" var="errorMessage"/>
    <fmt:message bundle="${loc}" key="local.message.banSuccessful" var="banSuccesful"/>
    <fmt:message bundle="${loc}" key="local.message.success" var="successMessage"/>
    <fmt:message bundle="${loc}" key="local.message.failure" var="failureMessage"/>
    <fmt:message bundle="${loc}" key="local.message.confirm" var="confirm"/>
    <fmt:message bundle="${loc}" key="local.message.unbanConfirmation" var="unbanConfirmation"/>
    <fmt:message bundle="${loc}" key="local.message.cancel" var="cancel"/>

    <title>${title} | MotionPicture Bank [MPB]</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/layout.css">
    <link rel="stylesheet" type="text/css" href="/css/table-style.css">
    <script src="../../../js/jquery-3.2.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</head>
<body>
<c:import url="/WEB-INF/jsp/header.jsp"/>
<div class="main">
    <c:import url="/WEB-INF/jsp/adminNav.jsp"/>
    <article>
        <div class="row">
            <div class="col-sm-12">
                <div class="onPage pull-left">
                    <form action="${pageContext.request.contextPath}/mpb" method="get" id="onPageForm"
                          onchange="submitPerPage();">
                        <input type="hidden" name="command" value="take_user_list">
                        <label for="onPageSelection"><c:out value="${onPage}"/></label>
                        <select name="onPage" id="onPageSelection">
                            <option value="25">25</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                        </select>
                        <input type="hidden" name="page" value="1">

                    </form>
                </div>
                <nav class="navigation pull-right">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.page eq i}">
                                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                                               value="${i}"></li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                                            <input type="hidden" name="command" value="take_user_list">
                                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                                            <input class="btn btn-default " type="submit" name="page" value="${i}">
                                        </form>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="users-table">
            <table class="table">
                <tr>
                    <th>id</th>
                    <th>${usernameHeader}</th>
                    <th>E-mail</th>
                    <th>${statusHeader}</th>
                    <th>${actionHeader}</th>
                </tr>
                <c:forEach items="${requestScope.userList}" var="currentUser">
                    <c:choose>
                        <c:when test="${currentUser.isBanned eq true}">

                            <tr class="bannedUser" data-toggle="collapse" data-target="#${currentUser.id}">
                                <td>
                                    <div class="userID ${currentUser.id}"><c:out value="${currentUser.id}"/></div>
                                </td>
                                <td class="userName ${currentUser.id}"><c:out value="${currentUser.userName}"/></td>
                                <td><c:out value="${currentUser.eMail}"/></td>
                                <td><c:out value="${currentUser.userStatus}"/></td>
                                <td>
                                <span  data-toggle="tooltip" data-placement="bottom" title="${unban}" >
                                     <a data-toggle="modal" data-target="#unbanDialog" class="unbanButton ${currentUser.id}" href="#">
                                         <span class="glyphicon glyphicon-ok"></span>
                                        </a>
                                </span>
                                    <span class="glyphicon glyphicon-trash"></span>
                                    <span class="glyphicon glyphicon-sunglasses"></span>
                                </td>

                            </tr>

                            <tr class="banContent collapse" id="${currentUser.id}">
                                <td colspan="5">
                                    <p>
                                            ${ban}: <c:out value="${currentUser.banInfo.banTime}"/> <br/>
                                            ${unban}: <c:out value="${currentUser.banInfo.unbanTime}"/> <br/>
                                            ${banReason}: <c:out value="${currentUser.banInfo.banReason.reason}"/>
                                    </p></td>
                            </tr>

                        </c:when>
                        <c:otherwise>

                            <tr>
                                <td>
                                    <div class="userID ${currentUser.id}"><c:out value="${currentUser.id}"/></div>
                                </td>
                                <td class="userName ${currentUser.id}"><c:out value="${currentUser.userName}"/></td>
                                <td><c:out value="${currentUser.eMail}"/></td>
                                <td><c:out value="${currentUser.userStatus}"/></td>
                                <td>
                                    <c:if test="${currentUser.userStatus ne 'ADMIN'}">
                                        <span  data-toggle="tooltip" data-placement="bottom" title="${ban}" >
                                        <a data-toggle="modal" data-target="#banDialog" class="banButton ${currentUser.id}" href="#">
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </a>
                                        </span>
                                        <span class="glyphicon glyphicon-trash"></span>
                                        <span class="glyphicon glyphicon-sunglasses"></span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </table>

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
                            <input type="hidden" name="userBanID" value="" id="userBanID">
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

                                <label class="control-label col-sm-4"
                                       for="unbanTime">${unbanTime}:</label>
                                <div class="col-sm-6">
                                    <input type="datetime-local" class="form-control" id="unbanTime" name="unbanTime">
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
                                    <button type="submit" class="btn btn-default">${ban}</button>
                                </div>
                            </div>
                        </form>
                        <div class="alert alert-success alert-dismissable" id="banSuccessAlert">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong>${successMessage}!</strong> ${banSuccesful}
                        </div>
                        <div class="alert alert-danger alert-dismissable" id="banFailureAlert">
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
                            <input type="hidden" name="userUnbanID" value="" id="userUnbanID">
                            <div class="alert alert-danger alert-dismissable" id="unbanFailureAlert">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>${failureMessage}!</strong> ${errorMessage}
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-7">
                                    <button type="submit" class="btn btn-default" data-dismiss="modal">${cancel}</button>
                                    <button type="submit" class="btn btn-primary">${unban}</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>

    </aside>
</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
<script src="../../../js/content.js"></script>
<script src="../../../js/table.js" async></script>
<script src="../../../js/toSubmit.js" async></script>
</body>
</html>
