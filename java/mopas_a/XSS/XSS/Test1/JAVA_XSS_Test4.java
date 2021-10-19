public class CS
{
	public void bad1(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = request.getParameter("name"); 

  		if (data != null)
  		{
			// 보안약점
    			response.getWriter().println("<br>bad(): data = " + data); 
  		}
	}

	public void bad2(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
 		{
			// 보안약점
    			response.getWriter().println("<br>bad(): data = " + request.getParameter("name")); 
  		}
	}


	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = request.getParameter("name"); 

  		if (data != null)
  		{
    			response.getWriter().println("<br>bad(): data = " + data.replaceAll("<","&lt")); 
  		}
	}
}