public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
	 String data;
	 Logger log_bad = Logger.getLogger("local-logger");
	
	 data = request.getParameter("name");		
	 response.getWriter().write(data);	
}

}