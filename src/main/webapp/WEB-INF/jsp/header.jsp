<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <style>
        <jsp:include  page="/WEB-INF/css/stylesheet.css"/>
    </style>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    <fmt:message bundle="${loc}" key="local.navButton.login" var="login" />
    <fmt:message bundle="${loc}" key="local.navButton.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.navButton.register" var="register" />
    <fmt:message bundle="${loc}" key="local.navButton.home" var="home" />
    <fmt:message bundle="${loc}" key="local.navButton.profile" var="profile" />

</head>
<body>
<header>
    <div id="top_bar_black">
        <div id="logo_image"></div>
        <div id="nav_block">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <div class="nav_button"><c:out value="${home}" /></div>
            </a>
            <c:choose>
                <c:when test="${sessionScope.userStatus == 'USER' or sessionScope.userStatus == 'ADMIN'}">
                    <form action="${pageContext.request.contextPath}/mpb" method="get">
                        <input type="hidden" name="command" value="show_account"/>

                        <input class="nav_button" type="submit" value="<c:out value="${profile}" />"/>

                    </form>
                    <form action="${pageContext.request.contextPath}/mpb" method="get">
                        <input type="hidden" name="command" value="logout"/>

                        <input class="nav_button" type="submit" value="<c:out value="${logout}" />"/>

                    </form>

                </c:when>

                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/register">
                        <div class="nav_button"> <c:out value="${register}" /></div>
                    </a>
                    <a href="${pageContext.request.contextPath}/login">
                        <div class="nav_button"><c:out value="${login}" /></div>
                    </a>
                </c:otherwise>
            </c:choose>

            <div class="lang_button">
            <form action="${pageContext.request.contextPath}/mpb" method="post">
                <input type="hidden" name="command" value="change_language"/>

                <input type="hidden" name="local" value="ru"/>
                <input id="ruButton" type="submit"  value=""/>

            </form>
            <form action="${pageContext.request.contextPath}/mpb" method="post">
                <input type="hidden" name="command" value="change_language"/>

                <input type="hidden" name="local" value="en"/>
                <input  type="submit" id="enButton" value=""/>

            </form>
            </div>

        </div>
    </div>
</header>
</body>