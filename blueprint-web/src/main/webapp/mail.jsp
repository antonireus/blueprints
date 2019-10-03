<%@ page info="Item Form & List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Mail</title>
</head>
<body>
    <h1>Versió ${version.number}</h1>
    <h2>Mails</h2>
    <c:if test="${sessionScope['mail_constraintViolations'] != null}">
        <jsp:useBean id="mail_constraintViolations" scope="session"
             type="java.util.Set<javax.validation.ConstraintViolation<org.fundaciobit.blueprint.ejb.jpa.Item>>" />
        <p>Validation errors</p>
        <ul>
        <c:forEach items="${mail_constraintViolations}" var="constraintViolation">
            <li>
                <c:out value="${constraintViolation.propertyPath}" />:
                <c:out value="${constraintViolation.message}" />
            </li>
        </c:forEach>
        </ul>
        <c:remove var="mail_constraintViolations" scope="session"/>
    </c:if>

    <form action="${pageContext.request.contextPath}/mail" method="post">
        <label for="subject">Subject</label>
        <input id="subject" type="text" name="subject" value="Test subject" /><br />

        <label for="email">Email</label>
        <input id="email" type="text" name="email" /><br />

        <label for="content">Content</label>
        <textarea id="content" name="content">Test</textarea>

        <input type="submit" name="submit" value="Enviar" />
    </form>

</body>
</html>
