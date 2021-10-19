public class CS
{

public void makeCookie_bad(HttpServletRequest request) 
{
	String maxAge = request.getParameter("maxAge");
  	if (maxAge.matches("[0-9]+")) {
    		String sessionID = request.getParameter("sesionID");
    		if (sessionID.matches("[A-Z=0-9a-z]+")) {
      			Cookie c = new Cookie("sessionID", sessionID);
      			// 보안약점 : 외부 입력이 쿠키 유효시한 설정에 그대로 사용 되었다.
     			c.setMaxAge(Integer.parseInt(maxAge));
    		}
		 // ...
	}
}

public void makeCookie_good(HttpServletRequest request) 
{
	String maxAge = request.getParameter("maxAge");

 	if (maxAge == null || "".equals(maxAge)) return;
  	if (maxAge.matches("[0-9]+")) {
    		String sessionID = request.getParameter("sesionID");
    		if (sessionID == null || "".equals(sessionID)) return;
   	        if (sessionID.matches("[A-Z=0-9a-z]+")) {
    			Cookie c = new Cookie("sessionID", sessionID);
   			int t = Integer.parseInt(maxAge);
			// 수정
    			if (t > 3600) {
     		       		 t = 3600;
    			}
    			c.setMaxAge(t);
  		}
		// ...
	}
}

}
