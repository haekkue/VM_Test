public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;
    	data = System.getenv("URL");
	// ���Ⱦ���
    	response.sendRedirect(data);
    	return;
  }
}

}