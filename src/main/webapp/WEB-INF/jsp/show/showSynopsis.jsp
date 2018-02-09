<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.show.link.synopsis" var="synopsisNav"/>
<jsp:useBean id="show" class="by.tr.web.domain.Show" type="by.tr.web.domain.Show" scope="request"/>
<div class="synopsis">
    <h3 id="synopsis"><c:out value="${synopsisNav}"/></h3>
    <div class="synopsis-content">
        <jsp:getProperty name="show" property="synopsis"/>
    </div>

</div>