<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta charset="UTF-8">
<title>Administrator</title>
<%@ include file = "Header.jsp"%>
</head>
<body>
<form action="CreateResearcherServlet" method="post">
    <input type="text" name="uid" placeholder="User Id"> 
    <input type="text" name="name" placeholder="Name">
    <input type="text" name="email" placeholder="Email">
    <input type="text" name="last_name" placeholder="Last name">
           <input type="text" name="eid" placeholder="Eid">
    <input type="text" name="scopusUrl" placeholder="ScopusUrl">
    <input type="password" name="password" placeholder="Password">
    <button type="submit">Create researcher</button>
</form>
<p></p>
<form action="PopulateResearcherServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="file" />
    <button type="submit">Populate Researchers</button>
</form>
<p></p>
<form action="PopulatePublicationServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="file" />
    <button type="submit">Populate Publications</button>
</form>
</body>
</html>