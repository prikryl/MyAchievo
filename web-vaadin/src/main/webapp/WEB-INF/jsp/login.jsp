<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>MyAchievo</title>
<link rel="stylesheet" href="<c:url value="/static/css/style.css" />" />
<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
		<p style="
    /* -webkit-font-smoothing: antialiased; */
    color: rgb(208, 209, 211);
    /* cursor: default; */
    /* display: block; */  font-family: 'Open Sans', Arial, sans-serif;
    font-size: 33px;
    /* font-style: normal; */
    /* font-variant: normal; */
    /* font-weight: normal; */
    /* height: 14px; */
    /* line-height: 14.300000190734863px; */  text-align: left;
    text-shadow: rgba(0, 0, 0, 0.498039) 0px 2px 2px;
    /* white-space: normal; */
    /* width: 85px; */
    padding: 1em;
">
			<span>MyAchievo</span>
		</p>

	<form name='f' action="<c:url value='/j_spring_security_check' />"
		class="login" method='POST'>

		<p>
			<label for="j_username">Jméno:</label> <input type="text"
				name="j_username" id="j_username">
		</p>

		<p>
			<label for="j_password">Heslo:</label> <input type="password"
				name="j_password" id="j_password" value="">
		</p>

		<p class="login-submit">
			<button type="submit" class="login-button">Login</button>
		</p>

		<p class="forgot-password">Pozor, použijte heslo do achieva.
			Nebývá stejné jako do domény!</p>
	</form>

<!-- 		<section class="about"> -->
<!-- 			<p class="about-links"> -->
<!-- 				<a href="http://www.cssflow.com/snippets/dark-login-form" -->
<!-- 					target="_parent">MyAchievo</a> -->
<!-- 					<a -->
<!-- 					href="http://www.cssflow.com/snippets/dark-login-form.zip" -->
<!-- 					target="_parent">Download</a> -->
<!-- 			</p> -->
<!-- 			<p class="about-author"> -->
<!-- 				&copy; 2012&ndash;2013 <a href="http://thibaut.me" target="_blank">Thibaut -->
<!-- 					Courouble</a> - <a href="http://www.cssflow.com/mit-license" -->
<!-- 					target="_blank">MIT License</a><br> Original PSD by <a -->
<!-- 					href="http://365psd.com/day/2-234/" target="_blank">Rich McNabb</a> -->
<!-- 		</section> -->
</body>
</html>


