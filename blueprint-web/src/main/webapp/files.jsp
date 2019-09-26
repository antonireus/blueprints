<%@ page info="Item Form & List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Files</title>
</head>
<body>
<h1>Versió ${version.number}</h1>
<h2>Files</h2>

<form action="${pageContext.request.contextPath}/file" method="post" enctype="multipart/form-data">

    <label for="name">Name</label>
    <input id="name" type="text" name="name"/><br />

    <label for="file">File</label>
    <input id="file" type="file" name="file"/><br />

    <input type="submit" name="submit" value="Enviar" />
</form>

<p>File list</p>
<jsp:useBean id="files" scope="request"
             type="java.util.List<java.io.File>" />
<c:choose>
    <c:when test="${fn:length(files) > 0}">
        <ul>
            <c:forEach items="${files}" var="file">
                <li>
                    <c:out value="${file.name}" /><br />
                    <c:out value="${file.absolutePath}" /><br />
                    <c:out value="${file.length()}" /><br />
                </li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <p>Empty!</p>
    </c:otherwise>
</c:choose>
</body>
</html>
