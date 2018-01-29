<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.show.sidenav.rating" var="ratingNav"/>
<jsp:useBean id="show" class="by.tr.web.domain.Show" type="by.tr.web.domain.Show" scope="request"/>
<div class="rating">
    <h3 id="rating"><c:out value="${ratingNav}"/></h3>
    <div class="rating-content">

        <div class="starrr ${show.showID}"></div>
        <div class="rate ${show.showID}"><mpb:show-rating show="${show}"
                                                          user="${sessionScope.user}"/></div>
    </div>

</div>