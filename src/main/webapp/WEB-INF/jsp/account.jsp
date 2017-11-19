
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User profile - MotionPicture Bank [MPB]</title>
</head>
<body>
        <h1>This is your account <c:out value="${sessionScope.user.userName}"/>, welcome!</h1>
</body>
</html>
