public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");
  	while (st.hasMoreTokens())
  	{
    		String token = st.nextToken();
    		if (token.startsWith("id=")) 
    		{
      		data = token.substring(3);
      		break;
    		}
  	}

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