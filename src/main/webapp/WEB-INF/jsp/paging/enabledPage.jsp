<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<li>
    <form method="get" action="${pageContext.request.contextPath}/mpb">
        <input type="hidden" name="command" value="take_movie_list">
        <input type="hidden" name="order" value="${requestScope.order}">
        <input type="hidden" name="onPage" value="${requestScope.onPage}">
        <input class="btn btn-default" type="submit" name="page" value="<c:out value="${param.pageNumber}"/>">
    </form>
</li>