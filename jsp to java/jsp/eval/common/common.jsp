<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="com.hmall.eval.util.EvalUtil" %>
<%@ page import="com.hmall.eval.util.SessionManager" %>
<%@ page import="com.hmall.eval.util.SessionObject" %>
<%@ page import="com.hmall.eval.dto.CustUserDto" %>

<HEAD>
	<TITLE>현대Hmall</TITLE>
	<META http-equiv=Content-Type content="text/html; charset=euc-kr">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String vImageUrl = EvalUtil.getImageUrl();
	String vUploadImageUrl = EvalUtil.getUploadImageUrl();
	String vEssence = "<font color='red'><b>* </b></font>";
	String vCssUrl = EvalUtil.getCSSUrl();
	String vJsUrl = EvalUtil.getJsUrl();
	
	String sesCust_No = "";
	String sesCustuserId = "";
	String sesCustName = "";
	String sesCustMail = "";
	String sesCustTel = "";
	String sesCustPartCode = "";
	String sesCustPartCodeName = "";
	String sesCustSectionCode = "";
	String sesCustSectionCodeName = "";
	String sesCustRoleCode = "";
	String sesCustRoleCodeName = "";
	String sesAdminFlag = "";
	String tot_Saveamt = "";
	String sesMd_sabun = "";
	
	if(SessionManager.getSession(request) != null && SessionManager.getSession(request).getCustUserDto() != null) {
		sesCustName = SessionManager.getSession(request).getCustUserDto().getCustName()==null ? "" : SessionManager.getSession(request).getCustUserDto().getCustName();		
	}
%>


<SCRIPT language=javascript>
/*--------------------------validation check-------------------------------*/
function checkLength(vData, limitLength, object) {
	var form = document.form01;
	var vDataLength = vData.length;
	if(vDataLength>limitLength) {
		alert("글자 수는 "+limitLength+"자 이상을 넘을 수 없습니다.");
		object.value = vData.substr(0,limitLength-1); 
		object.focus();
		return;
	}
}

function checkLessLength(vData, limitLength, vDataName) {
	var form = document.form01;
	var vDataLength = vData.length;
	if(vDataLength!=0 && vDataLength<limitLength) {
		alert("["+vDataName+"] 제안내용은 "+limitLength+"자 이상 되어야 합니다.");
		return true;
	}
	return false;
}

function check_empty(object, message, checkName){
	if (is_empty(object.value)) {
		if (!is_empty(message))
			alert("[" + checkName + "] " + message);
		else
			alert("[" + checkName + "] 필수입력 항목입니다.");
		object.focus();
		return true;
	}
	return false;
}

function is_empty( p_str ) {
	if( p_str == null || trim(p_str) == "" || p_str == "undefined" ) return true;
    return false;
}

function trim( p_str ) {
	return ltrim( rtrim( p_str ) );
}

function ltrim( p_str ) {
	if (p_str == "") {
		return	p_str;
	}
	var len = p_str.length;
	var st = 0;

	while ((st < len) && (p_str.charAt(st) <= ' ')) {
		st++;
	}
	return	(st > 0) ? p_str.substring(st, len) : p_str;
}

function rtrim( p_str ) {
	if (p_str == "") {
		return	p_str;
	}
	var len = p_str.length;
	var st = 0;
	while ((st < len) && (p_str.charAt(len - 1) <= ' ')) {
		len--;
	}
	return	(len < p_str.length) ? p_str.substring(st, len) : p_str;
}
</script>
</HEAD>

<%@ include file="/jsp/eval/common/top_menu.jsp" %>