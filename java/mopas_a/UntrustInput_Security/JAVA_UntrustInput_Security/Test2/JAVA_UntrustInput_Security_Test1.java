public class CS
{
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
    	String data;
    
    	if (request.getHeader("Referer") == null) //탐지1 getHeader
    	{
    		response.getWriter().write("Referer not set");
    		data = "";
    	}
    	else {
    		data = request.getHeader("Referer");
    	}
    
    	// 보안약점 : referer 필드값을 적절한 필터링없이 인증 비교값을 사용
    	if (data.equals("http://real.authentication.com/login_success.jsp"))
    	{
    		response.getWriter().write("You are authenticated!");
    		request.getSession(true).setAttribute("authenticated", true);
    	}
    
    }


}