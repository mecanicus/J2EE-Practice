<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%@ include file = "Header.jsp"%>
</head>
<body>
<form action="LoginServlet" method="post">
   <input type="text" name="email" placeholder="Email">
   <input type="password" name="password" placeholder="Password">
   <button type="submit">Login</button>
</form>
</body>
</html>