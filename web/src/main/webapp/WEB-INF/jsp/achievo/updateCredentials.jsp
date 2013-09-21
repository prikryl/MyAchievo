<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update achievo credentials</title>
</head>
<body>
	${credentials.username}
	<form:form commandName="/achievo/updateCredentials"
		modelAttribute="credentials">
		<label for="usernameInput">Username: </label>
		<form:input path="username" id="usernameInput"></form:input>
		<form:errors path="username" cssclass="error"></form:errors>
		<br />
		<label for="passwordInput">password: </label>
		<form:password path="password" id="passwordInput"></form:password>
		<form:errors path="password" cssclass="error"></form:errors>
		<br />
		<br />
		<input type="submit" value="Submit" />
	</form:form>

</body>
</html>

