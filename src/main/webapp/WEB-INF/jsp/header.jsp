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
    <fmt:message bundle="${loc}" key="local.navButton.movies" var="movies" />
    <fmt:message bundle="${loc}" key="local.navButton.tv" var="tvShows" />
</head>
<body>
<header>
    <div id="top_bar_black">
        <div class="logo_image">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/images/icon.png">
            </a>
        </div>
        <div class="movies_nav">
            <ul>
                <li class="right_li">
                    <a href="${pageContext.request.contextPath}/jsp/movies.jsp">
                        <div class="nav_button"><c:out value="${movies}" /></div>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/jsp/tv.jsp">
                        <div class="nav_button"><c:out value="${tvShows}" /></div>
                    </a>
                </li>
            </ul>
        </div>
        <div class="left_nav">
            <div class="lang_buttons">
                <div class="lang_button">
                    <form action="${pageContext.request.contextPath}/mpb" method="post">
                        <input type="hidden" name="command" value="change_language"/>

                        <input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                        <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
                        <input type="hidden" name="local" value="ru"/>
                        <input id="ruButton" type="submit"  value=""/>

                    </form>
                </div>
                <div class="lang_button">
                    <form action="${pageContext.request.contextPath}/mpb" method="post">
                        <input type="hidden" name="command" value="change_language"/>

                        <input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
                        <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
                        <input type="hidden" name="local" value="en"/>
                        <input  type="submit" id="enButton" value=""/>

                    </form>
                </div>
            </div>
        <div class="nav_block">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <div class="nav_button"><c:out value="${home}" /></div>
                    </a>
                </li>
                <c:choose>
                    <c:when test="${not empty sessionScope.userStatus}">
                       <li> <form id="profile_form" action="${pageContext.request.contextPath}/mpb" method="get">
                            <input type="hidden" name="command" value="take_account"/>

                           <a href="javascript:{}" onclick="document.getElementById('profile_form').submit(); return false;">
                               <c:out value="${profile}" /></a>

                       </form></li>
                       <li> <form id="logout_form" action="${pageContext.request.contextPath}/mpb" method="get">
                            <input type="hidden" name="command" value="logout"/>

                           <a href="javascript:{}" onclick="document.getElementById('logout_form').submit(); return false;">
                               <c:out value="${logout}" /></a>
                        </form></li>

                    </c:when>

                    <c:otherwise>
                        <li>
                        <a href="${pageContext.request.contextPath}/register">
                             <c:out value="${register}" />
                        </a>
                        </li>
                        <li>
                        <a href="${pageContext.request.contextPath}/login">
                            <c:out value="${login}" />
                        </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>


        </div>
    </div>
</header>
</body>