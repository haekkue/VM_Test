<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
<head>
<title>테이블의 레코드를 화면에 표시하는 예제</title>
</head>
<body>
 <h2>테이블의 레코드를 화면에 표시하는 예제</h2>
 <table border="1" width="550" cellspacing="1">
  <tr bgcolor="black">
   <th width="100"><font color="white">아이디</font></td>
   <th width="100"><font color="white">패스워드</font></td>
   <th width="100"><font color="white">이름</font></td>
   <th width="250"><font color="white">가입일자</font></td>
  </tr>
  <%
   String id = session.getValue("id");
   
   Connection conn   = null;
   PreparedStatement pstmt = null;
   ResultSet rs    = null;
   
   try{
    String jdbcUrl  = "jdbc:oracle:thin:@localhost:1521:orcl";
    String dbId  = "java";
    String dbPass = "java";
    
    Class.forName("oracle.jdbc.driver.OracleDriver");
    conn = DriverManager.getConnection(jdbcUrl,dbId,dbPass);
    
    String sql = "select * from member1";
    pstmt = conn.prepareStatement(sql);
    rs = pstmt.executeQuery();
    
    while(rs.next()){
     String id   = rs.getString("id");
     String passwd  = rs.getString("passwd");
     String name  = rs.getString("name");
     Timestamp register = rs.getTimestamp("reg_date");
     }
    }catch(Exception e){
    	e.printStackTrace();
    }finally{
    	if(rs!=null)try{rs.close();}catch(SQLException sqle){}
    	if(pstmt!=null)try{pstmt.close();}catch(SQLException sqle){}
    	if(conn!=null)try{conn.close();}catch(SQLException sqle){}
   	}
  %>
  <tr>
   <td width="100"><%=id %></td>
   <td width="100"><%=passwd %></td>
   <td width="100"><%=name %></td>
   <td width="250"><%=register.toString() %></td>
  </tr>
  <%
    
  %>
 </table>
 <p><a href="UpdateTestForm.jsp">▶ 수정</a>
</body>
</html> 
