<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>logout.jsp</title>
</head>
<body>
	<%session.invalidate(); %>

	<h1>로그아웃이 되었습니다.</h1>
	<br><br><br>
	<a href="login.html">로그인</a>
</body>
</html>