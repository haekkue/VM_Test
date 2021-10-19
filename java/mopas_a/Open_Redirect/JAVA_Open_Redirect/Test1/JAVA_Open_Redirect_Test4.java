public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;
    	data = System.getenv("URL");
	// 보안약점
    	response.sendRedirect(data);
    	return;
  }
}

}