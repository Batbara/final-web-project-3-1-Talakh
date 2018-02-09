<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.user.message.password" var="passwordPlaceholder"/>
<fmt:message bundle="${loc}" key="local.error.password" var="passwordPatternMismatch"/>
<fmt:message bundle="${loc}" key="local.error.password.empty" var="emptyField"/>
<div class="form-group">

    <div class="col-md-12">
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
            <input name="password" id="#userPassword" type="password"
                   pattern="^[a-zA-Z0-9!*_?@#$%^&]{5,}$"
                   placeholder="${passwordPlaceholder}" class="form-control" maxlength="16"
                   data-pattern-error="${passwordPatternMismatch}"
                   data-required-error="${emptyField}"
                   required>
        </div>
        <div class="help-block with-errors"></div>
    </div>
</div>