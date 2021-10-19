<%@ page contentType="text/html; charset=euc-kr" language="java"%>
<%@ page import="kr.co.envision.fw.RequestWrapper" %>
<%@ page import="java.util.*, kr.co.envision.fw.UserList" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.lang.math.NumberUtils" %>
<%@ page
	import="com.oreilly.servlet.MultipartRequest,com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="kr.co.envision.fw.Config"%>
<jsp:useBean id="mgr" class="kr.co.envision.kdb.manage.asset.AssetMgr" scope="page" />
<jsp:useBean id="userInfo" class="kr.co.envision.kdb.UserInfo" scope="session" />

<%
	boolean b = false;
	String rtnType = "auto";
	String url = "";
	String msg = "";
	String script = "";

	request.setCharacterEncoding("euc-kr");
	String mode = StringUtils.defaultString(request.getParameter("mode"));
	
	if(mode.equals("upload")) { 
		int sizeLimit = 10 * 1024 * 1024;
		Config conf = Config.getInstance();
		MultipartRequest mReq = new MultipartRequest(request, conf.getString("TempExcelUpoadFileDir"), sizeLimit, "euc-kr",
			new DefaultFileRenamePolicy());

		String filename1 = mReq.getFilesystemName("file0");
		ArrayList errlist = new ArrayList();

		String ERROR_MSG = mgr.insertPkFile( mReq.getFile("file0"), errlist );
		if(  ERROR_MSG == null && errlist.size() == 0 ) {
			msg = filename1+ " 파일을 성공적으로 등록했습니다.";
			script = "opener.location.reload(); self.close();";
		} else {
			msg = ERROR_MSG;
		}
		if( ERROR_MSG == null ) b= true;
	} else {
		msg= "해당 요청 모드가 존재하지 않습니다.";
	}
%>
<!--메시지 처리-->
<script language="javascript">
<%
	if( msg != null && !msg.equals("")) {
		out.println("alert(\""+ msg + "\");");
	}
	if( !rtnType.equals("auto") ){
		//개발자 직접 기술
	} else {
		if( b ) {
			if(!url.equals("") ) { 
				out.println("location.href('"+url+"');");
			} else {
				out.println(script);
			}
		} else {
			out.println("history.back();");
		}
	}
%>
</script>

