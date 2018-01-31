<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.navButton.admin" var="title"/>
<fmt:message bundle="${loc}" key="local.info.user.ban" var="ban"/>
<fmt:message bundle="${loc}" key="local.info.user.unban" var="unban"/>
<fmt:message bundle="${loc}" key="local.admin.usertable.action" var="actionHeader"/>
<fmt:message bundle="${loc}" key="local.admin.usertable.name" var="usernameHeader"/>
<fmt:message bundle="${loc}" key="local.admin.usertable.status" var="statusHeader"/>
<fmt:message bundle="${loc}" key="local.info.user.change.status" var="changeStatusMessage"/>

<table class="table">
    <thead>
    <tr>
        <th>id</th>
        <th>${usernameHeader}</th>
        <th>E-mail</th>
        <th>${statusHeader}</th>
        <th>${actionHeader}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.userList}" var="currentUser">
        <c:choose>
            <c:when test="${currentUser.isBanned eq true}">

                <tr class="bannedUser" data-toggle="collapse" data-target="#${currentUser.id}">
                    <td>
                        <div class="userID ${currentUser.id}"><c:out value="${currentUser.id}"/></div>
                    </td>
                    <td class="userName ${currentUser.id}"><c:out value="${currentUser.userName}"/></td>
                    <td><c:out value="${currentUser.email}"/></td>
                    <td> <mpb:user-status userStatus="${currentUser.userStatus}"/></td>
                    <td>
                                <span data-toggle="tooltip" data-placement="bottom" title="${unban}">
                                     <a data-toggle="modal" data-target="#unbanDialog"
                                        class="unbanButton ${currentUser.id}" href="#">
                                         <span class="glyphicon glyphicon-ok"></span>
                                        </a>
                                </span>
                        <span class="glyphicon glyphicon-trash"></span>
                        <span data-toggle="tooltip" data-placement="bottom" title="${changeStatusMessage}">
                                     <a data-toggle="modal" data-target="#changeStatusDialog"
                                        class="changeStatusButton ${currentUser.id}" href="#">
                                         <span class="glyphicon glyphicon-sunglasses"></span>
                                        </a>
                                </span>

                    </td>

                </tr>

                <tr class="banContent collapse" id="${currentUser.id}">
                    <td colspan="5">
                        <mpb:ban-info user="${currentUser}"/>
                    </td>
                </tr>

            </c:when>
            <c:otherwise>

                <tr>
                    <td>
                        <div class="userID ${currentUser.id}"><c:out value="${currentUser.id}"/></div>
                    </td>
                    <td class="userName ${currentUser.id}"><c:out value="${currentUser.userName}"/></td>
                    <td><c:out value="${currentUser.email}"/></td>
                    <td><mpb:user-status userStatus="${currentUser.userStatus}"/></td>
                    <td>
                        <c:if test="${currentUser.userStatus ne 'ADMIN'}">
                                        <span data-toggle="tooltip" data-placement="bottom" title="${ban}">
                                        <a data-toggle="modal" data-target="#banDialog"
                                           class="banButton ${currentUser.id}" href="#">
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </a>
                                        </span>
                            <span class="glyphicon glyphicon-trash"></span>
                            <span data-toggle="tooltip" data-placement="bottom" title="${changeStatusMessage}">
                                     <a data-toggle="modal" data-target="#changeStatusDialog"
                                        class="changeStatusButton ${currentUser.id}" href="#">
                                         <span class="glyphicon glyphicon-sunglasses"></span>
                                        </a>
                                </span>
                        </c:if>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
</table>
