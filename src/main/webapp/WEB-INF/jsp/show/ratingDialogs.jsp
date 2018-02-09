<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.response.success" var="successMessage"/>
<fmt:message bundle="${loc}" key="local.response.message.reate.success" var="rateApprovedMessage"/>
<fmt:message bundle="${loc}" key="local.message.oops" var="oopsMessage"/>
<fmt:message bundle="${loc}" key="local.response.message.rate.error" var="rateDeniedMessage"/>
<div class="modal fade" id="ratingSetSuccessful" role="dialog">
    <div class="modal-dialog modal-md">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;
                </button>
                <h3 class="modal-title">${successMessage}</h3>
            </div>
            <div class="modal-body">
                ${rateApprovedMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="ratingSetFailed" role="dialog">
    <div class="modal-dialog modal-md">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;
                </button>
                <h3 class="modal-title">${oopsMessage}</h3>
            </div>
            <div class="modal-body">
                <div class="fluid-container">
                    ${rateDeniedMessage}
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>