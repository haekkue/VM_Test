public class CS
{

    /* 
    사용자의 role을 설정할 때 사용자 웹브라우저 쿠키의 role에 할당된 값을 사용하고 있어, 이 값이 사용자에 의해 변경되는 경우 사용자 role값이 의도하지 않은 값으로 할당될 수 있습니다. 
    */
    public void bad(HttpServletRequest request, HttpServletResponse response)
    {
    	Cookie[] cookies = request.getCookies();
    	for (int i=0; cookies != null && i<cookies.length; i++)
    	{
    		Cookie c = cookies[i];
    		if (c.getName().equals("role"))
    		{
    			// 보안약점 : 외부 입력값이 할당
    			userRole = c.getValue();
    		}
    	}
    }
    
    /* 
    사용자 권한, 인증여부 등 보안결정에 사용하는 값은 사용자 입력값을 사용하지 않고 내부 세션값을 활용합니다. 
    */
    public void good(HttpServletRequest request, HttpServletResponse response)
    {
    	// ...
    	// 수정
    	HttpSession session = context.getSession(id);
    	String userRole  = (String)session.getValue("role");
    	// ...
    }
}
