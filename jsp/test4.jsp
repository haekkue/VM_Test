<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="java.sql.*" %>
<%
 String code = request.getParameter("code");
        //�ڵ尪�� ��û�� ������ ��ҽ��ϴ�.
 if(code==null)
  throw new Exception("������Ʈ �ڵ带 �Է� �ϼ���!");
 //�ڵ尪�� ���� ��� ����ó���� �˴ϴ�.
 
 Connection conn=null;
 //DB������ ���� Connection��ü
 Statement stmt = null;
 //������ ������ Statement��ü 
 try{
  String driver="com.mysql.jdbc.Driver";
  String url="jdbc:mysql://localhost:3306/webdb";
  String cid="root";
  String cpw="1111";
  //db������ ���� �⺻ ������  

  Class.forName(driver);
  //jdbc����̹��� �ε� �ϰ�
  conn = DriverManager.getConnection(url,cid,cpw); 
  //Ŀ�ؼ� ��ü�� ����̹��Ŵ����� ������ db������ �غ� �մϴ�. 
  if(conn==null)
   throw new Exception("�����Ϳ� ���� �� �� �����ϴ�.");
  //Ŀ�ؼ� ��ü�� ��� ������ ����ó��... 
  stmt = conn.createStatement();
  //������ ������ Statement��ü  
  ResultSet rs = 
   stmt.executeQuery("select * from goodsinfo where code="+code+";");
  //������� ���� rs ����...�������� �ڵ带 ���� ��ȸ  
  if(!rs.next())
       throw new Exception("������Ʈ �ڵ�("+code+") �� �ش��ϴ� ������ ����");
  //������� ���� ��� ����ó��
  
  String title = rs.getString("title");
  String writer = rs.getString("writer");
  int price = rs.getInt("price");
  //�� ������ ������ ���� ����ϴ�.
  
  request.setAttribute("CODE",code);
  request.setAttribute("TITLE", toUnicode(title));
  request.setAttribute("WRITER", toUnicode(writer));
  request.setAttribute("PRICE",new Integer(price));
  //��ƿ� ���� setAttribute�� ���� �������� ������ �غ� 
 }finally{
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
 //�����ִ� stmt�� conn ��ü�� ��� �ݾ� �ݴϴ�.

 RequestDispatcher dispatcher = request.getRequestDispatcher("EditForm.jsp");
   dispatcher.forward(request,response);
 //EditForm.jsp�� ��û���� ���� �Բ� �������� �̵� ��ŵ�ϴ�.
%>

<%!
private String toUnicode(String str){
 try{
 byte[] b = str.getBytes("ISO-8859-1");
 return new String(b);
}
catch(java.io.UnsupportedEncodingException uee){
 System.out.println(uee.getMessage());
 return null;
 } 
}
//�ְ� �޴� �������� �ѱ� ������ �����ϱ� ����ISO-8859-1 ���ڿ��� unicode ���ڿ��� ����
%>
