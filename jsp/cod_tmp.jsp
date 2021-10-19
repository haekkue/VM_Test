<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
<%@ page import="java.io.*, java.util.*, java.text.SimpleDateFormat, java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>

<%

public void bad() throws Throwable
{
	String data;
	data = System.getParameter("user.home"); 	// getProperty 메소드를 통해 입력값 받음

	Connection conn_tmp2 = null;
	Statement sqlstatement = null;

	try {
		conn_tmp2 = IO.getDBConnection();
		sqlstatement = conn_tmp2.createStatement();

		// 보안약점 : 외부 입력값이 아무런 필터링없이 쿼리문에 사용
    		Boolean bResult = sqlstatement.executeQuery("insert into users (status) values ('updated') where name='"+data+"'");
  	}
}


private void good() throws Throwable
{
	String data;
	data = System.getProperty("user.home");

	Connection conn_tmp2 = null;
	Statement sqlstatement = null;
	try {
		conn_tmp2 = IO.getDBConnection();

		// 안전 : preparedStatement 객체를 이용해서 쿼리문 파라미터를 설정
		sqlstatement = conn_tmp2.prepareStatement("insert into users (status) values ('updated') where name=?");
		sqlstatement.setString(1, data);
		Boolean bResult = sqlstatement.execute();
	}
}


%>
</body>
</html>
