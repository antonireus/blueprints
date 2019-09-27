<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Error</h1>
<table>
    <tr>
        <td>Status Code</td>
        <td>${pageContext.errorData.statusCode}</td>
    </tr>
    <tr>
        <td>Servlet Name</td>
        <td>${pageContext.errorData.servletName}</td>
    </tr>
    <tr>
        <td>Request URI</td>
        <td>${pageContext.errorData.requestURI}</td>
    </tr>
    <tr>
        <td>Exception</td>
        <td>${pageContext.exception}</td>
    </tr>
    <tr>
        <td>Exception Message</td>
        <td>${pageContext.exception.message}</td>
    </tr>
    <tr>
        <td>Exception Cause</td>
        <td>${pageContext.exception.cause}</td>
    </tr>
    <tr>
        <td>Exception Cause Message</td>
        <td>${pageContext.exception.cause.message}</td>
    </tr>
</table>
<h2>StackTrace</h2>
<pre>${pageContext.out.flush();pageContext.exception.printStackTrace(pageContext.response.writer)}</pre>
</body>
</html>
