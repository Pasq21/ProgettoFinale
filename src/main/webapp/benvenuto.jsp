<%@page import="java.util.List"%>
<%@page import="it.dstech.mogliemiglia.Attivita"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>
		Benvenuto
		<%=request.getParameter("user")%>! Il tuo saldo è
		<%=request.getAttribute("saldo")%>
		punti.
	</h1>

	<%
		String user = request.getParameter("user");
	%>
	<%
		String pass = request.getParameter("pass");
	%>
	<%
		List<Attivita> listaAzioniMoglie = (List<Attivita>) request.getAttribute("azioniMoglie");
	%>
	<form action="azioniPerMoglie" method="POST">


		<%
			Integer i = 0;
		%>

		<%
			for (Attivita attivita : listaAzioniMoglie) {
		%>
		<input type="radio" name="attivita" value="<%=i%>"><%=attivita%>
		<%
			i++;
		%>
		<br>
		<%
			}
		%>
		<input type="hidden" name="user" value="<%=user%>"> <input
			type="hidden" name="pass" value="<%=pass%>"> <input
			type="submit" value="Effettua l'azione">
	</form>
	<br>
	<br>
	<br>
	<form action="azioniPerMarito" method="POST">
		<%
			for (Attivita attivita : ((List<Attivita>) request.getAttribute("azioniMarito"))) {
		%>
		<%=attivita%>
		<br>
		<%
			}
		%>
		<input type="submit" value="Effettua l'azione">
	</form>
	<br>
	<br>
	<br>
	<form action="storico" method="POST">
		<input type="hidden" name="user" value="<%=user%>"> <input
			type="hidden" name="pass" value="<%=pass%>"> <input
			type="submit" value="Visualizza il tuo storico azioni">
	</form>
</body>
</html>