<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Login </title>
</head>
<body>

<h2>Inserisci username e password: </h2>
    <form action="login" method="POST">
      <label for="uname"><b>Username</b></label>
      <input type="text" placeholder="Inserisci Username" name="uname" required>

      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Inserisci Password" name="psw" required>
        
      <input type="submit" value="accedi">
  </form>



</body>
</html>