<%@ page info="Item Form & List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Items</title>
</head>
<body>
    <h1>Versió ${version.number}</h1>
    <h2>Items</h2>
    <c:if test="${sessionScope['item_constraintViolations'] != null}">
        <jsp:useBean id="item_constraintViolations" scope="session"
             type="java.util.Set<javax.validation.ConstraintViolation<org.fundaciobit.blueprint.ejb.jpa.Item>>" />
        <p>Validation errors</p>
        <ul>
        <c:forEach items="${item_constraintViolations}" var="constraintViolation">
            <li>
                <c:out value="${constraintViolation.propertyPath}" />:
                <c:out value="${constraintViolation.message}" />
            </li>
        </c:forEach>
        </ul>
        <c:remove var="item_constraintViolations" scope="session"/>
    </c:if>

    <form action="<c:url value="/item" />" method="post">
        <label for="name">Name</label>
        <input id="name" type="text" name="name" value="${param.name}"/>

        <label for="nif">NIF</label>
        <input id="nif" type="text" name="nif" value="${param.nif}"/>

        <label for="description_ca">Description CA</label>
        <input id="description_ca" type="text" name="description_ca" value="${param.description_ca}"/>

        <label for="description_es">Description ES</label>
        <input id="description_es" type="text" name="description_es" value="${param.description_es}"/>

        <input type="submit" name="submit" value="Enviar" />
    </form>

    <p>Item list</p>
    <jsp:useBean id="items" scope="request"
                 type="java.util.List<org.fundaciobit.blueprint.ejb.jpa.Item>" />
    <c:choose>
        <c:when test="${fn:length(items) > 0}">
            <ul>
            <c:forEach items="${items}" var="item">
                <li>
                    <c:out value="${item.id}" />,
                    <c:out value="${item.name}" />,
                    <c:out value="${item.nif}" />,
                    <c:out value="${item.creation}" /><br />
                    <c:out value="${item.description['ca']}" /><br />
                    <c:out value="${item.description['es']}" />
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
