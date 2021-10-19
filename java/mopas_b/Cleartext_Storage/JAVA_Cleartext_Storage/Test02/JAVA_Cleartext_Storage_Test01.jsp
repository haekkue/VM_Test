<%@ page
    contentType="text/html; charset=euc-kr"
    import="java.sql.*
          , com.db.DBCon, com.util.Utility, com.util.sendMail
          , java.util.*, javax.mail.*, javax.mail.internet.*"
%>
<%
DBCon db       = new DBCon();
Utility ut     = new Utility();
sendMail sm    = new sendMail();
String name    = request.getParameter("name");
String ssn1    = request.getParameter("ssn1");
String ssn2    = request.getParameter("ssn2");
String ssn     = ssn1+ssn2;
String email   = request.getParameter("email");
Connection con = null;
String customer_id = null;
String pwd = null;
String msg = null;
out.println(name+ssn);
String sender = request.getParameter("sender");;

try{
    con = db.getCon();             
    PreparedStatement stmt = con.prepareStatement("select customer_id, pwd from customer where name = ? and ssn = ?");
    stmt.setString(1,ut.toKR(name));
    stmt.setString(2,ssn);
    ResultSet rs = stmt.executeQuery();
    while(rs.next()){
        customer_id = rs.getString(1); 
        pwd         = rs.getString(2);
    }
    if(customer_id == null){
        msg = "가입된 기록이 없습니다.  이름과 주민등록 번호를 확인해 주세요.";
    }else{
        String title   = "[쇼핑몰] 가입정보입니다.";
        String content = "아이디 : "+ customer_id +"<br><br>"+"비밀번호 : "+pwd;
        msg = sm.send(sender,"admin@serverpage.com", name, email, title, content);
        if(msg == null){
            msg = "요청하신 메일주소로 아이디와 비밀번호가 전송되었습니다.";
        }
    }
}catch(Exception e){

}finally{
    db.closeCon(con);
}
%>
<html>
<body topmargin=0 leftmargin=0 onload="upfix();">
<!-- 인클루드 top //-->
<%@include file="/shop/top_inc.jsp"%>
<table width="824" border="0"> 
 <tr><td colspan="2" height="5"></td></tr>
 <tr>
  <td width="180" valign="top">
    <!--인클루드 메뉴 //-->
  <%@include file="/shop/left.jsp"%>
  <!--인클루드 메뉴 끝//-->
  </td>
  <td width="5">&nbsp;</td>
  <td width="644" valign="top">
   
   <script language="javascript">
        alert("<%=msg%>");
        location.href="/shop/start.jsp";
    </script>
</td>
  <!-- 오른쪽 화면 //-->
  <td width=200>
  <%@include file="/shop/adv.jsp"%>
  </td>
 </tr>
 </table>
 
  <!-- 마지막 꼬리말 부분 //-->
  <%@include file="/shop/foot_inc.jsp"%>
  <!-- 꼬리말 끝 //-->
 
</body>
</html>