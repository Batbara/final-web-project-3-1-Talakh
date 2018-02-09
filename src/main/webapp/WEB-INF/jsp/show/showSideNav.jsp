<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.show.link.rating" var="ratingNav"/>
<fmt:message bundle="${loc}" key="local.show.link.reviews" var="reviewsNav"/>
<fmt:message bundle="${loc}" key="local.show.link.synopsis" var="synopsisNav"/>
<fmt:message bundle="${loc}" key="local.show.link.add.review" var="addReview"/>

<fmt:message bundle="${loc}" key="local.show.link.info" var="infoNav"/>
<ul class="nav nav-stacked" >
    <li>
        <a href="#info" class = "side-nav-button">
            <c:out value="${infoNav}"/>
        </a>
    </li>
    <li>
        <a href="#rating" class = "side-nav-button">
            <c:out value="${ratingNav}"/>
        </a>
    </li>
    <li>
        <a href="#synopsis" class = "side-nav-button">
            <c:out value="${synopsisNav}"/>
        </a>
    </li>
    <li>
        <a href="#reviews" class = "side-nav-button">
            <c:out value="${reviewsNav}"/>
        </a>
    </li>
    <li>
        <a href="#addReview" class = "side-nav-button">
            <c:out value="${addReview}"/>
        </a>
    </li>
</ul>