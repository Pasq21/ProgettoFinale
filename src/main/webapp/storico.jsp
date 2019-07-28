<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Stampa lista storico</title>
</head>


<body>
	<%
		String user = request.getParameter("user");
	%>
	<%
		String pass = request.getParameter("pass");
	%>
<form action="login" method="POST">
	<%
		List<String> lista = (List<String>) request.getAttribute("lista");
	%>
	<%
		for (String singoloStorico : lista) {
	%>
	<%=singoloStorico%>
	<br>
	<%
		}
	%>
	<input type="hidden" name="user" value="<%=user%>"> <input
			type="hidden" name="pass" value="<%=pass%>"> <input
			type="submit" value="Torna alla tua dashboard">
</form> 
</body>
</html>