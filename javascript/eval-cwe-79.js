<%@page import="org.owasp.esapi.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>Eval 취약점 샘플</h1>
<%
  	// 외부의 입력값을 받는다.
	String evalParam = request.getParameter("eval");

%>
<script>
	// 보안약점
  	eval(<%=evalParam%>);
</script>
</body>
</html>