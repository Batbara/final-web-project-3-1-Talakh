<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.show.table.header.poster" var="posterLabel"/>
<fmt:message bundle="${loc}" key="local.admin.add" var="addButton"/>

<fmt:message bundle="${loc}" key="local.lang.english.full" var="englishTitlePlaceholder"/>
<fmt:message bundle="${loc}" key="local.lang.russian.full" var="russianTitlePlaceholder"/>
<label for="posters" class="control-label">${posterLabel}</label>
<div class="input-group" id="posters">

    <label for="poster_en" class="control-label">${englishTitlePlaceholder}</label>
    <input type="file" id="poster_en" name="poster_en" />

    <label for="poster_ru" class="control-label">${russianTitlePlaceholder}</label>
    <input type="file" id="poster_ru" name="poster_ru" placeholder="${russianTitlePlaceholder}"/>
</div>
