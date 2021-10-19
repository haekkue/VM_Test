<%@ page language="java" contentType="application/vns.ms-excel; charset=utf-8"   pageEncoding="UTF-8"%>
<%@page import= "java.util.*" %>
<%@page import="java.net.*"%>
<%@page import="java.io.*"%>
<%@page import= "com.util.StrUtil" %>
<%@page import= "com.util.Page" %>
<%
	int tp = (Integer)request.getAttribute("tp");
	String filename = (String)request.getAttribute("filename");
	String title = (String)request.getAttribute("title");
	HashMap headtitle = (HashMap)request.getAttribute("headtitle");
	List aList    = (List)request.getAttribute("aList");
	List subList = (List)aList.get(0);
	
	response.reset();
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Content-Disposition", "attachment;filename="+ filename +".xls;");
	response.setHeader("Content-Description", "JSP Generated Data");

%>
<html>
<head>
<meta http-equiv="Content-type" content="application/x-msexcel;charset=UTF-8">
</head>
<body oncontextmenu="return false" ondragstart="return false" !onselectstart="return false" >
<h3><%=title %></h3>
<table>
<tr>
	<%for(int i=1; i <= headtitle.size(); i++){%>
  		<th ><p><%=headtitle.get(i)%></p></th>
  	<%}%>
</tr>
<%



	for(int i=0; i<subList.size(); i++){
		HashMap hm = (HashMap)subList.get(i);
%>  
	<tr>
		<td><%=hm.get("EIDX") %></td>
		<td><%=hm.get("MNAME") %></td>
		<td><%=hm.get("MEMID") %></td>
		<td><%=hm.get("BIRTH") %></td>
		<td><%=hm.get("SEX") %></td>
		<td><%=hm.get("TEL1") %> - <%=hm.get("TEL2") %> - <%=hm.get("TEL3") %></td>
		<td><%=hm.get("REGDATE") %></td>
		<td><%=hm.get("ZIPCODE") %></td>
		<td><%=hm.get("ADDR1") %></td>
		<td><%=hm.get("ADDR2") %></td>
		<td><%=hm.get("CONTENT") %></td>
		<td><%=hm.get("WINYN") %></td>
		<td><%=hm.get("WINRANK") %></td>
		<td><%=hm.get("EMAIL") %></td>
		<td><%=hm.get("SOCID") %></td>
		<td><%=hm.get("DICODE") %></td>
	</tr>
<%
	}
%>   	 
 
</table>
</body>
</html>	