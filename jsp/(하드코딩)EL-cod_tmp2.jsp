<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head>
<title>Title01</title>
</head>

<body>
  Hahaha<br><br>

<!--
�Ʒ� ${����} �����

1. <c:xxx ���� �±� ���� ${����}�� ����� �ƴ�
2. �� �� ${����}�� �����
3. <c:xxx ���� �±� ���̶� escapeXML=false ���� ${����}�� �����
-->

<!-- 
����� : 
����� : ${item.thngid}
-->
<input type="hidden" name="thngid" id="thngid" value=<c:xxx value=��${item.thngid}"/> /> 

<!-- 
����� : 
����� : ${item.thngid}
-->
<input type="hidden" name="thngid" id="thngid" value=<c:out value=��${item.thngid}"/> /> 

<!-- 
����� : 
����� : ${empty userSession.uid}
-->
<input type="hidden" name="thngid" id="thngid" value=<c:when test="${empty userSession.uid}"> /> 

<!-- 
����� : ${param.flag}
����� : 
-->
<input type="hidden" name="flag" id="flag" value="${param.flag}" />

<!-- 
����� : ${item.thngseq}
����� :
-->
<input type="hidden" name="thngseq" id="thngseq" value=<c:out value=��${item.thngseq}" escapeXML=false />/>

<!-- 
����� : ${sTitle}, ${sURL} 
����� : ${not empty userSession}
-->
<c:if test="${not empty userSession}">
	<li><a href="#none" onclick="addScrap('${sTitle}', '<%=contextPath%>/common/jsp/CommScrap.jsp', '${sURL}', document.getElementById('fScrap')); return false;">
	<img src="<%=imagePath%>/common/btn_scrap.gif" alt="scrap" /></a></li>
</c:if>

<!-- 
����� : ${thngid}, ${test}
����� : ${empty userSession.uid}
-->
<input type="hidden" name="thngid" id="${thngid}" value=<c:when test="${empty userSession.uid}"> id2="${test}"  /> 

<!-- 
����� : ${county.countyId}, ${county.countyName} 
����� : 
-->
<option value="${county.countyId}">${county.countyName}</option>


</body>
</html>

