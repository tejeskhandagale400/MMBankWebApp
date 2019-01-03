<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<form action="updateInDB.mm">
 


	<table>
		<tr>
			<th>Account Number</th>
			<th>Holder Name</th>
			<th>Account Balance</th>
			<th>Salary</th>
			<th>Over Draft Limit</th>
			<th>Type Of Account</th>
		</tr>
		<jstl:if test="${requestScope.account!=null}">

			<tr>
				<td name="accountNumber">  ${requestScope.account.bankAccount.accountNumber}</td>
				<td><input type="text" name="name"
					value="${account.bankAccount.accountHolderName}"></td>
				<td>${account.bankAccount.accountBalance}</td>
				<jstl:if test="${requestScope.account.salary==true}">
					<td><select name="salary">
							<option value="salaryTrue">Yes</option>
							<option value="salaryFalse">No</option></td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</jstl:if>
				<jstl:if test="${requestScope.account.salary==false}">
					<td><select name="salary">
							<option value="salaryFalse">No</option>
							<option value="salaryTrue">Yes</option></td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</jstl:if>
			</tr>
		</jstl:if>
			
	</table>
	<input type = "submit" name="submit"> 	&nbsp &nbsp&nbsp<input type = "reset" name="reset"> <br>
		
			</form>
		
	
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>

