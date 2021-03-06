public class CS
{
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");   // 입력
  		while (st.hasMoreTokens())
  		{
    			String token = st.nextToken(); 
    			if (token.startsWith("id="))  
    			{
      			data = token.substring(3); 
      			break;
    			}
  		}

  		if (data != null) {
			// 보안약점
      		response.getWriter().println("<br>bad(): data = " + data);
  		}
	}

	private void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");   // 입력
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
    			response.getWriter().println("<br>bad(): data = " + data.replaceAll("<", ""));
  		}
	}
}
