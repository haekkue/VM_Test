public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	data = request.getParameter("name");

  	if (data != null)
  	{
   		URI u;
    		try
    		{
      		u = new URI(data);
    		}
    		catch (URISyntaxException e)
    		{
      		response.getWriter().write("Invalid redirect URL");
      		return;
    		}

		// 보안약점
    		response.sendRedirect(data);
    		return;
  	}
}

}