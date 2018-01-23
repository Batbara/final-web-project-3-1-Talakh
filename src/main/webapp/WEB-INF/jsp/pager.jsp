<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<ul class="pagination">

    <c:choose>
        <c:when test="${requestScope.numOfPages <= 5}">
            <c:choose>
                <c:when test="${requestScope.page eq 1}">
                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                               value="<<"></li>
                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                               value="1"></li>
                </c:when>
                <c:otherwise>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="1">
                            <input class="btn btn-default" type="submit" value="<<">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page" value="${1}">
                        </form>
                    </li>

                </c:otherwise>
            </c:choose>
            <c:forEach begin="2" end="${requestScope.numOfPages}" var="i">
                <c:choose>

                    <c:when test="${requestScope.page eq i}">
                        <li><input class="btn btn-default active disabled" type="submit" name="page"
                                   value="${i}"></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <form method="get" action="${pageContext.request.contextPath}/mpb">
                                <input type="hidden" name="command" value="take_movie_list">
                                <input type="hidden" name="order" value="${requestScope.order}">
                                <input type="hidden" name="onPage" value="${requestScope.onPage}">
                                <input class="btn btn-default" type="submit" name="page" value="${i}">
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${requestScope.page eq requestScope.numOfPages}">

                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                               value=">>"></li>
                </c:when>
                <c:otherwise>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="${requestScope.numOfPages}">
                            <input class="btn btn-default" type="submit" value=">>">
                        </form>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:otherwise>
            <c:choose>
                <c:when test="${requestScope.page eq 1}">
                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                               value="<<"></li>
                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                               value="1"></li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page"
                                   value="2">
                        </form>
                    </li>

                </c:when>
                <c:when test="${requestScope.page < requestScope.numOfPages-3}">
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="1">
                            <input class="btn btn-default" type="submit" value="<<">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default active disabled" type="submit" name="page"
                                   value="${requestScope.page}">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page"
                                   value="${requestScope.page+1}">
                        </form>
                    </li>

                </c:when>
                <c:when test="${requestScope.page eq requestScope.numOfPages-3}">
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="1">
                            <input class="btn btn-default" type="submit" value="<<">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default active disabled" type="submit" name="page"
                                   value="${requestScope.numOfPages-3}">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page"
                                   value="${requestScope.numOfPages-2}">
                        </form>
                    </li>
                </c:when>
                <c:when test="${requestScope.page eq requestScope.numOfPages-2}">
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="1">
                            <input class="btn btn-default" type="submit" value="<<">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page" value="${1}">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default active disabled" type="submit" name="page"
                                   value="${requestScope.numOfPages-2}">
                        </form>
                    </li>

                </c:when>
                <c:otherwise>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="1">
                            <input class="btn btn-default" type="submit" value="<<">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page" value="1">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page"
                                   value="2">
                        </form>
                    </li>

                </c:otherwise>

            </c:choose>

            <li><input class="btn btn-default active disabled" type="submit" name="page"
                       value="..."></li>
            <c:choose>
                <c:when test="${requestScope.numOfPages eq requestScope.page}">
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page"
                                   value="${requestScope.numOfPages-1}">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default  active disabled" type="submit" name="page"
                                   value="${requestScope.numOfPages}">
                        </form>
                    </li>
                    <li><input class="btn btn-default active disabled" type="submit" name="page"
                               value=">>"></li>
                </c:when>
                <c:when test="${requestScope.numOfPages-1 eq requestScope.page}">
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default active disabled" type="submit" name="page"
                                   value="${requestScope.numOfPages-1}">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input class="btn btn-default" type="submit" name="page" value="${requestScope.numOfPages}">
                        </form>
                    </li>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="${requestScope.numOfPages}">
                            <input class="btn btn-default" type="submit" value=">>">
                        </form>
                    </li>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="${requestScope.numOfPages-1}" end="${requestScope.numOfPages}" var="n">
                        <li>
                            <form method="get" action="${pageContext.request.contextPath}/mpb">
                                <input type="hidden" name="command" value="take_movie_list">
                                <input type="hidden" name="order" value="${requestScope.order}">
                                <input type="hidden" name="onPage" value="${requestScope.onPage}">
                                <input class="btn btn-default" type="submit" name="page" value="${n}">
                            </form>
                        </li>

                    </c:forEach>
                    <li>
                        <form method="get" action="${pageContext.request.contextPath}/mpb">
                            <input type="hidden" name="command" value="take_movie_list">
                            <input type="hidden" name="order" value="${requestScope.order}">
                            <input type="hidden" name="onPage" value="${requestScope.onPage}">
                            <input type="hidden" name="page" value="${requestScope.numOfPages}">
                            <input class="btn btn-default" type="submit" value=">>">
                        </form>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:otherwise>

    </c:choose>

</ul>
