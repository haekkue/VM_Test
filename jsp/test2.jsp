<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="java.sql.*" %>
<%
     String code = request.getParameter("code");
     String title = request.getParameter("title");
     String writer = request.getParameter("writer");
     String price = request.getParameter("price");
     //EditForm.jsp���� �޾ƿ� ��
     
     if(code==null || title==null || writer==null || price==null)
      throw new Exception("������ �����Ͱ� �ֽ��ϴ�.");
     //���Էµ� �����Ͱ� �ִ��� Ȯ���Ͽ� ����ó��
     
     Connection conn = null;
     Statement stmt = null;
     try{
     String url="jdbc:mysql://localhost:3306/webdb";
   String driver="com.mysql.jdbc.Driver";
   String cid="root";
   String cpw="1111";
   
   Class.forName(driver);
   conn = DriverManager.getConnection(url,cid,cpw); 
   
   if(conn==null)
   throw new Exception("�����Ϳ� ���� �� �� �����ϴ�."); 
   stmt = conn.createStatement();
      //������ ���� �κ��� ������ ���� �մϴ�. 
   
   String command = String.format("update goodsinfo set title = '%s', writer='%s', price='%s' where code = '%s';", title, writer, price, code);
  //���� ������
   
   int rowNum = stmt.executeUpdate(command);
  //���� �������� �����ϸ� rowNum ������ ��� �˴ϴ�.
  //���⼭executeUpdate�� ������ �Է� ������
  //executeQuery�� ������ �˻��� ���Ǵ� ��ɾ� ���� ����� �θ� �����ϴ�.
   if(rowNum<1)
    throw new Exception("�����͸� DB�� ���� �� �� �����ϴ�.");      
     }
     finally{
      try{
       stmt.close();
      }
      catch(Exception ignored){       
      }
      try{
       conn.close();
      }
      catch(Exception ignored){       
      }
     }
     response.sendRedirect("UpdateResult.jsp?code=" + code);
     //�� ���� ������ UpdateResult.jsp?code= ���Ŀ� �ڵ��ȣ�� �ٿ��� ������ �̵� ��ŵ�ϴ�.
%>

