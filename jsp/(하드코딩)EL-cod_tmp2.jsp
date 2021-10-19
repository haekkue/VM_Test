<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head>
<title>Title01</title>
</head>

<body>
  Hahaha<br><br>

<!--
아래 ${변수} 취약점

1. <c:xxx 으로 태그 안의 ${변수}는 취약점 아님
2. 그 외 ${변수}는 취약점
3. <c:xxx 으로 태그 안이라도 escapeXML=false 안의 ${변수}는 취약점
-->

<!-- 
취약점 : 
미취약 : ${item.thngid}
-->
<input type="hidden" name="thngid" id="thngid" value=<c:xxx value=”${item.thngid}"/> /> 

<!-- 
취약점 : 
미취약 : ${item.thngid}
-->
<input type="hidden" name="thngid" id="thngid" value=<c:out value=”${item.thngid}"/> /> 

<!-- 
취약점 : 
미취약 : ${empty userSession.uid}
-->
<input type="hidden" name="thngid" id="thngid" value=<c:when test="${empty userSession.uid}"> /> 

<!-- 
취약점 : ${param.flag}
미취약 : 
-->
<input type="hidden" name="flag" id="flag" value="${param.flag}" />

<!-- 
취약점 : ${item.thngseq}
미취약 :
-->
<input type="hidden" name="thngseq" id="thngseq" value=<c:out value=”${item.thngseq}" escapeXML=false />/>

<!-- 
취약점 : ${sTitle}, ${sURL} 
미취약 : ${not empty userSession}
-->
<c:if test="${not empty userSession}">
	<li><a href="#none" onclick="addScrap('${sTitle}', '<%=contextPath%>/common/jsp/CommScrap.jsp', '${sURL}', document.getElementById('fScrap')); return false;">
	<img src="<%=imagePath%>/common/btn_scrap.gif" alt="scrap" /></a></li>
</c:if>

<!-- 
취약점 : ${thngid}, ${test}
미취약 : ${empty userSession.uid}
-->
<input type="hidden" name="thngid" id="${thngid}" value=<c:when test="${empty userSession.uid}"> id2="${test}"  /> 

<!-- 
취약점 : ${county.countyId}, ${county.countyName} 
미취약 : 
-->
<option value="${county.countyId}">${county.countyName}</option>


</body>
</html>

