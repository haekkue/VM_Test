public class CS
{
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = System.getenv("ADD");

  		if (data != null)
  		{
			// 보안약점
    			response.getWriter().println("<br>bad(): data = " + data); 
  		}
	}

	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = System.getenv("ADD");

  		if (data != null)
  		{
    			response.getWriter().println("<br>bad(): data = " + data.replaceAll("<","&lt")); 
  		}
	}
	
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = System.getenv("ADD");
  		data = data.replaceAll("<","&lt");
  		data = data.replaceAll(">","&gt");

  		if (data != null)
  		{
    			response.getWriter().println("<br>bad(): data = " + data); 
  		}
	}
}