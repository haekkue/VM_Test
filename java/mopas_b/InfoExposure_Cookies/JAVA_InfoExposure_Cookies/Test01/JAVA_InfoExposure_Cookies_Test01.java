public class CS
{
	
	
	//아래 코드는 javax.servlet.http.Cookie.setMaxAge 메소드 호출에 외부 입력값이 쿠키의 유효시한 설정에 그대로 사용되어 프로그램의 보안약점을 약기하는 코드입니다.
	
	public void bad2(HttpServletRequest request) 
	{
	  	String maxAge = request.getParameter(maxAge);
	  	if (maxAge.matches("[0-9]+")) {
	        String sessionID = request.getParameter(sesionID);
	        if (sessionID.matches("[A-Z=0-9a-z]+")) {
	            Cookie c = new Cookie(sessionID, sessionID);
				// 보안약점  외부 입력값이 필터링없이 쿠키에 저장
	            c.setMaxAge(Integer.parseInt(maxAge)); 
	    	}
	  	}
	}
	
	public void bad1(HttpServletRequest request) 
	{
	   		String sessionID = request.getParameter(sesionID);
	    		if (sessionID.matches("[A-Z=0-9a-z]+")) {
	      			Cookie c = new Cookie(sessionID, sessionID);
				 //보안약점  외부 입력값이 필터링없이 쿠키에 저장
	      			c.setMaxAge(Integer.parseInt(request.getParameter(maxAge))); 
	    		}
	}
	
	 
	//사용자가 요청한 값으로 쿠키의 유효시간을 설정하기 전에 사용자 요청을 검증하는 로직을 별도로 작성하여, 메소드 호출 전에 호출합니다. 
	
	public void good(HttpServletRequest request) 
	{
	  	String maxAge = request.getParameter(maxAge);
	  	if (maxAge.matches("[0-9]+")) {
	    		String sessionID = request.getParameter(sesionID);
	    		if (sessionID.matches("[A-Z=0-9a-z]+")) {
	      			Cookie c = new Cookie(sessionID, sessionID);
	
	      			int t = Integer.parseInt(maxAge);
	      			if ( t > 3600 ) {
	        			t = 3600;
	      			}
	
	      			c.setMaxAge(t);
	    		}
	  	}
	}

}