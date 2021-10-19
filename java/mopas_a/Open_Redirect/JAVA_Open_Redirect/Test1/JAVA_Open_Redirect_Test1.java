public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Properties props = new Properties();
  	FileInputStream finstr = null;
  	
	try {
    		finstr = new FileInputStream("../common/config.properties");
    		props.load(finstr);
    		data = props.getProperty("data");
  	}
  	// ...

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
