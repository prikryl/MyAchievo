<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Hello World!</h2>

	<h4>Message : ${message}</h4>

	<h2>Hello World!</h2>
	<form id="tw_signin" action="<c:url value="/signin/twitter"/>"
		method="POST">
		<button type="submit"></button>
	</form>

</body>
</html>

