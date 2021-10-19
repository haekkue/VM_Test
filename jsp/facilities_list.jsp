<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="imagetoolbar" content="no" />
<title>사이버 홍보실-KRRI 미디어-포토갤러리-연구시설</title>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath() %>/krri/common/styles/style.css" />
<!--[if IE 6]>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/krri/common/styles/ie6.css" media="all" />
<![endif]-->
<!--[if IE 7]>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/krri/common/styles/ie7.css" media="all" />
<![endif]-->
<!--[if IE 8]>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/krri/common/styles/ie8.css" media="all" />
<![endif]-->
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/jquery-1.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/left_menu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/top_menu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/scrollquick.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/scrollnews.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/tabpaneflash.js"></script>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	var lm=new leftmenu('leftmenu',1,3);
	var tm=new topmenu('headmenu',3,1);
	var scquick=new scrollquick('quick_menu',210);
	$("div.top_foot").Scroll({line:1,speed:400,timer:3000,up:".left_btn",down:".right_btn",autoplay:'',autostop:''});
    $("#maintab").tabonflash({total:3,tabname:'tab',tabpagename:'tabp',onstr:'',offstr:'',hoverclass:'',play:'',playbtn:'',stopbtn:'',nextbtn:'',prebtn:''});
});
function on_search(){
			var key = document.getElementById("keyword").value;
			if(key==null || key==""){
				alert("검색어를 입력하여 주십시요!");
				return false;
			}else{
				document.getElementById('searchForm').action ="webCommInfoAction!list.do?c=16";
				document.getElementById('searchForm').submit();
			}
		}
//]]>
</script>
</head>
<body id="bg_frame03">
<!--header start-->
<%@ include file="../common/include/header.jsp" %>
	<!--header end-->
	<div id="mid">
		<!--left start-->
		<%@ include file="../common/include/left03.jsp" %>
		<!--left end-->
		<%
				com.train.util.Pages pager=(com.train.util.Pages)request.getAttribute("pager");
			%>
			 <pg:pager items="<%=pager.getItems() %>" maxPageItems="<%=pager.getMaxPageItems() %>"
								  maxIndexPages="<%=pager.getMaxIndexPages() %>" isOffset="true"
								  index="center" url="train_admin/webCommInfoAction!list.do"
								  export="offset, currentPageNumber=pageNumber" scope="request">
			<%
				int number = pager.getItems().intValue()-(currentPageNumber-1)*pager.getMaxPageItems().intValue();
			 %>
		<!--contents start-->
		<div id="contents">
			<div id="path"><a href="/krri/homePageAction!list.do"><img src="<%=request.getContextPath() %>/krri/common/images/icons/ico_h03.gif" alt="home" /></a>&gt;<a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=15&amp;clear=1">사이버 홍보실</a>&gt;<a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=15&amp;clear=1">KRRI 미디어</a>&gt;<a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=17&amp;clear=1">포토갤러리</a>&gt;<span>연구시설</span></div>
			<%@ include file="../common/include/subvisual_03.jsp" %>
			<div id="con">
				<h3><img src="<%=request.getContextPath() %>/krri/common/images/titles/tit_h3_facilities_list.gif" alt="포토 캘러리" /></h3>
				<ul class="fac_viewtab">
					<li class="a1"><a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=17&amp;clear=1">주요행사</a></li>
					<li class="a2on"><a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=16&amp;clear=1">연구시설</a></li>
				</ul>
				<div class="facilities_box">
					
					<s:if test="#request.list.size()!=0">
					<ul>
					<s:iterator value="#request.list"  id="commOne">
						<li class="li1">
						<s:if test="#commOne.fileBeanList.size>0">
						<s:iterator value="#commOne.fileBeanList">
						<a href="webCommInfoAction!view.do?id=<s:property value="COMM_NO"/>&cla=<s:property value="COMM_CLASS"/>"><img src="<%=request.getContextPath()%>/<s:property value="SYSFILE_NAME"/>" alt="<s:property value="FILE_NAME"/>" style="width:150px;height:98px;"  /></a>
						</s:iterator>	
						</s:if>
						<s:else>
							<img src="" style="width:150px;height:98px;"  />
						</s:else>
							<dl>
								<dt><strong><s:date name="REG_DATE" format="yyyy.MM.dd"/></strong></dt>
								<dd><a href="webCommInfoAction!view.do?id=<s:property value="COMM_NO"/>&cla=<s:property value="COMM_CLASS"/>">
								<s:if test="%{TITLE.length()>15}">
										<s:property value="TITLE.substring(0,15)+'...'"/>
									</s:if> 
									<s:else>
									<s:property value="TITLE"/>&nbsp;
									</s:else></a></dd>
							</dl>
						</li>	
					</s:iterator>	
					</ul>
					</s:if>
							<s:else>
								<p style="text-align:center;padding:30px 0 10px 0;">등록된 게시물이 없습니다.</p>
							</s:else>
					
				</div>
				<div class="btn_notlist">
					<ul>
						<pg:param name="c" value="16"/>
					<pg:first>
						<li class="li01"><a href="<s:property value="#attr.pageUrl"/>"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn_cyber_list01.gif" alt="맨처음" /></a></li>
					</pg:first>
					<pg:skip pages="<%= -1 %>" ifnull="true">
								 <s:if test="#attr.pageUrl==null">	
						<li class="li01"><a href="#"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn_cyber_list02.gif" alt="이전" /></a></li>
					</s:if>
					<s:else>
					<li class="li01"><a href="<s:property value="#attr.pageUrl"/>"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn_cyber_list02.gif" alt="이전" /></a></li>	
					</s:else>
					</pg:skip>																												
					<pg:pages>
				    <s:if test="#attr.pageNumber==#attr.currentPageNumber">	
						<li><strong><s:property value="#attr.pageNumber"/></strong></li>
					</s:if>
					<s:else>	
						<li><a href="<s:property value="#attr.pageUrl"/>"><s:property value="#attr.pageNumber"/></a></li>
					</s:else>
					</pg:pages>
					<pg:skip pages="<%= 1 %>" ifnull="true">
					  <s:if test="#attr.pageUrl==null">	
						<li class="li02"><a href="#"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn_cyber_list03.gif" alt="다음" /></a></li>
					</s:if>
				    <s:else>
				    <li class="li02"><a href="<s:property value="#attr.pageUrl"/>"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn_cyber_list03.gif" alt="다음" /></a></li>	
					</s:else>
					</pg:skip>															
					<pg:last>	
						<li class="end"><a href="<s:property value="#attr.pageUrl"/>"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn_cyber_list04.gif" alt="맨끝" /></a></li>
					</pg:last>
					</ul>
				</div>
				<div class="serch_notlist">
					<s:form id="searchForm" name="searchForm" method="post" theme="simple" >
						<fieldset>
							<legend>검색</legend>
							<label for="notlist001"><img src="<%=request.getContextPath() %>/krri/images/notification/img_employ01_list01.gif" alt="Search" /></label>
							<s:select list="#{1:'전체',2:'제목',3:'내용',4:'작성자'}" name="seachType"></s:select>
							<input type="text" value="" id="keyword" name="keyword" title="검색어입력" class="input01" />
							<input type="image" onclick="on_search(); return false;" src="<%=request.getContextPath() %>/krri/images/notification/btn_employ01_list06.gif" alt="검색" />
						</fieldset>
					</s:form>
				</div>
			</div>
		</div>
		<!--contents end-->
	</pg:pager>	
		<!--quick_menu start-->
		<%@ include file="../common/include/quick_menu.jsp" %>
		<!--quick_menu end-->
	</div>
	<!--footer start-->
	<%@ include file="../common/include/footer.jsp" %>
	<!--footer end-->
	<script type="text/javascript">  
 <!-- 
 	if(document.addEventListener){
		 document.addEventListener("keypress",fireFoxHandler, true); 
	}else{  
		document.attachEvent("onkeypress",ieHandler);
	 }  
	 function fireFoxHandler(evt){ 
	  //alert("firefox");  
	  	if(evt.keyCode==13){ 
			return on_search();
		}  
	 }  
	 function ieHandler(evt){ 
	 	 //alert("IE"); 
		 if(evt.keyCode==13){
			return on_search();
		 }  
	}  
 //-->  
 </script>
</div>
</body>
</html>