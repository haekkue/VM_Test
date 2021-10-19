<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="java.sql.*" %>
<%
     String code = request.getParameter("code");
     String title = request.getParameter("title");
     String writer = request.getParameter("writer");
     String price = request.getParameter("price");
     //EditForm.jsp에서 받아온 값
     
     if(code==null || title==null || writer==null || price==null)
      throw new Exception("누락된 데이터가 있습니다.");
     //미입력된 데이터가 있는지 확인하여 예외처리
     
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
   throw new Exception("데이터에 연결 할 수 없습니다."); 
   stmt = conn.createStatement();
      //데이터 접속 부분은 설명을 생략 합니다. 
   
   String command = String.format("update goodsinfo set title = '%s', writer='%s', price='%s' where code = '%s';", title, writer, price, code);
  //수정 쿼리문
   
   int rowNum = stmt.executeUpdate(command);
  //수정 쿼리문을 실행하면 rowNum 변수에 기록 됩니다.
  //여기서executeUpdate는 데이터 입력 수정에
  //executeQuery는 데이터 검색에 사용되는 명령어 임을 기억해 두면 좋습니다.
   if(rowNum<1)
    throw new Exception("데이터를 DB에 연결 할 수 없습니다.");      
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
     //이 모든게 끝나면 UpdateResult.jsp?code= 형식에 코드번호를 붙여서 페이지 이동 시킵니다.
%>

