<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/> <fmt:message bundle="${loc}" key="local.error.login" var="loginError"/>
<fmt:message bundle="${loc}" key="local.error.login.password" var="loginPasswordError"/>
<fmt:message bundle="${loc}" key="local.error.username" var="usernamePatternMismatch"/>
<fmt:message bundle="${loc}" key="local.error.username.empty" var="usernameEmpty"/>
<fmt:message bundle="${loc}" key="local.message.username" var="userNamePlaceholder"/>
<div class="form-group">

    <div class="col-md-12">
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
            <input name="login" placeholder="${userNamePlaceholder}" class="form-control"
                   pattern="^[a-zA-Z0-9_]{3,}$" type="text"
                   data-pattern-error="${usernamePatternMismatch}"
                   data-required-error="${usernameEmpty}" required>
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>