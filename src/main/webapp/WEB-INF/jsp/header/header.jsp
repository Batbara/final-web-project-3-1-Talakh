<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<fmt:message bundle="${loc}" key="local.navButton.admin" var="administration"/>

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
                    <a href="javascript:{}"
                       onclick="$('#movies-form').submit(); return false;">
                        <span class="nav_button"><c:out value="${movies}"/></span>
                    </a>
                </form>
            </li>
            <li class="right_li">
                <form id="tvshow-form" action="${pageContext.request.contextPath}/mpb" method="get">
                    <input type="hidden" name="command" value="take_tv_list"/>
                    <a href="javascript:{}"
                       onclick="$('#tvshow-form').submit(); return false;">
                        <span class="nav_button"><c:out value="${tvShows}"/></span>
                    </a>
                </form>
            </li>
            <li>
                <div class="dropdown">
                    <img src="${pageContext.request.contextPath}/images/globe-icon.png" class="globe-icon">
                    <div class="dropdown-content">
                        <c:import url="/WEB-INF/jsp/header/changeLangForm.jsp">
                            <c:param name="local" value="ru"/>
                            <c:param name="lang" value="${rus}"/>
                        </c:import>
                        <c:import url="/WEB-INF/jsp/header/changeLangForm.jsp">
                            <c:param name="local" value="en"/>
                            <c:param name="lang" value="${eng}"/>
                        </c:import>
                    </div>
                </div>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <c:out value="${home}"/>
                </a>
            </li>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li>
                        <div class="dropdown">

                            <button class="profile-button" onclick="showProfileMenu(); return false">
                                <c:out value="${sessionScope.user.userName}"/></button>

                            <div class="profile-content" id="profileDropdown">
                                <ul class="profile-nav">
                                    <li>
                                        <form id="profile_form" action="${pageContext.request.contextPath}/mpb"
                                              method="get">
                                            <input type="hidden" name="command" value="take_account"/>
                                            <a href="javascript:{}"
                                               onclick="document.getElementById('profile_form').submit(); return false;">
                                                <c:out value="${profile}"/></a>
                                        </form>
                                    </li>
                                    <c:if test="${sessionScope.user.userStatus eq 'ADMIN'}">
                                        <li>

                                            <form id="administration_form"
                                                  action="${pageContext.request.contextPath}/mpb" method="get">
                                                <input type="hidden" name="command" value="take_user_list"/>
                                                <a href="javascript:{}"
                                                   onclick="document.getElementById('administration_form').submit(); return false;">
                                                    <c:out value="${administration}"/></a>
                                            </form>
                                        </li>
                                    </c:if>
                                    <li>
                                        <form id="logout_form" action="${pageContext.request.contextPath}/mpb"
                                              method="get">
                                            <input type="hidden" name="command" value="logout"/>

                                            <a href="javascript:{}"
                                               onclick="document.getElementById('logout_form').submit(); return false;">
                                                <c:out value="${logout}"/></a>
                                        </form>
                                    </li>
                                </ul>

                            </div>
                        </div>

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
