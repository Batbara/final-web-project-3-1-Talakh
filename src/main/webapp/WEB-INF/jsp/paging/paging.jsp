<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="pagination">

    <c:choose>
        <c:when test="${requestScope.numOfPages <= 5}">

            <c:choose>
                <c:when test="${requestScope.page eq 1}">
                    <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                        <c:param name="pageNumber" value="<<"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>
                    <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                        <c:param name="pageNumber" value="${1}"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>
                </c:when>
                <c:otherwise>
                    <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                        <c:param name="pageNumber" value="<<"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>
                    <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                        <c:param name="pageNumber" value="${1}"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>
                </c:otherwise>
            </c:choose>

            <c:forEach begin="2" end="${requestScope.numOfPages}" var="i">

                <c:choose>

                    <c:when test="${requestScope.page eq i}">
                        <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                            <c:param name="pageNumber" value="${i}"/>
                            <c:param name="command" value="${param.command}"/>
                        </c:import>
                    </c:when>
                    <c:otherwise>
                        <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                            <c:param name="pageNumber" value="${i}"/>
                            <c:param name="command" value="${param.command}"/>
                        </c:import>
                    </c:otherwise>
                </c:choose>

            </c:forEach>

            <c:choose>
                <c:when test="${requestScope.page eq requestScope.numOfPages}">
                    <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                        <c:param name="pageNumber" value=">>"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>
                </c:when>
                <c:otherwise>
                    <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                        <c:param name="pageNumber" value=">>"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>

                </c:otherwise>
            </c:choose>

        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${requestScope.page < 4}">

                    <c:choose>
                        <c:when test="${requestScope.page eq 1}">
                            <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                <c:param name="pageNumber" value="<<"/>
                            </c:import>
                            <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                <c:param name="pageNumber" value="${1}"/>
                            </c:import>
                        </c:when>
                        <c:otherwise>
                            <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                <c:param name="pageNumber" value="<<"/>
                                <c:param name="command" value="${param.command}"/>
                            </c:import>

                            <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                <c:param name="pageNumber" value="${1}"/>
                                <c:param name="command" value="${param.command}"/>
                            </c:import>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="2" end="5" var="i">
                        <c:choose>
                            <c:when test="${requestScope.page eq i}">
                                <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                    <c:param name="pageNumber" value="${i}"/>
                                </c:import>
                            </c:when>
                            <c:otherwise>
                                <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                    <c:param name="pageNumber" value="${i}"/>
                                    <c:param name="command" value="${param.command}"/>
                                </c:import>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${requestScope.page eq requestScope.numOfPages}">
                            <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                <c:param name="pageNumber" value=">>"/>
                            </c:import>
                        </c:when>
                        <c:otherwise>

                            <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                <c:param name="pageNumber" value=">>"/>
                                <c:param name="command" value="${param.command}"/>
                            </c:import>

                        </c:otherwise>
                    </c:choose>

                </c:when>

                <c:when test="${requestScope.page < requestScope.numOfPages-2}">

                    <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                        <c:param name="pageNumber" value="<<"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>
                    <c:forEach begin="${requestScope.page-2}" end="${requestScope.page+2}" var="i">
                        <c:choose>
                            <c:when test="${requestScope.page eq i}">
                                <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                    <c:param name="pageNumber" value="${i}"/>
                                </c:import>
                            </c:when>
                            <c:otherwise>

                                <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                    <c:param name="pageNumber" value="${i}"/>
                                    <c:param name="command" value="${param.command}"/>
                                </c:import>

                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                        <c:param name="pageNumber" value=">>"/>
                        <c:param name="command" value="${param.command}"/>
                    </c:import>

                </c:when>

                <c:otherwise>
                    <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                        <c:param name="pageNumber" value="<<"/>
                    </c:import>

                    <c:forEach begin="${requestScope.numOfPages-4}" end="${requestScope.numOfPages}" var="i">
                        <c:choose>
                            <c:when test="${requestScope.page eq i}">
                                <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                    <c:param name="pageNumber" value="${i}"/>
                                </c:import>
                            </c:when>
                            <c:otherwise>
                                <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                    <c:param name="pageNumber" value="${i}"/>
                                    <c:param name="command" value="${param.command}"/>
                                </c:import>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>

                        <c:when test="${requestScope.page eq requestScope.numOfPages}">
                            <c:import url="/WEB-INF/jsp/paging/disabledPage.jsp">
                                <c:param name="pageNumber" value=">>"/>
                            </c:import>
                        </c:when>
                        <c:otherwise>
                            <c:import url="/WEB-INF/jsp/paging/enabledPage.jsp">
                                <c:param name="pageNumber" value=">>"/>
                                <c:param name="command" value="${param.command}"/>
                            </c:import>
                        </c:otherwise>

                    </c:choose>

                </c:otherwise>

            </c:choose>

        </c:otherwise>

    </c:choose>

</ul>