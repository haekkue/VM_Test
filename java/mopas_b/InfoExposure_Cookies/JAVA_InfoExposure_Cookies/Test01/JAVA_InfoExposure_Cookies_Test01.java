public class CS
{
	
	
	//�Ʒ� �ڵ�� javax.servlet.http.Cookie.setMaxAge �޼ҵ� ȣ�⿡ �ܺ� �Է°��� ��Ű�� ��ȿ���� ������ �״�� ���Ǿ� ���α׷��� ���Ⱦ����� ����ϴ� �ڵ��Դϴ�.
	
	public void bad2(HttpServletRequest request) 
	{
	  	String maxAge = request.getParameter(maxAge);
	  	if (maxAge.matches("[0-9]+")) {
	        String sessionID = request.getParameter(sesionID);
	        if (sessionID.matches("[A-Z=0-9a-z]+")) {
	            Cookie c = new Cookie(sessionID, sessionID);
				// ���Ⱦ���  �ܺ� �Է°��� ���͸����� ��Ű�� ����
	            c.setMaxAge(Integer.parseInt(maxAge)); 
	    	}
	  	}
	}
	
	public void bad1(HttpServletRequest request) 
	{
	   		String sessionID = request.getParameter(sesionID);
	    		if (sessionID.matches("[A-Z=0-9a-z]+")) {
	      			Cookie c = new Cookie(sessionID, sessionID);
				 //���Ⱦ���  �ܺ� �Է°��� ���͸����� ��Ű�� ����
	      			c.setMaxAge(Integer.parseInt(request.getParameter(maxAge))); 
	    		}
	}
	
	 
	//����ڰ� ��û�� ������ ��Ű�� ��ȿ�ð��� �����ϱ� ���� ����� ��û�� �����ϴ� ������ ������ �ۼ��Ͽ�, �޼ҵ� ȣ�� ���� ȣ���մϴ�. 
	
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