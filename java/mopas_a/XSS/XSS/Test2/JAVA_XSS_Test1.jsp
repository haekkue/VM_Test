<%@ page 
    contentType="text/html; charset=euc-kr"
    import="java.sql.*, com.util.Utility, com.db.DBCon"
%>
<%
DBCon db = new DBCon();
Utility ut = new Utility();
String ssid = ut.replaceNull((String)session.getValue("id"));


Connection con = null;
String sql         = null;
String sql2        = null; 
String prd_company = null;
String name        = null;
String contents    = null;
String price       = null;
String d_price     = null;
String html        = null;
String size_code1  = null;
String photo       = null;
String photo2      = null;
String photo3      = null;
String num         = null;
String reg_date    = null;
String point       = null;
String point_rate  = null;

String path        = ut.toKR(request.getParameter("path"));
String order       = ut.replaceNull(request.getParameter("order"));
String goods_id    = request.getParameter("goods_id");
String gcode_id    = request.getParameter("gcode_id");


sql = " select gcode_id                "
    + "      , name                    "
    + "      , prd_company             "
    + "      , contents                "
    + "      , price                   "
    + "      , size_code1              "
    + "      , num                     "
    + "      , photo                   "
    + "      , DATE_FORMAT(reg_date,'%Y-%m-%d')                "
    + "      , round(price*point_rate) "
    + "      , point_rate              "
    + "      , photo2                  "
    + "      , photo3                  "
    + "      , d_price                 "
    + "      , html                    "
    + "   from goods                   "
    + "  where goods_id = ?            ";

sql2 = " select code1, code2, code3, name from gcode where gcode_id = ?";

try{
    con = db.getCon();
     
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setString(1,goods_id);
    ResultSet rs = stmt.executeQuery();
    while(rs.next()){
        name        = ut.DBToChar(rs.getString(2));     
        prd_company = rs.getString(3);   
        contents    = rs.getString(4);    
        price       = rs.getString(5);
        size_code1  = ut.replaceNull(rs.getString(6));
        num         = rs.getString(7);     
        photo       = rs.getString(8); 
        reg_date    = rs.getString(9);   
        point       = rs.getString(10);  
        point_rate  = rs.getString(11);
        photo2      = rs.getString(12);
        photo3      = rs.getString(13);  
        d_price     = rs.getString(14);  
        html        = rs.getString(15);  
        if(html.equals("0")){
            contents = ut.DBToChar(contents);
        }
    }
    
    
 
%>
<html>
<head>
<title>상품내역</title>
<link rel="stylesheet" href="../css/basic.css">
<script language="javascript">

function del(x,y){
    window.open("del.jsp?idx="+x+"&b_gubun="+y+"","","width=200,height=80,top=100,left=100");
}

function download(x,y){
    location.href="download.jsp?filename="+x+"&path="+y;
}

function regUnit(goods_id){
    window.open("regUnit.jsp?goods_id="+goods_id,"","width=420, height=150, left=100, top=100");
}
function realView(x){
    window.open("realImage.jsp?path="+x,"","width=630,height=500,left=0,top=0,resizable=true");
}
function delRelate(relate_id){
    sure = confirm("삭제하시겠습니까?");
    if(sure){
        window.open("delRelate.jsp?relate_id="+relate_id,"","width=100,height=100,left=100,top=4100");
    }
}

function delGoods(goods_id){
    sure = confirm("삭제하시겠습니까?");
    if(sure){
        window.open("delGoods.jsp?goods_id="+goods_id+"&gcode_id=<%=gcode_id%>&path=<%=path%>&photo=<%=photo%>","","width=100,height=100,left=100,top=4100");
    }
}
function goods_up(gcode_id,path){
    window.open("goods_up.jsp?gcode_id="+gcode_id,"","width=800,height=600,left=100,top=100");
}
function editGoods(goods_id){
    window.open("editGoods.jsp?goods_id="+goods_id+"&path=<%=path%>","","width=930,height=600,left=100,top=100");
}
function unitDetail(goods_id){
    window.open("re_goodsDetail.jsp?goods_id="+goods_id,"","width=660, height=600,left=0,top=0");

}
function goList(){
    document.myform.action="goodslist.jsp";
    document.myform.submit();
    
}

//장바구니에 담기

function temp_sale(){
    if(document.myform.r_num.value == ""){
        alert("상품수량을 선택하세요.");
        return;
    }
    document.myform.price.value="<%=price%>";
    document.myform.action = "cart_ok.jsp";
    document.myform.submit();
}
// 관심 품목에 넣기
function wishList(){
    document.myform.price.value="<%=price%>";
    document.myform.action = "wish_ok.jsp";
    document.myform.submit();
}

function goOrder(){
    if(document.myform.r_num.value == ""){
        alert("상품수량을 선택하세요.");
        return;
    }
    window.open("addCart.jsp?goods_id=<%=goods_id%>&r_num="+document.myform.r_num.value+"&size_code2="+document.myform.size_code2.value+"&price=<%=price%>","","width=10,height=10,left=4000,top=10");
    document.myform.action= "order.jsp";
    document.myform.submit();
}

//이미지 추가
function addImage(goods_id){
    window.open("addImage.jsp?goods_id="+goods_id,"","width=400, height=150, left=100, top=100");
}
function editImage(photos_id){
    window.open("editImage.jsp?photos_id="+photos_id,"","width=400, height=150, left=100, top=100");
}
function delImage(photos_id,filename){
    sure=confirm("이미지를 삭제하시겠습니까?");
    if(sure){
        window.open("delImage.jsp?photos_id="+photos_id+"&filename="+filename,"","width=400, height=150, left=4100, top=100");
    }
}

//상품평 삭제
function delComment(x,y){
      if( document.myform.tnum.value=="1"){
            t_idx = document.myform.t_idx.value;
      }else{
            t_idx = document.myform.t_idx[x].value;
      }
      window.open("delComment.jsp?idx="+t_idx+"&password="+y,"","width=200,height=80,top=100,left=100");
      
}

//상품평 수정
function editComment(x){
      if( document.myform.tnum.value=="1"){
            t_idx = document.myform.t_idx.value;
            tail  = document.myform.content.value;
      }else{
            t_idx = document.myform.t_idx[x].value;
            tail  = document.myform.content[x].value;
      }
      window.open("editComment.jsp?idx="+t_idx+"&tail="+tail+"","","width=600,height=120,top=100,left=100");
}

//상품평 입력
function writeComment(){
       x = document.myform.tail.value;
       y = document.myform.pwd.value;
       z = document.myform.user_name.value;
       if(x == "" || y == ""){
             alert("꼬리말 또는 비밀번호를 입력해 주세요!");
             return;
       }
       window.open("writeComment.jsp?password="+y+"&name="+z+"&goods_id=<%=goods_id%>&content="+x+"","","width=200,height=80,top=4100,left=100");
}

//AJAX이용해서 상품 규격별 수량 셋팅
function getNums(){
     var go = "/shop/sale/getSizeNums.jsp?goods_id=<%=goods_id%>&code1=<%=size_code1%>&code2="+document.myform.size_code2.value;
     setAJAX(go,'getNumsResult');

}
//AJAX 결과 처리 함수..
function getNumsResult(newXML){
     var select = newXML.getElementsByTagName("login")[0];
     var items = select.getElementsByTagName("result");
     
     var f1  = document.myform;
     var num = f1.r_num.options.length;
     //첫번째꺼 빼고 제거
     for(var i=1;i<num;i++){
          f1.r_num.options[1]= null;
     }
     for(var i=0;i< Number(items[0].getAttribute("value"));i++){
          f1.r_num.options[i+1] = new Option(i+1,i+1);
     }
     
}

//-->
</script>
</head>
<body topmargin=0 leftmargin=0 onload="upfix();">
<!-- 인클루드 top //-->
<%@include file="../top_inc.jsp"%>
<table border="0" width="824"> 
 <tr><td colspan="2" height="5"></td></tr>
 <tr>
  <td width="180" valign="top">
  <!--인클루드 메뉴 카테고리 //-->
  <%@include file="/shop/left.jsp"%>
  </td>
  <td width="5">&nbsp;</td>
  <td width="644" valign="top">
  <form name="myform" method="post" action="">
  <!-- 메인화면 //-->
   <table class="table_basic">
    <tr>
     <td height=20 class="td_path"><%=path%>
      
       <input type="hidden" name="filename">
       <input type="hidden" name="path">
       <input type="hidden" name="gcode_id"   value="<%=gcode_id%>">
       <input type="hidden" name="goods_id"   value="<%=goods_id%>">
       <input type="hidden" name="ck"         value="0">
       <input type="hidden" name="photo"      value="<%=photo%>">
       <input type="hidden" name="photo2"     value="<%=photo2%>">
       <input type="hidden" name="photo3"     value="<%=photo3%>">
       <input type="hidden" name="name"       value="<%=name%>">
       <input type="hidden" name="price"      value="<%=price%>">
       <input type="hidden" name="point"      value="<%=point%>">
       <input type="hidden" name="point_rate" value="<%=point_rate%>">
       <input type="hidden" name="size_code1" value="<%=size_code1%>">
       <input type="hidden" name="num"        value="<%=num%>">
     </td>
    </tr>
    
   </table>
   
   <table class="table_basic">
    <tr>
       <td colspan="2" class="td_dot"></td>
    </tr>
    <tr> 
     <td width="300">
      <a href="javascript:realView('../images/goods/<%=photo3%>');">
      <img src="../images/goods/<%=photo2%>" width="280" border="0"></a>
      <a href="javascript:realView('../images/goods/<%=photo3%>');">
      <img src="../images/icon_large.gif" border=0></a>
      
      
      
     </td>
     <td valign="top">
       <table border="0" width="320"  cellspacing="0">
        <tr>
          <td class="td_title" colspan="2"><%=name%></td>
        </tr>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
        <tr> 
         <td class="td_title_l">상품코드</td>
         <td class="td_content" ><%=goods_id%></td>
        </tr>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
        <tr> 
         <td class="td_title_l">제조사</td>
         <td class="td_content" ><%=ut.replaceNull(prd_company)%></td>
        </tr>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
        <tr> 
         <td class="td_title_l">판매가격</td>
         <td class="td_content" ><strike><%=ut.getComma(d_price)%>원</strike></td>
        </tr>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
         <tr> 
         <td class="td_title_l">할인가격</td>
         <td class="td_price" ><%=ut.getComma(price)%>원</td>
        </tr>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
        <tr> 
         <td class="td_title_l">적립금</td>
         <td class="td_content" ><%=ut.getComma(point)%>원</td>
        </tr>
        <%if(!size_code1.equals("00")){%>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
        <tr>
         <td class="td_title_l">규격</td> 
         <td class="td_content" >
          <select name="size_code2" onchange="getNums();">
            <option value=""> ---- </option>
            
            <%
                sql = " select idx, code1, code2, name, r_num,s_num from goods_num "
                    + "  where goods_id = ? and r_num-s_num > 0 ";
                
                    PreparedStatement stmt6 = con.prepareStatement(sql);
                    stmt6.setString(1,goods_id);
                    ResultSet rs6 = stmt6.executeQuery();
                    while(rs6.next()){%>
                    <option value="<%=rs6.getString(3)%>" >
                             <%=rs6.getString(4)%></option>
                    <%
                    }
                    rs6.close();
                    stmt6.close();
               
            
            %>
         </select>      
         </td>
        </tr> 
        <%}else{%>
        <input type="hidden" name="size_code2" value="">
        <%}%>
        <tr>
         <td colspan=2 class="td_dot"></td>
        </tr>
        <tr> 
         <td class="td_title_l">수량</td>
         <td class="td_content" >
           <%if(size_code1.equals("00")){
             if(num.equals("0")){%>
             품절
             <input type="hidden" name="r_num" value="">
           <%}else{%>
             <select name="r_num">
             <% for(int j=1;j<=Integer.parseInt(num);j++){%>
             <option value="<%=j%>"><%=j%></option>
             <%}%>
             </select>
           <%}%>
           <%}else{%>
           <select name="r_num">
            <option value="">--</option>  
           </select>
           <%}%>
         </td>
        </tr>
        <tr>
         <td height=1 colspan=2 background="../images/dot_line.gif"></td>
        </tr>
       </table> 
       
      </td>  
     </tr>
     <tr>
      <td></td>
      <td class="td_title_r">
       
       <input type="button" value="주 문"      onclick="goOrder();"   class="btn1">
       <input type="button" value="장바구니"   onclick="temp_sale();" class="btn1">
       <%if(!ssid.equals("")){%> 
       <input type="button" value="관심품목" onclick="wishList();" class="btn1">
       <%}%>
       <input type="button" value="상품목록"   onclick="history.back();" class="btn1">
      </td>
     </tr> 
     <tr>
      <td colspan=2 class="td_line1"></td>
     </tr>
    </table>
   
   <% if(ssid.equals("admin")){%>
   <table class="table_basic">
    <tr>
     <td class="td_title_r">
      <input type="button" value="상품수정"   onclick="editGoods('<%=goods_id%>');" class="btn1">
      <input type="button" value="상품삭제"   onclick="delGoods('<%=goods_id%>');"  class="btn1">
      <input type="button" value="관련상품등록"   onclick="regUnit('<%=goods_id%>');"   class="btn1"> 
      <input type="button" value="이미지추가" onclick="addImage('<%=goods_id%>');"  class="btn1"> 
     </td>
    </tr>
   </table> 
   <%}%>
   
   <br>
   <font class="txt2">상세정보</font>
   
   <table class="table_basic">
     <tr>
      <td class="td_content" colspan="2">
      <%
      String p_sql = " select title, filename, photos_id from photos where goods_id = ? order by idx ";
      PreparedStatement stmt3 = con.prepareStatement(p_sql);
      stmt3.setString(1,goods_id);
      ResultSet rs3 = stmt3.executeQuery();
      while(rs3.next()){%>
          <br>
          <img src="../images/goods/<%=rs3.getString(2)%>" border=0><br><br>
          <%=rs3.getString(1)%>
          <% if(ssid.equals("admin")){%>
          <br>
          <input type="button" value="수정" onclick="editImage('<%=rs3.getString(3)%>');">
          <input type="button" value="삭제" onclick="delImage('<%=rs3.getString(3)%>','<%=rs3.getString(2)%>');">
          <%}%>
          <br>
      <%}%>
      </td>
     </tr>
     <tr> 
      <td class="td_content" colspan="2">
       <br><%=contents%>
       <br>
      </td>
     </tr>
     <tr>
       <td colspan=4 class="td_line1"></td>
    </tr>
     <tr>
       <td><font class="txt2"> * 관련상품</font></td>
     </tr>
   <%
    String t_sql = " select c.photo, c.name, c.price, b.num, b.relate_id, c.goods_id  "
                  + "   from relate b, goods c               "
                  + "  where b.goods_id = ?     " 
                  + "    and c.goods_id = b.unit_id          ";              
                  
     //out.println(t_sql);             
     PreparedStatement stmt2 = con.prepareStatement(t_sql);
     stmt2.setString(1,goods_id);
     ResultSet rs2 = stmt2.executeQuery();
     while(rs2.next()){%>
    <tr>
       <td colspan=4 class="td_dot"></td>
    </tr>
     <tr>
      <td class="td_content">
        <a href="javascript:unitDetail('<%=rs2.getString(6)%>');">
         <img src="../images/goods/<%=rs2.getString(1)%>" width="50" border="0">
        </a> 
      </td>
      <td class="td_content"><%=rs2.getString(2)%></td>
      <td class="td_content_r"><%=ut.getComma(rs2.getString(3))%>원</td>
      <td class="td_content_c">
      <% if(ssid.equals("admin")){%>
        <input type="button" value="삭제" onclick="delRelate('<%=rs2.getString(5)%>');"  class="btn1">
      <%}%>  
        <input type="button" value="보기" onclick="unitDetail('<%=rs2.getString(6)%>');" class="btn1">  
      </td>
     </tr> 		
     <%}%>
   </table>
   
 <!-- 상품평 쓰기 //-->

  <table class="table_basic">
    <tr>
       <td colspan=4 height="10"></td>
    </tr>
      <tr>
       <td colspan=4 class="td_line1"></td>
    </tr>
    <tr>
       <td colspan=4><font class="txt2"> * 상품평</font></td>
     </tr>
    <tr>
       <td colspan=4 class="td_dot"></td>
    </tr>
  <%
  String tail_sql = "select name,content, w_date, idx, password from goods_comments where goods_id =? order by idx";
  PreparedStatement stmt4 = con.prepareStatement(tail_sql);
  stmt4.setString(1,goods_id);
  ResultSet rs4 = stmt4.executeQuery();
  int tnum = 0;
  while(rs4.next()){%>
  <tr>
   <td class="td4"><font class="txt4"><%=rs4.getString(1)%></td>
   <td width="350" class="td4"><font class="txt4"><textarea rows=2 cols=55 style="border:0px;" readonly><%=rs4.getString(2)%></textarea></font></td>
   <td class="td3"><font class="txt4">[<%=rs4.getString(3)%>]</td>
   <td class="td4"><font class="txt4">
    <input type="hidden" name="t_idx" value="<%=rs4.getString(4)%>">
    <input type="hidden" name="content" value="<%=rs4.getString(2)%>">
    <input type="button" value="수정" onclick="editComment(<%=tnum%>);">
    <% if(ssid.equals("admin")){%>
      <input type="button" value="삭제" onclick="delComment(<%=tnum%>,'<%=rs4.getString(5)%>');">
    <%}else{%>
      <input type="button" value="삭제" onclick="delComment(<%=tnum%>,'');">
    <%}%>
   </td>
  </tr>
  <tr>
   <td colspan=4 class="td_dot"></td>
  </tr> 		
  <%
  tnum++;
  }%>
  <tr>
  <td width=100 class="td4"><font class="txt4">이름<br><input type="text" name="user_name" value="" size="8" maxlength="8"></td>
  <td class="td4"><font class="txt4"><textarea name="tail" rows=3 cols=55></textarea></td>
  <td width=120 class="td4"><font class="txt4">password <br><input type="password" name="pwd" size="10" maxlength=10></td>
  <td width=150 class="td4"><font class="txt4"><input type="button" value="저장" onclick="writeComment();"></td>		
 </tr>
</table>
<input type="hidden" name="tnum" value="<%=tnum%>"> 
<!-- 상품평 쓰기 끝//-->
</form>   
   <!-- 메인화면 끝 //-->
  
  </td>
  <!-- 오른쪽 화면 //-->
  <td width=200>
  <%@include file="/shop/adv.jsp"%>
  </td>
 </tr>
</table>  
<!-- 마지막 꼬리말 부분 //-->
<%@include file="/shop/foot_inc.jsp"%>
 
</body>
</html>
<%

//상품 조회 로그 남기기
String sql9 = " insert into goods_log (goods_id, name, ip, reg_date) values(?,?,?,sysdate())";
PreparedStatement stmt9 = con.prepareStatement(sql9);
stmt9.setString(1,goods_id);
stmt9.setString(2,name);
stmt9.setString(3,request.getRemoteAddr());
stmt9.executeUpdate();
stmt9.close();
//상품조회 로그 남기기 끝...

}catch(Exception e){
    out.println(e);
}finally{
    db.closeCon(con);
}
%>
