<%@page import="it.dstech.course.connection.Database"%>
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
		<%String saldo = (String)request.getAttribute("saldo") ; %> <%=saldo %>
		punti.
	</h1>
  <h2> Accumula punti selezionando le azioni che soddisfano tua moglie</h2>
	<%
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		List<Attivita> listaAzioniMoglie = (List<Attivita>) request.getAttribute("azioniMoglie");
		List<Attivita> listaAzioniMarito = (List<Attivita>) request.getAttribute("azioniMarito");
	%>
	<form action="azioniPerMoglie" method="POST">

		<table>
		<tr><th>Nome azione</th><th>Punti azione</th></tr>
		<%
			Integer i = 0;
		
			for (Attivita attivita : listaAzioniMoglie) {
		%>
		<tr>
		<th style="text-align: left">
		<input type="radio" name="attivita" value="<%=i%>"><%=attivita.getAzione()%></th><th><%=attivita.getPunteggio()%>
		<%
			i++;
		%>
		</th>
		</tr>
		<%
			}
		%>
		</table>
		<input type="hidden" name="saldo" value="<%=saldo%>"> 
		<input type="hidden" name="user" value="<%=user%>"> 
		<input type="hidden" name="pass" value="<%=pass%>"> 
		
		<input type="submit" value="Effettua l'azione">
			<br>
			<h2>Spendi i punti accumulati per effettuare azioni per te stesso!<br>
			
			 Le azioni di secondo livello possono essere sbloccate dopo aver effettuato dieci azioni per tua moglie</h2>
	</form>
	<br>
	<form action="azioniPerMarito" method="POST">
	<table>
	<tr><th>Nome azione</th><th>Punti azione</th><th>Livello azione</th></tr>
	
		<%
			Integer j = 0;
		%>

		<%
			for (Attivita attivita : listaAzioniMarito) {
		%>
		
		
		<%
			if (((attivita.getLivello() == 1) && (Database.checkPunti(user, attivita)))
						|| ((attivita.getLivello() == 2) && (Database.azioniPositive(user))
								&& (Database.checkPunti(user, attivita)))) {
		%>
		<tr>
		<th style="text-align:left">
		<input type="radio" name="attivita" value="<%=j%>"><%=attivita.getAzione()%></th><th><%=attivita.getPunteggio()%></th><th><%=attivita.getLivello()%></th>
		
		<%
			} else {
		%>
		<tr>
		<th style="text-align:left">
		<input type="radio" name="attivita" value="<%=j%>" disabled><%=attivita.getAzione()%></th><th><%=attivita.getPunteggio()%></th><th><%=attivita.getLivello()%></th>
		<%
			}
		%>
		<%
			j++;
		%>
		</tr>
		<%
			}
		%>
		</table>
		<input type="hidden" name="saldo" value="<%=saldo%>"> 
		<input type="hidden" name="user" value="<%=user%>">
		 <input	type="hidden" name="pass" value="<%=pass%>"> 
		 <input	type="submit" value="Effettua l'azione">
	</form>
	<br>
	<form action="storico" method="POST">
		<input type="hidden" name="user" value="<%=user%>">
		 <input	type="hidden" name="pass" value="<%=pass%>"> 
		 <input	type="submit" value="Visualizza il tuo storico azioni">
	</form>
	<br>
	<form action="inizio">
		<input type="submit" value="Logout">
		
	</form>
</body>
</html>