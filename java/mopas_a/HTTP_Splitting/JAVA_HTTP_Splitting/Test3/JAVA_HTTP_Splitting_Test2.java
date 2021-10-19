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
<%for(int i=0; i<aList.size(); i++){
	HashMap hm = (HashMap) aList.get(i);
%>  
<tr>
		<td><%=hm.get("REGDATE") %></td>
		<td><%=hm.get("LECIDX") %></td>
		<td><%=hm.get("LECNM") %></td>
		<td><%=hm.get("MEMID") %></td>
		<td><%=hm.get("MNAME") %></td>
		<td><%=hm.get("SDATE") %></td>
		<td><%=hm.get("EDATE") %></td>
</tr>
<%} %>   	 
 
</table>
</body>
</html>	