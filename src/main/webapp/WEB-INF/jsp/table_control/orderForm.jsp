<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.table.order.by" var="orderBy"/>
<fmt:message bundle="${loc}" key="local.table.order.title" var="title"/>
<fmt:message bundle="${loc}" key="local.table.order.year" var="year"/>
<fmt:message bundle="${loc}" key="local.table.order.rating" var="rating"/>
<fmt:message bundle="${loc}" key="local.table.order.seasons" var="seasons"/>
<div class="order">
    <c:set var="splitedCommand" value="${fn:split(param.command, '_')}"/>
    <c:set var="orderId" value="${splitedCommand[1]}OrderSelection"/>
    <form action="${pageContext.request.contextPath}/mpb" method="get" id="orderForm"
          onchange="submitShowsListOrder()">
        <input type="hidden" name="command" value="<c:out value="${param.command}"/>">
        <label for="${orderId}"><c:out value="${orderBy}"/></label>

        <select name="order" id="${orderId}">
            <option value="title"><c:out value="${title}"/></option>
            <option value="year"><c:out value="${year}"/></option>
            <option value="rating"><c:out value="${rating}"/></option>
            <c:if test="${param.command == 'take_tv_list'}">
                <option value="seasons"><c:out value="${seasons}"/></option>
            </c:if>
        </select>
        <input type="hidden" name="onPage" value="${requestScope.onPage}">
        <input type="hidden" name="page" value="1">

    </form>
</div>