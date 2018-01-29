<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/WEB-INF/jsp/styling.jsp"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user-form.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message.signup" var="signupMessage"/>
    <fmt:message bundle="${loc}" key="local.submit.signup" var="signupButton"/>
    <fmt:message bundle="${loc}" key="local.error.register.user" var="regUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="regLoginError"/>
    <fmt:message bundle="${loc}" key="local.error.register.email" var="regEmailError"/>
    <fmt:message bundle="${loc}" key="local.error.register.password" var="regPasswordError"/>
    <fmt:message bundle="${loc}" key="local.error.email.empty" var="emailEmpty"/>

    <title><c:out value="${signupButton}"/> | MotionPicture Bank [MPB]</title>
</head>
<body>
<c:import url="/WEB-INF/jsp/header/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="container-fluid">
            <div class="row-fluid">

                <div class="col-md-offset-4 col-md-4" id="box">
                    <h2>${signupMessage}</h2>
                    <hr>
                    <form class="form-horizontal" action="${pageContext.request.contextPath}/mpb" method="post"
                          id="contact_form" data-toggle="validator" role="form">
                        <fieldset>
                            <input type="hidden" name="command" value="register">

                            <c:import url="/WEB-INF/jsp/form/usernameField.jsp"/>
                            <div class="form-group">

                                <div class="col-md-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-envelope"></i></span>
                                        <input name="email" placeholder="E-mail" class="form-control"
                                               type="email"

                                               data-required-error="${emailEmpty}" required>
                                    </div>

                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <c:import url="/WEB-INF/jsp/form/passwordField.jsp"/>


                            <div class="form-group">

                                <div class="col-md-12">
                                    <button type="submit"
                                            class="btn btn-md btn-danger pull-right">${signupButton}</button>
                                </div>
                            </div>

                        </fieldset>
                    </form>
                    <c:if test="${not empty requestScope.registerError}">
                        <div class="alert alert-warning alert-dismissable fade in">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>

                            <c:choose>
                                <c:when test="${requestScope.registerError == 'login'}">
                                    <c:out value="${regLoginError}"/>
                                </c:when>
                                <c:when test="${requestScope.registerError == 'user'}">
                                    <c:out value="${regUserError}"/>
                                </c:when>
                                <c:when test="${requestScope.registerError == 'email'}">
                                    <c:out value="${regEmailError}"/>
                                </c:when>
                                <c:when test="${requestScope.registerError == 'password'}">
                                    <c:out value="${regPasswordError}"/>
                                </c:when>

                            </c:choose>
                        </div>
                    </c:if>

                </div>
            </div>

        </div>
    </article>
    <aside></aside>

</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>

</body>
</html>
