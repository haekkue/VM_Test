<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
<head>
<title>���̺��� ���ڵ带 ȭ�鿡 ǥ���ϴ� ����</title>
</head>
<body>
 <h2>���̺��� ���ڵ带 ȭ�鿡 ǥ���ϴ� ����</h2>
 <table border="1" width="550" cellspacing="1">
  <tr bgcolor="black">
   <th width="100"><font color="white">���̵�</font></td>
   <th width="100"><font color="white">�н�����</font></td>
   <th width="100"><font color="white">�̸�</font></td>
   <th width="250"><font color="white">��������</font></td>
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
 <p><a href="UpdateTestForm.jsp">�� ����</a>
</body>
</html> 
