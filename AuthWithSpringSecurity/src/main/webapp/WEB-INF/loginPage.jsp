<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>

<!-- busca si hay attributes en el Model, para desplegar un logout message o un error message -->
    <c:if test="${logoutMessage != null}">
        <c:out value="${logoutMessage}"></c:out>
    </c:if>
    <h1>Login</h1>
    <c:if test="${errorMessage != null}">
        <c:out value="${errorMessage}"></c:out>
    </c:if>

<!-- Es importante tener en cuenta que el formulario debe tener un campo name con el valor del  
username para que Spring Security pueda tomar la información correctamente en el
 método loadUserByUsername(String). -->

    <form method="POST" action="/login">
        <p>
            <label for="username">Username</label>
            <input type="text" id="username" name="username"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Login!"/>
    </form>
</body>
</html>