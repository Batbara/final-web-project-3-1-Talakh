<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.control.choose" var="chooseInfo"/>
<fmt:message bundle="${loc}" key="local.info.boxoffice" var="boxOfficeMessage"/>
<fmt:message bundle="${loc}" key="local.info.mpaarating" var="mpaaRatingMessage"/>
<fmt:message bundle="${loc}" key="local.info.budget" var="budgetMessage"/>


<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="mpaaRatingList" class="control-label">${mpaaRatingMessage}</label>
            <select class="form-control selectpicker" id="mpaaRatingList" name="mpaaRating"
                    title="${chooseInfo}" data-language="${sessionScope.local}" >
                <mpb:mpaa-list/>
            </select>
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>


<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="budget" class="control-label">${budgetMessage}</label>
            <input  class="form-control" type="number" name="budget" id="budget">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="boxOffice" class="control-label">${boxOfficeMessage}</label>
            <input  class="form-control" type="number" name="boxOffice" id="boxOffice">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>
