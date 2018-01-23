<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<li>
    <form method="get" action="${pageContext.request.contextPath}/mpb">
        <input type="hidden" name="command" value="<c:out value="${param.command}"/>">
        <c:if test="${param.command =='take_movie_list'}">
            <input type="hidden" name="order" value="${requestScope.order}">
        </c:if>

        <input type="hidden" name="onPage" value="${requestScope.onPage}">
        <c:choose>
            <c:when test="${param.pageNumber =='>>'}">
                <input type="hidden" name="page" value="${requestScope.numOfPages}">
                <input class="btn btn-default" type="submit" value=">>">
            </c:when>
            <c:when test="${param.pageNumber =='<<'}">
                <input type="hidden" name="page" value="${1}">
                <input class="btn btn-default" type="submit" value="<<">
            </c:when>
            <c:otherwise>

                <input class="btn btn-default" type="submit" name="page" value="<c:out value="${param.pageNumber}"/>">
            </c:otherwise>
        </c:choose>

    </form>
</li>