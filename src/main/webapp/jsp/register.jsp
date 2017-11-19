
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register - MotionPicture Bank [MPB]</title>
</head>
<body>
<h4>Sign up new account now!</h4>
<form action="${pageContext.request.contextPath}/mpb" method="post">
    <input type="hidden" name="command" value="register">
    Username:<br>
    <label>
        <input type="text" name="login" value="">
    </label><br>
    Password:<br>
    <label>
        <input type="password" name="password" value="">
    </label><br>
    E-mail:<br>
    <label>
        <input type="text" name="eMail" value="">
    </label><br>
    <p id="button"><input type="submit" value="Sign up!"></p>
</form>
</body>
</html>
