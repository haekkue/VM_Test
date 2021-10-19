<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="java.sql.*" %>
<%
 String code = request.getParameter("code");
        //코드값을 요청해 변수에 담았습니다.
 if(code==null)
  throw new Exception("프로젝트 코드를 입력 하세요!");
 //코드값이 없을 경우 예외처리가 됩니다.
 
 Connection conn=null;
 //DB연결을 위한 Connection객체
 Statement stmt = null;
 //쿼리를 실행할 Statement객체 
 try{
  String driver="com.mysql.jdbc.Driver";
  String url="jdbc:mysql://localhost:3306/webdb";
  String cid="root";
  String cpw="1111";
  //db접속을 위한 기본 변수들  

  Class.forName(driver);
  //jdbc드라이버를 로드 하고
  conn = DriverManager.getConnection(url,cid,cpw); 
  //커넥션 객체에 드라이버매니져를 대입해 db연결을 준비 합니다. 
  if(conn==null)
   throw new Exception("데이터에 연결 할 수 없습니다.");
  //커넥션 객체가 비어 있으면 예외처리... 
  stmt = conn.createStatement();
  //쿼리를 실행할 Statement객체  
  ResultSet rs = 
   stmt.executeQuery("select * from goodsinfo where code="+code+";");
  //결과값을 담을 rs 변수...쿼리문은 코드를 통한 조회  
  if(!rs.next())
       throw new Exception("프로젝트 코드("+code+") 에 해당하는 데이터 없음");
  //결과값이 없을 경우 예외처리
  
  String title = rs.getString("title");
  String writer = rs.getString("writer");
  int price = rs.getInt("price");
  //각 변수에 가져온 값을 담습니다.
  
  request.setAttribute("CODE",code);
  request.setAttribute("TITLE", toUnicode(title));
  request.setAttribute("WRITER", toUnicode(writer));
  request.setAttribute("PRICE",new Integer(price));
  //담아온 값을 setAttribute를 통해 전달해줄 값으로 준비 
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
 //열려있는 stmt와 conn 객체를 모두 닫아 줍니다.

 RequestDispatcher dispatcher = request.getRequestDispatcher("EditForm.jsp");
   dispatcher.forward(request,response);
 //EditForm.jsp로 요청받은 값과 함께 페이지를 이동 시킵니다.
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
//주고 받는 데이터중 한글 깨짐을 방지하기 위해ISO-8859-1 문자열을 unicode 문자열로 변경
%>
