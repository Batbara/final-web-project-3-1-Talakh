<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.info.runtime" var="runtimeType"/>
<fmt:message bundle="${loc}" key="local.info.premiere" var="premiereType"/>

<jsp:useBean id="show" class="by.tr.web.domain.Show" type="by.tr.web.domain.Show" scope="request"/>
<tr>
    <td class="type">
        <c:out value="${premiereType}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="premiereDate"/>
    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${runtimeType}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="runtime"/>
    </td>
</tr>