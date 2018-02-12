<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.error.field.empty" var="emptyFieldMessage"/>
<fmt:message bundle="${loc}" key="local.control.choose" var="chooseInfo"/>
<fmt:message bundle="${loc}" key="local.info.seasons.number" var="seasons"/>
<fmt:message bundle="${loc}" key="local.info.episodes.number" var="episodes"/>
<fmt:message bundle="${loc}" key="local.info.tv.channel" var="channel"/>
<fmt:message bundle="${loc}" key="local.info.user.status" var="showStatus"/>
<fmt:message bundle="${loc}" key="local.info.tv.finished.year" var="finishedYear"/>

<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="channel" class="control-label">${channel}</label>
            <input  class="form-control" type="text" name="channel"
                    maxlength="25"
                    required data-required-error="${emptyFieldMessage}"
                    id="channel">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>
<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="seasonsNum" class="control-label">${seasons}</label>
            <input  class="form-control" type="number"
                    data-required-error="${emptyFieldMessage}"
                    min="1" required
                    name="seasonsNum" id="seasonsNum">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="episodesNum" class="control-label">${episodes}</label>
            <input  class="form-control" type="number" name="episodesNum"
                    min="0"
                    required data-required-error="${emptyFieldMessage}"
                    id="episodesNum">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>
<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="tvShowStatusList" class="control-label">${showStatus}</label>
            <select class="form-control selectpicker" id="tvShowStatusList" name="tvShowStatus" required
                    title="${chooseInfo}"
                    data-required-error="${emptyFieldMessage}">
                <mpb:tv-show-status-list/>
            </select>
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

<div class="form-group" id="finishedYearFormGroup">

    <div class="col-md-12">

        <div class="input-group">
            <label for="finishedYear" class="control-label">${finishedYear}</label>
            <input  class="form-control" type="number" name="finishedYear"
                    min="1920"
                    data-required-error="${emptyFieldMessage}"
                    id="finishedYear">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

