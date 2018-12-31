<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
 <body>
 <form action ="add.mm">
  
	Enter Name :&nbsp &nbsp<input type = "text" name="name" > <br><br>
	Enter Balance :&nbsp<input type = "number" name="accountBalance"> <br><br>
	Salary :<input type = radio name="rdSalary" value = "true"> True  &nbsp 
	<input type = radio name="rdSalary" value = "false">False <br><br>
	<input type = "submit" name="submit"> 	&nbsp &nbsp&nbsp<input type = "reset" name="reset"> <br>
		
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
	
</body>
</html>