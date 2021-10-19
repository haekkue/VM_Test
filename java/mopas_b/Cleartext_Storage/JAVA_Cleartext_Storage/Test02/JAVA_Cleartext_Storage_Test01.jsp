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
        msg = "���Ե� ����� �����ϴ�.  �̸��� �ֹε�� ��ȣ�� Ȯ���� �ּ���.";
    }else{
        String title   = "[���θ�] ���������Դϴ�.";
        String content = "���̵� : "+ customer_id +"<br><br>"+"��й�ȣ : "+pwd;
        msg = sm.send(sender,"admin@serverpage.com", name, email, title, content);
        if(msg == null){
            msg = "��û�Ͻ� �����ּҷ� ���̵�� ��й�ȣ�� ���۵Ǿ����ϴ�.";
        }
    }
}catch(Exception e){

}finally{
    db.closeCon(con);
}
%>
<html>
<body topmargin=0 leftmargin=0 onload="upfix();">
<!-- ��Ŭ��� top //-->
<%@include file="/shop/top_inc.jsp"%>
<table width="824" border="0"> 
 <tr><td colspan="2" height="5"></td></tr>
 <tr>
  <td width="180" valign="top">
    <!--��Ŭ��� �޴� //-->
  <%@include file="/shop/left.jsp"%>
  <!--��Ŭ��� �޴� ��//-->
  </td>
  <td width="5">&nbsp;</td>
  <td width="644" valign="top">
   
   <script language="javascript">
        alert("<%=msg%>");
        location.href="/shop/start.jsp";
    </script>
</td>
  <!-- ������ ȭ�� //-->
  <td width=200>
  <%@include file="/shop/adv.jsp"%>
  </td>
 </tr>
 </table>
 
  <!-- ������ ������ �κ� //-->
  <%@include file="/shop/foot_inc.jsp"%>
  <!-- ������ �� //-->
 
</body>
</html>