<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<form action ="add.mm">
  
	<!-- Enter Name :&nbsp &nbsp<input type = "text" name="name" > <br><br>
	Enter Balance :&nbsp<input type = "number" name="accountBalance"> <br><br>
	Salary :<input type = radio name="rdSalary" value = "true"> True  &nbsp 
	<input type = radio name="rdSalary" value = "false">False <br><br>
	<input type = "submit" name="submit"> 	&nbsp &nbsp&nbsp<input type = "reset" name="reset"> <br> -->
		
	</form>


	<table>
		<tr>
			<th>Account Number </th>
			<th>Holder Name</th>
			<th>Account Balance</th>
			<th>Salary</th>
			<th>  Over Draft Limit </th>
			<th>Type Of Account</th>
		</tr>
		<jstl:if test="${requestScope.account!=null}">
			<tr>
			 	<td>${requestScope.account.bankAccount.accountNumber}</td>
				 <td><input type = "text" name="name" value ="<%=request.account.bankAccount.accountHolderName %>"></td>
				<%-- <td>${requestScope.account.bankAccount.accountBalance}</td>
				<td>${requestScope.account.salary==true?"Yes":"No"}</td>
				<td>${"N/A"}</td>
				<td>${"Savings"}</td> 	 --%>  
			</tr>
		</jstl:if>
		<jstl:if test="${requestScope.accounts!=null}">
			<jstl:forEach var="account" items="${requestScope.accounts}">
				<tr>
					<td>${account.bankAccount.accountNumber}</td>
					<td>${account.bankAccount.accountHolderName }</td>
					<td>${account.bankAccount.accountBalance}</td>
					<td>${account.salary==true?"Yes":"No"}</td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</tr>
			</jstl:forEach>
		</jstl:if>
	</table>
 	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>







