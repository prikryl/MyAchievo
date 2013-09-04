<html>
<body>
	<h2>Hello World!</h2>
	<form id="tw_signin" action="signin/facebook" method="POST">
		<button type="submit">facebook</button>
	</form>
	<form id="tw_signin" action="signin/google" method="POST">
		<input type="hidden" name="scope"
			value="https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.profile">
		<button type="submit">google</button>
	</form>
</body>
</html>
