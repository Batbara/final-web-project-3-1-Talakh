<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.user.message.signup" var="signupMessage"/>
    <fmt:message bundle="${loc}" key="local.submit.signup" var="signupButton"/>
    <fmt:message bundle="${loc}" key="local.error.register.user" var="regUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="regLoginError"/>
    <fmt:message bundle="${loc}" key="local.error.register.email" var="regEmailError"/>
    <fmt:message bundle="${loc}" key="local.error.register.password" var="regPasswordError"/>
    <fmt:message bundle="${loc}" key="local.error.email.empty" var="emailEmpty"/>

    <title><c:out value="${signupButton}"/> | MotionPicture Bank [MPB]</title>
</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<c:if test="${sessionScope.user ne null}">
    <c:redirect url="/index.jsp"/>
</c:if>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="container-fluid">
            <div class="row-fluid">

                <div class="col-md-offset-4 col-md-4" id="box">
                    <h2>${signupMessage}</h2>
                    <hr>
                    <form class="form-horizontal" action="${pageContext.request.contextPath}/mpb" method="post"
                          id="registerForm" data-toggle="validator" role="form">
                        <fieldset>
                            <input type="hidden" name="command" value="register">

                            <c:import url="/WEB-INF/jsp/user/usernameField.jsp"/>
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
                            <c:import url="/WEB-INF/jsp/user/passwordField.jsp"/>


                            <div class="form-group">

                                <div class="col-md-12">
                                    <button type="submit"
                                            class="btn btn-md btn-danger pull-right">${signupButton}</button>
                                </div>
                            </div>

                        </fieldset>K
                    </form>
                    <div id="errorContainer">
                    </div>

                </div>
            </div>

        </div>
    </article>
    <aside></aside>

</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>

</body>
</html>
