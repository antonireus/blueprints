<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Error</h1>
<table>
    <tr>
        <td>Status Code</td>
        <td><c:out value="${pageContext.errorData.statusCode}" /></td>
    </tr>
    <tr>
        <td>Servlet Name</td>
        <td><c:out value="${pageContext.errorData.servletName}" /></td>
    </tr>
    <tr>
        <td>Request URI</td>
        <td><c:out value="${pageContext.errorData.requestURI}" /></td>
    </tr>
    <tr>
        <td>Exception</td>
        <td><c:out value="${pageContext.exception}" /></td>
    </tr>
    <tr>
        <td>Exception Message</td>
        <td><c:out value="${pageContext.exception.message}" /></td>
    </tr>
    <tr>
        <td>Exception Cause</td>
        <td><c:out value="${pageContext.exception.cause}" /></td>
    </tr>
    <tr>
        <td>Exception Cause Message</td>
        <td><c:out value="${pageContext.exception.cause.message}" /></td>
    </tr>
</table>
</body>
</html>
