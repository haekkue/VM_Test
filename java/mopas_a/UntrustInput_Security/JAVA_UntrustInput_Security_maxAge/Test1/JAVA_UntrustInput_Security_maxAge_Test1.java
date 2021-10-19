public class CS
{

public void makeCookie_bad(HttpServletRequest request) 
{
	String maxAge = request.getParameter("maxAge");
  	if (maxAge.matches("[0-9]+")) {
    		String sessionID = request.getParameter("sesionID");
    		if (sessionID.matches("[A-Z=0-9a-z]+")) {
      			Cookie c = new Cookie("sessionID", sessionID);
      			// ���Ⱦ��� : �ܺ� �Է��� ��Ű ��ȿ���� ������ �״�� ��� �Ǿ���.
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
			// ����
    			if (t > 3600) {
     		       		 t = 3600;
    			}
    			c.setMaxAge(t);
  		}
		// ...
	}
}

}
