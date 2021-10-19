<HTML>
<HEAD>
<TITLE>현대Hmall</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=euc-kr">
<LINK REL="STYLEsheet" HREF="<%=vCssUrl%>styleEval.css" TYPE="text/css">
<SCRIPT language=javascript>
	function funMovePage(url){
<%	if(SessionManager.getSession(request) == null) {	%>
		alert("로그인 정보가 없습니다.\n로그아웃 하신 후 다시 접속해 주십시오!");
		return;
<%	}	%>
	    window.location = url;
	}
	
	function openAnothorPop(tmpUrl) {
<%	if(SessionManager.getSession(request) == null) {	%>
		alert("로그인 정보가 없습니다.\n로그아웃 하신 후 다시 접속해 주십시오!");
		return;
<%	}	%>
		var url = tmpUrl;
		var popName = "openAnothorCust";
		var popOption = "height=800, width=1024, fullscreen=no, location=yes, scrollbars=yes, menubar=yes, toolbar=yes, titlebar=yes, directories=no, resizable=yes";
		window.open(url,popName,popOption);
	}
	
</SCRIPT>
</HEAD>

<BODY LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0">
<TABLE width="910" border="0" cellspacing="0" cellpadding="0" align="center">
  <TR>
    <TD width="325" height="58">
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="325" height="58" title="logo">
      <PARAM name="movie" value="<%=vImageUrl%>top_logo1.swf">
      <PARAM name="quality" value="high">
      <EMBED src="<%=vImageUrl%>top_logo1.swf" QUALITY="high" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer" TYPE="application/x-shockwave-flash" width="325" height="58"></EMBED>
    </OBJECT></TD>
    <TD width="585" align="right" valign="top" background="<%=vImageUrl%>top_img02.jpg" style="padding: 33 205 0 0;">
		<!-- 개인정보 S -->
		<TABLE border="0" cellspacing="0" cellpadding="0">
			<TR>
				<TD style="padding-right:10px"><%=sesCustName%> 님</TD>
				<TD style="padding-right:10px" class="tx_og">적립금 <%=tot_Saveamt%>원</TD>
				<TD style="padding-right:10px"><a href="<%=path%>/eval/logOut.do">로그아웃</A></TD>
			</TR>
		</TABLE>
		<!-- 개인정보 E -->
	</TD>
  </TR>
   <TR>
    <TD height="45" colspan="2">
			<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD><IMG src="<%=vImageUrl%>top_menu_bgl.gif"></TD>
					<TD><A href="javascript:funMovePage('<%=path%>/eval/moveMain.do')"><IMG src="<%=vImageUrl%>top_menu_01.gif" border="0"></A></TD>
					<TD><A href="javascript:funMovePage('<%=path%>/eval/proposal/infoCatchList.do')"><IMG src="<%=vImageUrl%>top_menu_02.gif" border="0"></A></TD>
					<TD><A href="javascript:openAnothorPop('<%=path%>/cnPollL.do')"><IMG src="<%=vImageUrl%>top070913_menu_03.gif" border="0"></A></TD>
					<TD><A href="javascript:openAnothorPop('<%=path%>/cnMonitorR.do')"><IMG src="<%=vImageUrl%>top070913_menu_04.gif" border="0"></A></TD>
<%	if(sesAdminFlag.equals("1") || sesCustRoleCode.equals("MONITOR")) {	
		if(sesAdminFlag.equals("1")) {	%>
					<TD><A href="javascript:funMovePage('<%=path%>/eval/manage/memberList.do')"><IMG src="<%=vImageUrl%>top070913_menu_05.gif" border="0"></A></TD>
<%		} else if(sesCustRoleCode.equals("MONITOR")) {	%>
					<TD><A href="javascript:funMovePage('<%=path%>/eval/manage/valuateActivity.do')"><IMG src="<%=vImageUrl%>top_menu_06.gif" border="0"></A></TD>
<%		}
	} else {	%>
					<TD><IMG src="<%=vImageUrl%>top070913_menu_05_1.gif"></TD>
					
<%	}	%>
                    <TD><IMG src="<%=vImageUrl%>top_menu_06_bg.gif"></TD>
					<TD><IMG src="<%=vImageUrl%>top_menu_bgr.gif"></TD>
				</TR>
			</TABLE>		
		</TD>
  </TR>
  <TR>
    <TD height="6" colspan="2"></TD>
  </TR>
</TABLE>
</BODY>
</HTML>