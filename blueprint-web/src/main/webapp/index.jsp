<%@ page info="Item Form & List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hello world!</title>
</head>
<body>
<h1>Hello world!</h1>
<ul>
    <li><a href="<c:url value="/item" />">Items</a></li>
    <li><a href="<c:url value="/file" />">Files</a></li>
    <li><a href="<c:url value="/mail" />">Mail</a></li>
    <li><a href="<c:url value="/chat.jsp" />">Chat</a></li>
    <li><a href="<c:url value="/faces" />">Faces</a></li>
</ul>
</body>
</html>