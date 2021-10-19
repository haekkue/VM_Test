public class CS
{
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = System.getenv("ADD");

  		if (data != null)
  		{
    			response.sendError(404, "<br>bad(): data = " + data); 
  		}
	}

	public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		data = System.getenv("ADD");

  		if (data != null)
  		{
    			response.sendError(404, "<br>bad(): data = " + data.replaceAll("<","&lt")); 
  		}
	}
}