<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Azione effettuata con successo</title>
</head>
<body>
<h1> Azione eseguita!</h1>
<% String user = request.getParameter("user");%>
<% String pass = request.getParameter("pass");%>
<form action = "login" method = "POST"> 

<input type="hidden" name="user" value=<%=user %>>
<input type="hidden" name="pass" value=<%=pass %>>
<input type="submit" value="Torna alla dashboard">
</form>
</body>
</html>