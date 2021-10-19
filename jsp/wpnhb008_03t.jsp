<%
/*******************************************************************************
 * 최초작성일자     : 2013-01-09
 * 업        무     : 국민주택기금>주택채권>일반고객>채권 취소/변경/상환
 * 최초  작업자     : 90121326
 * 프로그램설명     : 취소/변경/상환 처리
 * 파일명           : wpnhb008_03t.jsp (c008226)
 
@taskcode WDCNHBO001_02C

@aptask   

@sqlid wpnhb005_01t.jsp.001(취소), wbnhb001_01t.jsp.004(중도상환)

@service  app.service.nhb.WDCNHBControl

@trcode   

*/
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/com/inc/wpcom001_01i.jsp" %>
<%@ taglib prefix="wbf" uri="/tld/wbf.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 
/***********************************************************************
 *  업무별 Service Time Check를 위해 해당 TASK_CODE를 기술한다.
/***********************************************************************
-->
<%  String USE_TASK_CODE = "WDCNHBO001_02C"; %>
<%
	String withyou2 = request.getParameter("withyou"); //채권번호
%>
<!-- 
/***********************************************************************
 *  Service Time Check 공통 JSP File
/***********************************************************************
-->
<%@ include file="/com/inc/wccom001_02i.jsp" %>

<%
logger.debug("################################################ WBF_DUP_CHECK_KEY:" + request.getParameter("WBF_DUP_CHECK_KEY"));
%>
<%
    // 중복체크 시작
    try 
    {
    	long requestedDupCheckKey      = Long.parseLong( request.getParameter("WBF_DUP_CHECK_KEY") );
    	DupRequestChecker dupChecker   = new DupRequestChecker();
    	dupChecker.checkKey(CURRENT_CUSTOMER.getUserID(), reqUUID, request, ""+requestedDupCheckKey);
        dupChecker.checkBitKey(CURRENT_CUSTOMER.getUserID(), reqUUID, request);
    } catch(WBFException dupKeyCreateEx) 
    {
        throw new WBFWebException("DUP00002", jspeed.cms.tag.ATag.getPageUrl(request, "HBNHB0014", null, false), null, pageContext);
    } catch(Exception e) 
    {
        throw new WBFWebException("DUP00002", jspeed.cms.tag.ATag.getPageUrl(request, "HBNHB0014", null, false), null, pageContext);
    }
    // 중복체크 끝
%>

<% 

/*  WMPNHB001_04C wpnhb005_01t.jsp.001  
insert into BAP_APV
(USER_ID, APVRQ_DT, APVRQ_SEQ, EXE_DT, APV_APL_DIS, STS, CAN_YN, RSV_TS_PC_YN,
APVAP_CASE_BAS_INF1, APVAP_CASE_BAS_INF2, APVAP_CASE_BAS_INF3, 
REQ_TOT_CNT, REQ_TOT_AMT,
RGS_FILE_ID, RGS_FILE_STOR_PTH, CO_CUS_MST_ID, CHG_DT, CHG_TM)
values(?, ?, ?, ?, ?, ?, ?, ?,
?, ?, ?, 
?, ?,
?, ?, ?, hex(current date), hex(current time))
*/
	WBFRecord inRecord = WBFRecordFactory.getInstance().create();
	//메타 파일 받기........
	String sMasterData = StringUtil.null2void(request.getParameter("masterdata")); // 총건수+적용일자			
	String sDetailData = StringUtil.null2void(request.getParameter("detaildata"));
	String GUBUN = StringUtil.null2void(request.getParameter("GUBUN"));
  	String APV_APL_DIS = request.getParameter("APV_APL_DIS");
	String[] masterData = StringUtil.parsing4(sMasterData, "；");	//마스터 정보 자르기	

%>

<%
	try
	{
	
		BTConnector pwBtConn = null;
		int verifyResult = 1;
		String sCertErrCd = "";
		String sCertErrMsg = "";
		
		// 파일명 체크 타스크 코드
		String returnURL = "wbdep330_01i.jsp";
		String chGb			= "w"; //w:웹구분
		String errFlag		= "N"; //N:일반페이지, P:팝업페이지
		String errMsgDis	= "1"; //1:한글, 2:영문
		String errTrCode	= ""; //trcode
		String errCode		= "";
		String errMsg		= "";
		String key			= "";
		
		String USER_ID = "";		
		
		String RGS_FILE_ID = DateUtil.get(DateUtil.YYYYMMDDhhmmssmil)+CURRENT_CUSTOMER.getUserID();
		
		String RGS_FILE_STOR_PTH = CommonPropService.getInstance().getProperty("BIZ1", "ftpTargetFileDir") + APV_APL_DIS + "/" + DateUtil.get(DateUtil.YYYYMMDD)+"/";
		
		inRecord.setField("USER_ID", CURRENT_CUSTOMER.getUserID());
		inRecord.setField("CAN_YN","0");
	    inRecord.setField("RGS_FILE_ID", RGS_FILE_ID); // 등록 파일 ID
	    inRecord.setField("RGS_FILE_STOR_PTH", RGS_FILE_STOR_PTH);
	    inRecord.setField("APV_APL_DIS", APV_APL_DIS); // 업무구분
	    inRecord.setField("CO_CUS_MST_ID", CURRENT_CUSTOMER.getUserID()); // 마스터 ID

	    inRecord.setField("STS", "11"); // 상태구분
	    
	    inRecord.setField("APV_YN", "0");				// 승인요청 여부('1':승인요청, '0':승인요청 아님)
	    
	    inRecord.setField("YUIBID", CURRENT_CUSTOMER.getUserID() );				// 이용자_ID
	    inRecord.setField("APVRQ_DT", DateUtil.get(DateUtil.YYYYMMDD));			// 승인요청_일자
	    inRecord.setField("APVRQ_SEQ", DateUtil.get(DateUtil.hhmmssmil));			// 승인요청_일련번호
	    inRecord.setField("EXE_DT", DateUtil.get(DateUtil.YYYYMMDD));				// 실행_일자
		 	
	    inRecord.setField("RSV_TS_PC_YN", "0");        // 예약여부구분
	     	 
	    inRecord.setField("REQ_TOT_CNT", masterData[0]);
		 	
	    inRecord.setField("REQ_TOT_AMT", "0");
	    
	    inRecord.setField("APVAP_CASE_BAS_INF1", GUBUN); // 계좌번호
	    inRecord.setField("APVAP_CASE_BAS_INF2", CURRENT_CUSTOMER.getUserID()); // 계좌번호
	    inRecord.setField("APVAP_CASE_BAS_INF3", CURRENT_CUSTOMER.getUserNm()); // 법무사주민번호
	    inRecord.setField("APVAP_CASE_BAS_INF4", CURRENT_CUSTOMER.getPsBzNo()); // 휴대번호승인여부
	    inRecord.setField("USE_DIS", GUBUN); // 계좌번호
	    inRecord.setField("detailData",sDetailData);
	    FileOutputStream fos = null;
	    
	    try
	    {
			byte[] FILE_DATA = (sDetailData + "\r\n").getBytes();									
			File fileDir = new File(RGS_FILE_STOR_PTH);
			
			if (!fileDir.exists())
			{
				fileDir.mkdirs();
			}		
			String FILE_FULL_PATH = RGS_FILE_STOR_PTH + File.separator + RGS_FILE_ID; //로그파일명
						
			fos = new FileOutputStream(FILE_FULL_PATH, true);
						
			fos.write(FILE_DATA);			
			
	    }
	    catch(Exception ex1)
	    {
	    	throw ex1;
	    }
	    finally
	    {
	    	try
	    	{
	    		fos.close();
	    	}
	    	catch(Exception ex2)
	    	{
	    		throw ex2;
	    	}
	    }   
	
		BTConnector btConn = null;
		WBFRecord outRecord = null;
	  
	  /***********************************************************************
	   *  전문통신을 위한 BTConnector 생성
	   *
	   *  개인    btConn = new BTConnectorImplPib("해당 TASK CODE");
	   *  기업    btConn = new BTConnectorImplBiz("해당 TASK CODE");
	   *  노블    btConn = new BTConnectorImplNob("해당 TASK CODE");
	   **********************************************************************/
	   btConn = new BTConnectorImplPib(USE_TASK_CODE);
	  
	  /***********************************************************************
	   *  전문통신 결과값을 WBFRecord로 받는다. (wsession -> CURRENT_CUSTOMER)
	   **********************************************************************/
	   outRecord = btConn.trx(CURRENT_CUSTOMER, inRecord, request);
	
	  /***********************************************************************
	   *  전문통신 결과가 정상이 아니면 에러처리
	   * 
	   *  @param   WBFRecord  outRecord   전문통신 결과 Record
	   *  @param   Stirng     retRrl      Return URL
	   *  @param   Stirng     chGubun     채널 구분(웹 : w, 모바일 : m, Xinternet : x)
	   **********************************************************************/
		if (outRecord.hasError()) 
		{      	
	      
			/*
			errCode     = outRecord.getResultCode();
			
			out.println("<script language='javascript'>");
			out.println("alert('[" +errCode+"]');");
			out.println("</script>");
			*/
			
			throw new WBFWebException(outRecord, wbf.web.UrlUtil.getPageUrl(request, "HBNHB0014", null, false), null, pageContext);
			
			
		}
		else
		{

%>
			<script language='javascript'>
				//parent.hideProgressBar();
				//parent.document.all["OK"].innerHTML = "";
				//alert('전송결과를 [결과확인]에서 확인하시기 바랍니다.');
			</script>
<%
		}
	}
	catch(Exception e)
	{
		throw e;
	}	
%> 

<div class="title-step-1 pos3">
	<ul>
		<li><span class="num">1</span><span>정보확인</span></li>
		<li class="on"><span class="num">2</span><span>실행완료</span></li>	
	</ul>
</div>
<div class="title-area clearfix">
	<h3 class="fleft">
	<%if("1".equals(GUBUN)){%>
	국민주택채권 매입 취소
	<%}else if("2".equals(GUBUN)){%>
	국민주택채권 중도상환
	<%}else if("3".equals(GUBUN)){%>
	국민주택채권을 상환취소
	<%}else{
	}%>
	</h3>
</div>
	
<dl class="box-cmsg mb20">
	<dt class="mt30 mb5">
	<%if("1".equals(GUBUN)){%>
	국민주택채권 매입 취소가 정상적으로 완료되었습니다.
	<%}else if("2".equals(GUBUN)){%>
	국민주택채권 중도상환이 정상적으로 완료되었습니다.
	<%}else if("3".equals(GUBUN)){%>
	국민주택채권을 상환취소가 정상적으로 완료되었습니다.
	<%}else{
	}%>
	</dt>
	<dd class="mb30">전송결과를 [결과확인]에서 확인하시기 바랍니다.</dd>
</dl>
<div class="btn-area">	
	<a href="javascript:goResult();" class="btn-pack btn-type-3c">확인</a>
</div>

<wbf:form name="frm" id="frm" method="POST">
</wbf:form>

<script type="text/javascript">
//<![CDATA[
/*
 * 초기화면으로 이동
 */
function goResult(){
	
	if('<%=withyou2%>'== 'HBNHB0014'){
		location.href='/svc/Dream?withyou=HBNHB0014';
	}else if('<%=withyou2%>'== 'HBNHB0061'){
		location.href='/svc/Dream?withyou=HBNHB0061';
	}
	
}
//]]>
</script>
