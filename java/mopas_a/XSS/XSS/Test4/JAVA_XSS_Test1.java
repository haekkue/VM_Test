package com.util;
import javax.servlet.http.*;
import java.io.*;
import java.lang.*;
import java.util.Enumeration;
import java.util.HashMap;

public class FormData {
	
	public static void PrintRequestValue(HttpServletRequest req){
		try{
			Enumeration enu = req.getParameterNames();
			while(enu.hasMoreElements()){
				String pnm = (String)enu.nextElement();
				String pval = (String)req.getParameter(pnm);
				System.out.println(pnm + " == " + pval);
			}			
		}catch(Exception e){
			ExceptionManager.getInstance().debug(e);
		}
	}
	
	
	public static void sendRedirect(HttpServletRequest req, HttpServletResponse res, String LoginURI){
		try{
			System.out.println(res.getWriter());
			PrintWriter p = res.getWriter();
			String pnm = "";
			String pval = "";
			Enumeration enu = req.getParameterNames();
			req.setCharacterEncoding("UTF8");
			res.setCharacterEncoding("UTF8");
			res.setContentType("text/html;charset=UTF-8");
			//res.setHeader("Content-Type", "text/plain");
			
			p.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			p.println("<html>");
			p.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");			
			p.println("<title>::: 개인정보보호 포탈 I-PRIVACY - 로그인이 필요합니다. :::</title>");			
			p.println("</head>");
			p.println("<body oncontextmenu=\"return false\" ondragstart=\"return false\" onselectstart=\"return false\" >");
			p.println("<form name=fm action=\"" +LoginURI+"\" method=post>");	
			while(enu.hasMoreElements()){
				pnm = (String)enu.nextElement();
				pval = (String)req.getParameter(pnm); 
				if(pval ==  null) pval="";
				p.println("<input type=hidden name=\""+pnm+"\" value=\"" + pval +"\">");				
			}
				p.println("<input type=hidden name=\"RURI\" value=\"" + req.getScheme() + "://" + req.getServerName() + req.getRequestURI() +"\">");
				
			p.println("<noscript>");
			p.println("<span style=\"font-size:12px\">스크립트 사용을 할 수 없습니다.<br/>스크립트가 작동하지 않으면 사이트 일부 기능을 사용할 수 없습니다.<p/>");
			p.println("로그인 후 이용할 수 있는 메뉴입니다.<br/></span>");
			p.println("<input type=submit value='로그인 화면으로 이동'>");
			p.println("</noscript>");
			p.println("</form>");
			p.println("<script>document.fm.submit();</script>");
			p.println("</body>");
			p.println("</html>");
		}catch(Exception e){
			ExceptionManager.getInstance().debug(e);
		}
	}
	
	public static String[] reqArray(HttpServletRequest req, String inp, String[] outp){
		String[] ret = outp;		
		if( req.getParameterValues(inp) == null ){
			ret = outp;
		}else{
			ret = req.getParameterValues(inp);
		}
		return ret;
	}
	
	public static String reqArray2Str(HttpServletRequest req, String inp, String[] outp){
		String[] ret = outp;
		String r = "";
		if( req.getParameterValues(inp) == null ){
			ret = outp;
		}else{
			ret = req.getParameterValues(inp);
		}
		for(int i=0; i< ret.length; i++){
			if(r.equals("")){
				r = ret[i].trim();
			}else{
				r = r + "," + ret[i].trim();
			}
		}
		return r;
	}
	
	public static String reqStr(HttpServletRequest req, String inp, String outp){
		String ret = outp;
		if( req.getParameter(inp) == null ){
			ret = outp;
		}else if(req.getParameter(inp).equals("")){
			ret = outp;
		}else{
			ret = req.getParameter(inp);
		}
		return  ret.trim();
	}
	
	public static int reqInt(HttpServletRequest req, String inp, int outp){		
		int ret = outp;
			if( req.getParameter(inp) == null ){
				ret = outp;
			}else if(req.getParameter(inp).equals("")){
				ret = outp;
			}else{
				ret = Integer.parseInt(req.getParameter(inp).trim());
			}
			
		
		return  ret;
	}
	
	public static String IIF(Boolean expression, String ret1, String ret2){
		if( expression){
			return ret1;
		}else{ 
			return ret2;
		}
	}
	
	public static int IIF(Boolean expression, int ret1, int ret2){
		if( expression){
			return ret1;
		}else{ 
			return ret2;
		}
	}
	
}
