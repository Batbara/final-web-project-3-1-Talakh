<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.show.table.header.title" var="showTitle"/>
<fmt:message bundle="${loc}" key="local.info.genre" var="showGenre"/>
<fmt:message bundle="${loc}" key="local.info.country" var="showCountry"/>
<fmt:message bundle="${loc}" key="local.info.choose" var="chooseInfo"/>
<fmt:message bundle="${loc}" key="local.info.premiere" var="premiereDateInfo"/>

<fmt:message bundle="${loc}" key="local.show.sidenav.synopsis" var="synopsisInfo"/>
<fmt:message bundle="${loc}" key="local.info.runtime" var="runtimeMessage"/>
<fmt:message bundle="${loc}" key="local.info.min" var="mins"/>
<fmt:message bundle="${loc}" key="local.info.english" var="englishTitlePlaceholder"/>
<fmt:message bundle="${loc}" key="local.info.russian" var="russianTitlePlaceholder"/>
<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="title_en" class="control-label">${showTitle}</label>
            <input type="text" name="title_en" placeholder="${englishTitlePlaceholder}"
                   class="form-control" id="title_en" required maxlength="65">
            <input type="text" name="title_ru" placeholder="${russianTitlePlaceholder}"
                   class="form-control" id="title_ru" maxlength="65">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>
<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="premiereDate" class="control-label">${premiereDateInfo}</label>
            <input type="date" name="premiereDate"
                   class="form-control" id="premiereDate" required>

        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>
<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="genreList" class="control-label">${showGenre}</label>
            <select class="form-control selectpicker" id="genreList" name="genreId"
                    title="${chooseInfo}" data-language="${sessionScope.local}" data-selected-text-format="count > 3" multiple>


            </select>
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="countryList" class="control-label">${showCountry}</label>
            <select class="form-control selectpicker" id="countryList" name="countryId"
                    title="${chooseInfo}"  data-selected-text-format="count > 3" multiple>


            </select>
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="runtime" class="control-label">${runtimeMessage}</label>
            <input  class="form-control" type="time" name="runtime" id="runtime">
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>

<div class="form-group">

    <div class="col-md-12">

        <div class="input-group">
            <label for="synopsis_en" class="control-label">${synopsisInfo}</label>
            <textarea  class="form-control" name="synopsis_en" id="synopsis_en"
                       placeholder="${englishTitlePlaceholder}" required></textarea>
            <textarea  class="form-control" name="synopsis_ru" id="synopsis_ru"
                       placeholder="${russianTitlePlaceholder}"></textarea>
        </div>

        <div class="help-block with-errors"></div>
    </div>
</div>
