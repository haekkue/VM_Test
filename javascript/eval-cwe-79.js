<%@page import="org.owasp.esapi.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>Eval ����� ����</h1>
<%
  	// �ܺ��� �Է°��� �޴´�.
	String evalParam = request.getParameter("eval");

%>
<script>
	// ���Ⱦ���
  	eval(<%=evalParam%>);
</script>
</body>
</html>