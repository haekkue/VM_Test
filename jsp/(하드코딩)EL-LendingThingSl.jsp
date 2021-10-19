<%--
/*
 *************************************************************************
 * @DESC  : 장난감 도서관 상세 (홈페이지)
 * @PROJ  : 홈페이지 통합
 * Copyright 2009 LG CNS All rights reserved
 *------------------------------------------------------------------------
 * DATE         AUTHOR      DESCRIPTION
 * -----------  ----------  -----------------------------------------------
 * 2010.03.08   정영순      최초 작성
 *************************************************************************
 */
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-------------------------------------------------------------------------
                            ## jsp header 정의 ##
--------------------------------------------------------------------------%>
<%@ include file="/cpis2gi/common/include/Head.jspf"%>
<%
programId = request.getParameter("programId");
if(programId != null){
  programId = programId.replaceAll("<","&lt;");
  programId = programId.replaceAll(">","&gt;");
}
%>

<!--
아래 ${변수} 취약점
-->

<%-------------------------------------------------------------------------
                            ## import java 객체 정의 ##
--------------------------------------------------------------------------%>
<tag:sTag  bean="cis.cpis2gi.lend.LendThingMgr"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${head_title}</title>
<%-------------------------------------------------------------------------
                      ## 공통 Script 및 CSS 인클루드  ##
--------------------------------------------------------------------------%>
<%@ include file="/cpis2gi/common/include/Web.jspf"%>
<%-- SNS 처리를 위한 js 파일을 include 한다. --%>
<script type="text/javascript" src="<%=jscpis2giPath%>/sns.js"></script>
<%-- 스크랩 처리를 위한 js 파일을 include 한다. --%>
<script type="text/javascript" src="<%=jscpis2giPath%>/scrap.js"></script>
<%-------------------------------------------------------------------------
                      ## 자바 Script Function 정의 ##
--------------------------------------------------------------------------%>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function() {
   var frm = document.cfrm;
   frm.bcomment.validExp = "댓글내용:yes:minByteLength=1&maxByteLength=600";
  fnOnload();
});


  //window.onload = fnOnload;

  function fnOnload(){

    //fnImgReszie();

    //예약 가능여부
    <c:set var="rsvable" value="대여신청불가" />
    <c:if test="${item.lendgb eq '1'}"> //온라인인 경우만
    <c:if test="${item.thngstscode eq '01' }">
    <c:if test="${curLendStsCode eq '02' || curLendStsCode eq '05' || curLendStsCode eq '00'}">
          <c:set var="rsvable" value="대여신청가능" />
        </c:if>
      </c:if>
    </c:if>
  }

  function fnImgReszie(){

    var img1 = document.getElementById("img1");
    var originWidth  = img1.width;
    var originHeight = img1.height;

    img1.width  = 200;
    img1.height = 200;

  }

  function fnList(){
    var frm = document.sfrm;
    frm.flag.value = "";
    frm.action = "<%=contextPath%>/lend/LendingThingSlPL.jsp";
    //frm.target = '_self';
    frm.submit();
  }

  function fnRsvForm(){
    if("${userSession.uid}"==""){
      alert("로그인 하셔야 합니다.");
      return;
    }

    if("${item.lendgb}" !="1"){
      alert("인터넷 물품만 대여신청이 가능합니다.");
      return;
    }

    if("${item.thngstscode}" != "01") { // 보관중일때만 예약가능
      alert("대여신청이 불가능한 물품 입니다.");
      return;
    }

    var curLendStsCode ='${curLendStsCode}';
    var curLendStsCodeNm;
    if(curLendStsCode=='01'){curLendStsCodeNm='대여신청중';}
    else if(curLendStsCode=='02'){curLendStsCodeNm='대여신청취소';}
    else if(curLendStsCode=='03'){curLendStsCodeNm='대여';}
    else if(curLendStsCode=='04'){curLendStsCodeNm='대여연장';}
    else if(curLendStsCode=='05'){curLendStsCodeNm='반납';}
    else if(curLendStsCode=='06'){curLendStsCodeNm='분실미반납';}

    if(curLendStsCode =="02" || curLendStsCode =="05"  || curLendStsCode == "00"){ //예약취소, 반납 상태
      var frm = document.sfrm;
      frm.flag.value = "RF";
      frm.action = "<%=contextPath%>/lend/ReservationIsF.jsp";
      frm.submit();
    }else{
      alert("대여신청이 불가능한 물품 입니다. ("+curLendStsCodeNm+")");
      return;
    }
  }

  //댓글 달기
  function fnReply(){
    var frm = document.cfrm;
    if(!cfValidate(frm))
        {
          return;
        }
     if(!cfConfirmMsg(MSG_CONFIRM_REGIST))
        {
          return;
        }
    frm.action="<%=contextPath%>/lend/LendingThingSv.jsp"
    if("${userSession.uid}"==""){
      alert("로그인 하셔야 합니다.");
      return;
    }
    frm.submit();
  }

  //코멘트삭제
  function fnDeleteComment(seq){
    var frm = document.cfrm;
    if(!cfConfirmMsg(MSG_CONFIRM_DELETE)) return ;
    frm.action = "<%=contextPath%>/lend/LendingThingSv.jsp";
    frm.flag.value = "cDel";
    frm.cmtseq.value = seq;
    frm.submit();
  }

    //페이지이동
  function gotoPage(offset) {
    var frm = document.cfrm;
    frm.flag.value = "Sl";
    frm.offset.value = offset;
    frm.action = '<%=contextPath%>/lend/LendingThingSv.jsp';
    frm.submit();
  }

//]]>
</script>
</head>

<body id="sub">
<noscript>
  <p>본 사이트는 자바스크립트가 작동되어야 편리하게 이용하실 수 있습니다. 현재 스크립트가 정상적으로 작동되지 않습니다.</p>
</noscript>
<!-- accessablity -->
<div id="accessablity">
  <a href="#container">본문바로가기</a>
  <a href="#gnbwrap">주메뉴바로가기</a>
  <a href="#footer">하단영역바로가기</a>
</div>
<!-- //accessablity -->
<!-- wrap /S/ -->
<div id="wrap">
<!-- header /S/ -->
<% if(type.equals("01")) { %>
<%@ include file="/cpis2gi/common/include/page/Header01.jspf"%>
<% } else if(type.equals("02")) { %>
<%@ include file="/cpis2gi/common/include/page/Header02.jspf"%>
<% } %>
<!-- header /E/ -->
<hr />
  <!-- sub_visual /S/ -->
  <% if(type.equals("01")) { %>
  <%@ include file="/cpis2gi/cms/commonContent/topVisual_8042.jspf"%>
  <% } else if(type.equals("02")) { %>
  <%@ include file="/cpis2gi/cms/commonContent/topVisual_8318.jspf"%>
  <% } %>
  <!-- sub_visual /E/ -->

  <!-- container /S/ -->
  <div id="container">
    <!-- LNB /S/ -->
    <%@ include file="/cpis2gi/common/include/Left.jspf"%>
    <!--// LNB /E/ -->
    <!-- contents /S/ -->
    <div id="contentswrap" class="contents">
      <!-- page_map -->
      <%@ include file="/cpis2gi/common/include/page/location.jspf"%>
      <!--//page_map -->
      <h2 class="dno">전자도서관</h2>
      <c:if test="${param.thnggubn eq '01' }">
      <h4 class="tit"><img src="<%=imagePath%>/common/h4_0302_01.gif" alt="장난감" /></h4>
      </c:if>
      <c:if test="${param.thnggubn eq '02' }">
      <h4 class="tit"><img src="<%=imagePath%>/common/h4_0302_02.gif" alt="도서" /></h4>
      </c:if>
      <c:if test="${param.thnggubn eq '03' }">
      <h4 class="tit"><img src="<%=imagePath%>/common/h4_0302_03.gif" alt="교재교구" /></h4>
      </c:if>
      <!-- util_menu -->
      <div class="util_menu">
        <ul>
          <c:set var="sTitle" value="${item.thngnm}" />
          <c:set var="sURL" value="/cpis2gi/lend/LendingThingSl.jsp" />
          <c:if test="${empty userSession}">
          <li><a href="#none" onclick="alert('로그인이 필요합니다.'); return false;"><img src="<%=imagePath%>/common/btn_scrap.gif" alt="scrap" /></a></li>
          </c:if>
          <c:if test="${not empty userSession}">
          <li><a href="#none" onclick="addScrap('${sTitle}', '<%=contextPath%>/common/jsp/CommScrap.jsp', '${sURL}', document.getElementById('fScrap')); return false;"><img src="<%=imagePath%>/common/btn_scrap.gif" alt="scrap" /></a></li>
          </c:if>
          <%-- SNS 예제 --%>
          <li><a href="#none" onClick="facebook( '${sTitle}', 'http://<%=request.getServerName()%>/cpis2gi/lend/LendingThingSl.jsp', document.getElementById('fScrap')); return false;"><img src="<%=imagePath%>/common/btn_facebook.gif" alt="facebook" /></a></li>
          <li><a href="#none" onClick="twitter( '${sTitle}', 'http://<%=request.getServerName()%>/cpis2gi/lend/LendingThingSl.jsp', document.getElementById('fScrap')); return false;"><img src="<%=imagePath%>/common/btn_twitter.gif" alt="twitter" /></a></li>
        </ul>
      </div>
      <!--//util_menu -->
      <!-- con /S/ -->
      <div class="con">
      <%-- 스크랩할 때 사용하는 FORM SJ는 게시물의 제목이고 JSPURL은 JSP의 URL정보이며(SJ, JSPURL, programId은 필수) 추가 파라미터는 필요에 따라 추가한다. --%>
      <form name="fScrap" id="fScrap" method="post" action="">
        <input type="hidden" name="SJ" value=""/>
        <input type="hidden" name="JSPURL" value=""/>
        <input type="hidden" name="flag" id="flag" value="${param.flag}" />
        <input type="hidden" name="thngid" id="thngid" value="${item.thngid}" />
        <input type="hidden" name="thngseq" id="thngseq" value="${item.thngseq}" />
        <input type="hidden" name="thnggubn" id="thnggubn" value="${param.thnggubn}" />
        <input type="hidden" name="total" id="total" value="${param.total}" />
        <input type="hidden" name="offset" id="offset" value="${param.offset}" />
        <input type="hidden" name="limit" id="limit" value="${param.limit}" />
        <input type="hidden" name="SYSCODE" id="SYSCODE" value="${param.SYSCODE}" />
        <input type="hidden" name="programId" id="programId" value="<%=programId%>" />
        <input type="image" name="hiddenSubmit" id="hiddenSubmit" src="<%=imageRootPath%>/common/common/blank.gif" alt="post" style="display: none;" />
      </form>

        <!-- view -->
      <form name="sfrm" id="sfrm" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="flag" id="flag" value="${param.flag}"/>
      <input type="hidden" name="thngid" id="thngid" value="${item.thngid}" />
      <input type="hidden" name="thngseq" id="thngseq" value="${item.thngseq}" />
      <input type="hidden" name="thnggubn" id="thnggubn" value="${param.thnggubn}" />
      <input type="hidden" name="offset" id="offset" value="${param.offset}" /><!-- 페이징처리에 필요  -->
      <input type="hidden" name="programId" id="programId" value="<%=programId%>" />
      <input type="image" name="hiddenSubmit" id="hiddenSubmit" src="<%=imageRootPath%>/common/common/blank.gif" alt="post" style="display: none;" />

      <input type="hidden" name="SEL_SYSCODE" id="SEL_SYSCODE" value="${param.SEL_SYSCODE}" />
      <input type="hidden" name="thngclsfccode" id="thngclsfccode" value="${param.thngclsfccode}" />
      <input type="hidden" name="relmcode" id="relmcode" value="${param.relmcode}" />
      <input type="hidden" name="growthinfocode" id="growthinfocode" value="${param.growthinfocode}" />
      <input type="hidden" name="agecode" id="agecode" value="${param.agecode}" />
      <input type="hidden" name="lendable" id="lendable" value="${param.lendable}" />
      <input type="hidden" name="thngnm" id="thngnm" value="${param.thngnm}" />

      <table class="tb-vty1" summary="장난감도서관 상세보기입니다.">
        <caption>장난감도서관</caption>
        <colgroup>
          <col width="35%" />
          <col width="" />
        </colgroup>
        <thead>
          <tr>
            <th class="ty1" colspan="2">${item.thngnm}
            <c:if test="${not empty item.manageno}">
            (${item.manageno})
            </c:if>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
              <c:if test="${!empty item.photofilenm}">
              <td class="ptb2 vat">
              <span class="tin_mg">
              <img src="<%=imageRootPath%>/cpis/lend${item.photostrepath}${item.photofilenm}" width="219px" alt="${item.thngnm}"  />
              </span>
              </td>
              </c:if>
              <c:if test="${empty item.photofilenm}">
              <td class="ptb2 vat">
              <span class="tin_mg">
              <img id="img1" name="img1" src="<%=imageRootPath%>/ccis/01/common/noimg_200200.gif" alt="${item.thngnm}"  />
              </span>
              </td>
              </c:if>
              <td class="tbin vat">
              <table class="tb-vin1" summary="">
              <colgroup>
                <col width="20%" />
                <col width="*" />
              </colgroup>
              <tr>
                  <th class="tln" scope="row">영역</th>
                  <td>${item.relmcodenm}</td>
              </tr>
              <tr>
                  <th class="tln" scope="row">발달정보</th>
                  <td>${item.growthinfocodenm}</td>
              </tr>
              <tr>
                  <th class="tln" scope="row">연령</th>
                  <td>${item.agecodenm}</td>
              </tr>
              <tr>
                  <th class="tln" scope="row">
                  <c:choose><c:when test="${item.thngclsfccode ne '02'}">제작사</c:when>
                            <c:otherwise>출판사</c:otherwise>
                  </c:choose>
                  </th>
                  <td>${item.makr}</td>
              </tr>
              <tr>
                  <th class="tln" scope="row">
                  <c:choose><c:when test="${item.thngclsfccode ne '02'}">등록일</c:when>
                  <c:otherwise>발행일</c:otherwise>
                  </c:choose>
                  </th>
                  <td>
                  <c:choose><c:when test="${item.thngclsfccode ne '02' }">${item.regdate }</c:when>
                  <c:otherwise>${item.pblictedt }</c:otherwise>
                  </c:choose>
                  </td>
              </tr>
              <tr>
                  <th class="tln" scope="row">대여구분</th>
                  <td>${item.lendgbnm}</td>
              </tr>
              <tr>
                  <th class="tln" scope="row">상태</th>
                  <td>
                  <c:choose>
                  <c:when test="${item.thngstscode eq '01' and ((empty item.lendstscode) or item.lendstscode eq '02' or item.lendstscode eq '05')}">
                     대여가능<c:if test="${item.lendgb eq '2'}">(방문대여만 가능)</c:if>
                  </c:when>
                  <c:when test="${(item.thngstscode eq '01' and (item.lendstscode eq '03' or item.lendstscode eq '04'))}">
                     대여불가<br/>
                    (반납예정일 : <c:choose>
                      <c:when test="${item.rturnprearngedt1 eq '--'}"></c:when>
                      <c:otherwise>${item.rturnprearngedt1 }</c:otherwise>
                    </c:choose>)
                  </c:when>
                  <c:otherwise>대여불가</c:otherwise>
                  </c:choose>
                  </td>
               </tr>
             </tbody>
             </table>
            </td>
          </tr>
        </tbody>
        </table>
        <!--// view -->

        <!-- select-area -->
        <div class="select-area">
        <!-- viewList  -->
        <div class="c-side">
      	<cis:pgFuncAuth progrmmid="PGM0701001/PGM0701002/PGM0701003" funcid="FN00000049/FN00000055/FN00000061" progrmkey="thnggubn=${param.thnggubn}" type="<%=type%>">
          <img src="<%=imagePath%>/board/btn_reserve.gif" alt="대여신청" onclick="fnRsvForm();return false;" onkeypress="if(event.keyCode==13){fnRsvForm();return false;}" style="cursor: pointer;" />
  		  </cis:pgFuncAuth>
          <img src="<%=imagePath%>/board/btn_list.gif" alt="목록보기" onclick="fnList();return false;" onkeypress="if(event.keyCode==13){fnList();return false;}" style="cursor: pointer;" />
        </div>
        <!-- // viewList  -->
      </div>
      </form>
      <!--// select-area -->
        <!-- rate -->
        <div class="tb_rate">
          <c:if test="${not empty userSession}">
          <c:set var="progrmId" value="thng" scope="page" />
          <c:set var="progrmKey" value="${item.thngid}|${item.thngseq}" scope="page" />
          <%@ include file="/cpis2gi/common/include/Satisfaction.jspf" %>
          </c:if>
        </div>
        <!--// rate -->

      <!-- comments -->
      <div class="comments">
      <form name="cfrm" id="cfrm"  method="post" action="">
      <input type="hidden" name="flag" id="flag" value="repl"/>
      <input type="hidden" name="thngid" id="thngid" value="${item.thngid}" />
      <input type="hidden" name="thngseq" id="thngseq" value="${item.thngseq}" />
      <input type="hidden" name="thnggubn" id="thnggubn" value="${param.thnggubn}" />
      <input type="hidden" name="cmtseq" id="cmtseq" value="${param.thnggubn}" />
      <input type="hidden" name="offset" id="offset" value="${param.offset}" /><!-- 페이징처리에 필요  -->
      <input type="hidden" name="programId" id="programId" value="<%=programId%>" />
      <input type="image" name="hiddenSubmit" id="hiddenSubmit" src="<%=imageRootPath%>/common/common/blank.gif" alt="post" style="display: none;" />
        <div class="regi">
          <c:choose>
            <c:when test="${not empty userSession.uid}">
              <label for="bcomment">댓글을입력하세요.</label>
              <textarea name="bcomment" id=bcomment rows="3" cols="1" onclick="" onkeyup="fc_chk_byte(this,600);" title="댓글"></textarea>
               <span id="txtCnt"><strong>0</strong> / 600bytes</span>
              <div class="btn2">
                <input type="image" src="<%=imagePath %>/board/btn_regi_comments.gif"  onclick="fnReply();return false;" onkeypress="if(event.keyCode==13){fnReply();return false;}" alt="댓글등록"  />
              </div>
            </c:when>
            <c:otherwise>
              <label for="bcomment">댓글을입력하세요.</label>
              <textarea disabled="true" name="bcomment" id=bcomment rows="3" cols="1" onclick="" onkeypress="if(event.keyCode==13){fnReply();return false;}"  title="댓글">로그인 후에 등록하실 수 있습니다.</textarea>
              <div class="btn2">
                <input type="image" src="<%=imagePath %>/board/btn_regi_comments.gif"  onclick="alert('로그인이 필요합니다.'); return false;" alt="댓글등록"  />
               </div>
            </c:otherwise>
          </c:choose>
        </div>

          <div class="view">
          <c:if test="${not empty cmtList}">
              <c:forEach items="${cmtList}" var="comment">
                <ul>
                  <li class="name2">${comment.REGISTID}</li>
                  <li class="date">${comment.REGDATE}</li>
                  <c:if test="${not empty userSession.uid && userSession.id eq comment.REGISTID}">
                  <li class="btn">
                      <a href="#none" onclick="fnDeleteComment('${comment.CMTSEQ}');" onkeypress="if(event.keyCode==13){fnDeleteComment('${comment.CMTSEQ}'); return false;}" >
                        <img src="<%=imagePath%>/board/btn_del_comments.gif" style="cursor: hand;" alt="댓글삭제" />
                      </a>
                  </li>
                  </c:if>
              </ul>
              <ul>
                  <li class="cont1">
                    <span>${comment.BCOMMENT}</span>
                  </li>
            </ul>
            </c:forEach>
          </c:if>
          </div>
        <!--//comments -->
        </form>
        <!--// ViewType -->
      </div>
      <!--// con /E/ -->
    </div>
    <!--// contents /E/ -->
  </div>
  </div>
  <!-- container /E/ -->
  <!-- quick_access /S/ -->
  <%@ include file="/cpis2gi/common/include/quick.jspf"%>
  <!--// quick_access /E/ -->
<hr />
  <!-- footer /S/ -->
  <%@ include file="/cpis2gi/common/include/Footer.jspf"%>
  <!-- footer /E/ -->
</div>
<!-- wrap /E/ -->
<!-- returntotop -->
<div id="returntotop"><a href="#accessablity">상단바로가기</a></div>
<!-- //returntotop -->
<%@ include file="/cpis2gi/common/include/Tail.jspf"%>
</body>
</html>
