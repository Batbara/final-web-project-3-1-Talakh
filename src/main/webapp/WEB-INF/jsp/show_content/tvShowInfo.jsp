<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.info.seasons.number" var="seasonsNumber"/>
<fmt:message bundle="${loc}" key="local.info.episodes.number" var="episodesNumber"/>
<fmt:message bundle="${loc}" key="local.info.tv.channel" var="tvChannel"/>
<jsp:useBean id="show" class="by.tr.web.domain.TvShow" type="by.tr.web.domain.TvShow" scope="request"/>
<tr>
    <td class="type">
        <c:out value="${seasonsNumber}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="seasonsNum"/>
    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${episodesNumber}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="episodesNum"/>
    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${tvChannel}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="channel"/>
    </td>
</tr>