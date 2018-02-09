<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article></article>
    <aside></aside>

</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
</body>
</html>
