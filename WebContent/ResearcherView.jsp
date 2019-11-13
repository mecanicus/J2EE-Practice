<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta charset="UTF-8">
<title>Researcher</title>
<%@ include file = "Header.jsp"%>
</head>
<body>
<table>
    <tr>
        <td>Name: ${researcher.name}</td>
        <td>Last Name: ${researcher.lastName}</td>
        <td>Publications: ${fn:length(researcher.publications)}</td>
        <td>Email: ${researcher.email}</td>
        <td>ScopusUrl: ${researcher.scopusUrl}</td>
        <td>Eid: ${researcher.eid}</td>
    </tr>
</table>
<table>
<c:forEach items="${publications}" var="p_i">
    <tr>
    	<td><a href="PublicationServlet?id=${p_i.id}">${p_i.id}</a></td>
        <td>Title: ${p_i.title}</td>
        <td>Publication Name: ${p_i.publicationName}</td>
        <td>Publication Date: ${p_i.publicationDate}</td>
        <td>Number of Authors: ${fn:length(p_i.authors)}</td>
        <td>Eid: ${p_i.eid}</td>
        <td>Cite Count: ${p_i.citeCount}</td>
        <td>First Author: ${p_i.firstAuthor}</td>
    </tr>
</c:forEach>
</table>
<form action="UpdateResearcherServlet" method="post">
    <input type="text" name="uid" placeholder="User Id"> 
    <input type="text" name="name" placeholder="Name">
    <input type="text" name="email" placeholder="Email">
    <input type="text" name="last_name" placeholder="Last name">
    <input type="text" name="eid" placeholder="Eid">
    <input type="password" name="password" placeholder="Password">
        <input type="text" name="scopusUrl" placeholder="scopusUrl">
    <button type="submit">Update researcher</button>
</form>

<form action="CreatePublicationServlet" method="post">
    <input type="text" name="id" placeholder="Publication Id"> 
   <input type="text" name="title" placeholder="Title">
   <input type="text" name="name" placeholder="Name">
   <input type="text" name="date" placeholder="Date">
   <input type="text" name="eid" placeholder="Eid">
    <button type="submit">Create Publication</button>
</form>

<form action="UpdatePublicationsQueueServlet" method="post">
	<input type="hidden" name="id" value="${researcher.id}"></input>
    <button type="submit">Update Publications from Queue</button>
</form>
<form action="GenerateCVServlet" method="post">
    <button type="submit">Generate CV</button>
</form>
<form action="GenerateCVMailServlet" method="post">
    <button type="submit">Generate CV Email</button>
</form>
</body>
</html>