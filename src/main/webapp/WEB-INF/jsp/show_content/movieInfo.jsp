<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.info.budget" var="budgetType"/>
<fmt:message bundle="${loc}" key="local.info.boxoffice" var="boxofficeType"/>
<fmt:message bundle="${loc}" key="local.info.mpaarating" var="mpaaratingType"/>

<jsp:useBean id="show" class="by.tr.web.domain.Movie" type="by.tr.web.domain.Movie" scope="request"/>
<tr>
    <td class="type">
        <c:out value="${boxofficeType}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="boxOffice"/>
    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${budgetType}"/>
    </td>
    <td>
        <jsp:getProperty name="show" property="budget"/>
    </td>
</tr>
<tr>
    <td class="type">
        <c:out value="${mpaaratingType}"/>
    </td>
    <td>
        <mpb:mpaa-rating mpaaRating="${show.mpaaRating}"/>
    </td>
</tr>