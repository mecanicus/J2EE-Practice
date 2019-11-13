<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta charset="UTF-8">
<title>Researchers List</title>
<%@ include file = "Header.jsp"%>
</head>
<body>
<table>
<c:forEach items="${researcherList}" var="r_i">
    <tr>
        <td><a href="ResearcherServlet?id=${r_i.id}">${r_i.id}</a></td>
        <td>Name: ${r_i.name}</td>
        <td>Last Name: ${r_i.lastName}</td>
        <td>Number of publications: ${fn:length(r_i.publications)}</td>
        <td>Email: ${r_i.email}</td>
        <td>Eid: ${r_i.eid}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>