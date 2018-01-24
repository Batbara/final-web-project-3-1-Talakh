<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.table.onPage" var="onPage"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="onPage">
    <c:set var="splitedCommand" value="${fn:split(param.command, '_')}"/>
    <c:set var="onPageId" value="${splitedCommand[1]}OnPageSelection"/>
    <form action="${pageContext.request.contextPath}/mpb" method="get" id="onPageForm"
          onchange="submitPerPage();">
        <input type="hidden" name="command" value="${param.command}">
        <input type="hidden" name="order" value="${requestScope.order}">

        <c:choose>
            <c:when test="${param.command == 'take_user_list'}">
                <label for="onUsersPageSelection">${onPage}</label>
                <select name="onPage" id="onUsersPageSelection">
                    <option value="25">25</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                </select>
            </c:when>
            <c:otherwise>
                <label for="${onPageId}">${onPage}</label>
                <select name="onPage" id="${onPageId}">
                    <option value="5">5</option>
                    <option value="15">15</option>
                    <option value="25">25</option>
                </select>
            </c:otherwise>
        </c:choose>
        <input type="hidden" name="page" value="1">
    </form>
</div>