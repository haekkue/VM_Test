public class CS
{
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		Cookie cookieSources[] = request.getCookies();   // Ž��
  		if (cookieSources != null)
  		{
    			data = cookieSources[0].getValue();    
  		}

  		if (data != NULL) {
			// ���Ⱦ���
     		response.getWriter().println("<br>bad(): data = " + data);
  		}
	}

	public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		Cookie cookieSources[] = request.getCookies();   // Ž��
  		if (cookieSources != null)
  		{
    			data = cookieSources[0].getValue();    
  		}

  		if (data != NULL) {
    			response.getWriter().println("<br>bad(): data = " + data.replaceAll("<","&lt"));
  		}
	}

}