<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action ="deposite.mm">
  
	Enter Account Number :&nbsp &nbsp<input type = "number" name="accountNumber" > <br><br>
	Enter Amount To Deposite :&nbsp<input type = "number" name="amount"> <br><br>
 	<input type = "submit" name="submit"> 	&nbsp &nbsp&nbsp<input type = "reset" name="reset"> <br>
		
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>