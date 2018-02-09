<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.info.genre" var="genreType"/>
<fmt:message bundle="${loc}" key="local.info.country" var="countryType"/>
<fmt:message bundle="${loc}" key="local.info.year" var="yearType"/>
<jsp:useBean id="show" class="by.tr.web.domain.Show" type="by.tr.web.domain.Show" scope="request"/>
<tr>
    <td class="type">
        <c:out value="${yearType}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="year"/>
    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${countryType}"/>
    </td>
    <td>
        <c:out value="${fn:join(show.countries, ', ')}"/>

    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${genreType}"/>
    </td>
    <td>
        <c:out value="${fn:join(show.genres, ', ')}"/>
    </td>
</tr>