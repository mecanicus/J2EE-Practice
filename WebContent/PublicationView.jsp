<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta charset="UTF-8">
<title>Publication View</title>
<%@ include file = "Header.jsp"%>
</head>
<body>

<c:forEach items="${authors}" var="a_i">
    <tr>
        <td><a href="ResearcherServlet?id=${a_i.id}">${a_i.id}</a></td>
    </tr>
</c:forEach>
<table>
    <tr>
        <td>Title: ${publication.title}</td>
        <td>Publication Name: ${publication.publicationName}</td>
        <td>Publication Date: ${publication.publicationDate}</td>
        <td>Publication citation count ${publication.citeCount}</td>
    </tr>
</table>
<c:if test="${userAdmin=='true' or user.id == firstAuthor.id}">
    <form action="UpdatePublicationServlet" method="post">
   <input type="text" name="title" placeholder="Title">
   <input type="text" name="name" placeholder="Name">
   <input type="text" name="date" placeholder="Date">
   <input type="text" name="eid" placeholder="Eid">
   <button type="submit">Update Publication</button>
</form>
<form action="UpdateCitationsAPIServlet" method="post">
   <input type="hidden" name="id" value="${publication.id}">
   <button type="submit">Citation Count</button>
</form>
</c:if>
</body>
</html>