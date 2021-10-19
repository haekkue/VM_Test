public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;
  	Socket sock = null;
  	BufferedReader buffread = null;
  	InputStreamReader instrread = null;
  	try {
    		sock = new Socket("host.example.org", 39544);
    		instrread = new InputStreamReader(sock.getInputStream(), "UTF-8");
    		buffread = new BufferedReader(instrread);
    		data = buffread.readLine();
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