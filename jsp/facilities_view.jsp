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
<script type="text/javascript" src="<%=request.getContextPath() %>/krri/common/scripts/horizontal.js"></script>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	var lm=new leftmenu('leftmenu',1,3);
	var tm=new topmenu('headmenu',3,1);
	var scquick=new scrollquick('quick_menu',210);
	$("div.top_foot").Scroll({line:1,speed:400,timer:3000,up:".left_btn",down:".right_btn",autoplay:'',autostop:''});
    $("#maintab").tabonflash({total:3,tabname:'tab',tabpagename:'tabp',onstr:'',offstr:'',hoverclass:'',play:'',playbtn:'',stopbtn:'',nextbtn:'',prebtn:''});
	// var playbanner=new intplaybanner('.box2_meview','.btn01','.btn02','','');
	
});
function move(id,v){
		$("#dong").attr("src","<%=request.getContextPath() %>/"+id);
		$("#pho"+v).addClass("on");
	} 
	function removeClass(v){
		$("#pho"+v).removeClass("on");
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
		<!--contents start-->
		<div id="contents">
			<div id="path"><a href="/krri/homePageAction!list.do"><img src="<%=request.getContextPath() %>/krri/common/images/icons/ico_h03.gif" alt="home" /></a>&gt;<a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=15&amp;clear=1">사이버 홍보실</a>&gt;<a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=15&amp;clear=1">KRRI 미디어</a>&gt;<a href="<%=request.getContextPath() %>/krri/webCommInfoAction!list.do?c=17&amp;clear=1">포토갤러리</a>&gt;<span>연구시설</span></div>
			<%@ include file="../common/include/subvisual_03.jsp" %>
			<div id="con">
				<h3><img src="<%=request.getContextPath() %>/krri/common/images/titles/tit_h3_facilities_list.gif" alt="포토 캘러리" /></h3>
				<ul class="fac_viewtab">
					<li class="a1"><a href="webCommInfoAction!list.do?c=17&amp;clear=1">주요행사</a></li>
					<li class="a2on"><a href="webCommInfoAction!list.do?c=16&amp;clear=1">연구시설</a></li>
				</ul>
				<table summary="연구시설게시물의 제목,작성자 등을 제공한 표" class="table_emview">
					<caption>연구시설</caption>
					<colgroup>
						<col width="13%" />
						<col width="34%" />
						<col width="13%" />
						<col width="14%" />
						<col width="13%" />
						<col width="*" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">제  목</th>
							<td class="bor_rno" colspan="5"><s:property value="bean.TITLE"/></td>
						</tr>
						<tr>
							<th scope="row" class="bor_bgreen">작성자</th>
							<td class="bor_bgreen"><s:property value="bean.REG_ADMIN"/></td>
							<th class="bor_bgreen" scope="row">작성일</th>
							<td class="td_center bor_bgreen"><s:date name="bean.REG_DATE" format="yyyy-MM-dd"/></td>
							<th scope="row" class="bor_bgreen">조회수</th>
							<td class="bor_rno td_center bor_bgreen"><s:property value="bean.CLICK_CNT"/></td>
						</tr>
						<tr>
							<td style="overflow:auto;word-break:break-all;" class=" bor_bno bottd bor_rno sty_pad1" colspan="6">
							<s:set name="contents" value="bean.COMM_CONTENTS"></s:set>
							<%=com.train.util.StringUtil.toHtml(String.valueOf(request.getAttribute("contents"))) %><br/>
								<s:iterator value="#request.file_list" status="fl">
								<s:if test="#fl.index==0">
								<img src="<%=request.getContextPath() %>/<s:property value="SYSFILE_NAME"/>" alt="<s:property value="FILE_NAME"/>" width="310" height="235" id="dong"/>
								</s:if>
								</s:iterator>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="box_mediav">
			<%
					com.train.util.Pages pager=(com.train.util.Pages)request.getAttribute("pager");
			%>
			 <pg:pager items="<%=pager.getItems() %>" maxPageItems="<%=pager.getMaxPageItems() %>"
								  maxIndexPages="<%=pager.getMaxIndexPages() %>" isOffset="true"
								  index="center" url="/webCommInfoAction!view.do"
								  export="offset, currentPageNumber=pageNumber" scope="request">
					<div class="box1_meview"> 
					<%
						String commNO = String.valueOf(request.getParameter("id"));
						String commClass = String.valueOf(request.getParameter("cla"));
					 %>
					<pg:param name="id" value="<%=commNO %>"/>
					<pg:param name="cla" value="<%=commClass %>"/>
					<pg:first>
						<a class="btn01" href="<s:property value="#attr.pageUrl"/>"><img src="<%=request.getContextPath()%>/krri/images/cyber/btn01_media_view.gif" alt="이전" /></a>
					</pg:first>
					
						<div class="box2_meview" style=" width:590px; ">
							<ul>
							<s:iterator value="#request.listFileBeans" status="st">
									<li><a href="#" id='pho<s:property value="#st.index"/>' onblur='removeClass(<s:property value="#st.index"/>)' onclick="move('<s:property value="SYSFILE_NAME"/>',<s:property value="#st.index"/>);return false;"><img src="<%=request.getContextPath() %>/<s:property value="SYSFILE_NAME"/>" alt="<s:property value="FILE_NAME"/>" width="110" height="74" /></a></li>
							</s:iterator>
							</ul>
						</div>
						
					<pg:last>
						<s:if test="#request.listFileBeans.size() >= 4">
							<a class="btn02" href="<s:property value="#attr.pageUrl"/>"><img src="<%=request.getContextPath()%>/krri/images/cyber/btn02_media_view.gif" alt="다음" /></a>
						</s:if>
					</pg:last>
					</pg:pager>	
					</div>
				</div>
				<div class="box_collbot">
					<ul class="ul02">
						<li><a href="webCommInfoAction!list.do?c=16&amp;pager.offset=<s:property value="#request.pager.offset-1"/>"><img src="<%=request.getContextPath() %>/krri/images/cyber/btn02_cool_view.gif" alt="목록보기" /></a></li>
					</ul>
				</div>
			</div>
		</div>
		<!--contents end-->
		<!--quick_menu start-->
		<%@ include file="../common/include/quick_menu.jsp" %>
		<!--quick_menu end-->
	</div>
	<!--footer start-->
	<%@ include file="../common/include/footer.jsp" %>
	<!--footer end-->
</div>
</body>
</html>