public class CS
{
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
    	String data;
    
    	if (request.getHeader("Referer") == null) //Ž��1 getHeader
    	{
    		response.getWriter().write("Referer not set");
    		data = "";
    	}
    	else {
    		data = request.getHeader("Referer");
    	}
    
    	// ���Ⱦ��� : referer �ʵ尪�� ������ ���͸����� ���� �񱳰��� ���
    	if (data.equals("http://real.authentication.com/login_success.jsp"))
    	{
    		response.getWriter().write("You are authenticated!");
    		request.getSession(true).setAttribute("authenticated", true);
    	}
    
    }


}