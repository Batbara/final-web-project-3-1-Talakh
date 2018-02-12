<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

    <fmt:message bundle="${loc}" key="local.message.welcome" var="welcomeMessage"/>
    <fmt:message bundle="${loc}" key="local.message.site.info" var="siteInfo"/>

</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div>
            <div class="welcome-header">
                <h1>${welcomeMessage}</h1>
            </div>
            <div class="holder">
                <div class="site-info">

                    ${siteInfo}
                </div>
                <div class="image">
                    <img class="img-responsive" src="${pageContext.request.contextPath}/images/bb8.png">
                </div>
            </div>
        </div>
    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>
    </aside>

</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
</body>
</html>
