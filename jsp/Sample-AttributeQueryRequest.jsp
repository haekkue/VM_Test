<%@ page language = "java" contentType = "text/html; charset=UTF-8"%>
<%@ page import="gov.mogaha.gpin.sp.proxy.*" %>

<%
    // SendRequestAttr.jsp

    session.setAttribute("gpinAuthRetPage", "Sample-AttributeQueryResponse.jsp");

	GPinProxy proxy = GPinProxy.getInstance(this.getServletConfig().getServletContext());

    String requestHTML;
    System.out.println("Attr="+request.getParameter("Attr") );
    System.out.println("vidn="+request.getParameter("vidn") );

    String[] Attrs = Util.split(request.getParameter("Attr"), ';');

    for (int i = 0; i < Attrs.length; i++)
    {
        System.out.println("Attr"+i+":" + Attrs[i]);
    }

    requestHTML = proxy.makeAttributeRequest((String)request.getParameter("vidn"), Attrs);
    out.println(requestHTML);
%>
