<div class="lang_button">
    <form action="${pageContext.request.contextPath}/mpb" method="post">
        <input type="hidden" name="command" value="change_language"/>

        <input type="hidden" name="address" value="${pageContext.request.requestURL}"/>
        <input type="hidden" name="query" value="${pageContext.request.queryString}"/>
        <input type="hidden" name="local" value="${param.local}"/>
        <input class="lang-button" type="submit" value="${param.lang}"/>

    </form>
</div>