<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.navButton.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.navButton.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.navButton.register" var="register"/>
    <fmt:message bundle="${loc}" key="local.navButton.home" var="home"/>
    <fmt:message bundle="${loc}" key="local.navButton.profile" var="profile"/>
    <fmt:message bundle="${loc}" key="local.navButton.movies" var="movies"/>
    <fmt:message bundle="${loc}" key="local.navButton.tv" var="tvShows"/>
    <fmt:message bundle="${loc}" key="local.navButton.rus" var="rus"/>
    <fmt:message bundle="${loc}" key="local.navButton.eng" var="eng"/>
</head>
<body>
<header>
    <nav class="top-navbar">
        <ul class="main-nav">

            <li>
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <img src="${pageContext.request.contextPath}/images/logo.png">
                </a>
            </li>

            <li>

                <form id="movies-form" action="${pageContext.request.contextPath}/mpb" method="get">
                    <input type="hidden" name="command" value="take_movie_list"/>
                    <input type="hidden"  name="order" value="title">
                    <input type="hidden" name="onPage" value="5">
                    <input type="hidden" name="page" value="1">
                    <a href="javascript:{}"
                       onclick="document.getElementById('movies-form').submit(); return false;">
                        <div class="nav_button"><c:out value="${movies}"/></div>
                    </a>

                </form>
            </li>
            <li class="right_li">
                <a href="${pageContext.request.contextPath}/jsp/tv.jsp">
                    <div class="nav_button"><c:out value="${tvShows}"/></div>
                </a>
            </li>
            <li>
                <div class="dropdown">
                    <img src="${pageContext.request.contextPath}/images/globe-icon.png" class="globe-icon">
                    <div class="dropdown-content">
                        <div class="lang_button">
                            <form action="${pageContext.request.contextPath}/mpb" method="post">
                                <input type="hidden" name="command" value="change_language"/>

                                <input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                                <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
                                <input type="hidden" name="local" value="ru"/>
                                <input id="ruButton" type="submit" value="${rus}"/>

                            </form>
                        </div>
                        <div class="lang_button">
                            <form action="${pageContext.request.contextPath}/mpb" method="post">
                                <input type="hidden" name="command" value="change_language"/>

                                <input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                                <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
                                <input type="hidden" name="local" value="en"/>
                                <input type="submit" id="enButton" value="${eng}"/>

                            </form>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <div class="nav_button"><c:out value="${home}"/></div>
                </a>
            </li>

            <c:choose>
            <c:when test="${not empty sessionScope.userStatus}">
            <li>
                <form id="profile_form" action="${pageContext.request.contextPath}/mpb" method="get">
                    <input type="hidden" name="command" value="take_account"/>

                    <a href="javascript:{}"
                       onclick="document.getElementById('profile_form').submit(); return false;">
                        <c:out value="${profile}"/></a>

                </form>
            </li>
            <li>
                <form id="logout_form" action="${pageContext.request.contextPath}/mpb" method="get">
                    <input type="hidden" name="command" value="logout"/>

                    <a href="javascript:{}"
                       onclick="document.getElementById('logout_form').submit(); return false;">
                        <c:out value="${logout}"/></a>
                </form>
            </li>

            </c:when>

            <c:otherwise>
            <li>
                <a href="${pageContext.request.contextPath}/register">
                    <c:out value="${register}"/>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/login">
                    <c:out value="${login}"/>
                </a>
            </li>
            </c:otherwise>
            </c:choose>


            </ul>

    </nav>
</header>
</body>