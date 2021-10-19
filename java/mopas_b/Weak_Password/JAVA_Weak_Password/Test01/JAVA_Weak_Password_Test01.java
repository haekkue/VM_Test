public class CS
{

    /* 패스워드에 대한 복잡도 검증없이 가입 승인 처리를 수행하고 있습니다. */
    public void doPost_bad1(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
      	try {
        		String id = request.getParameter("id");
        		String passwd = request.getParameter("passwd");
        		// 보안약점 : 패스워드 복잡도 검증 없이 가입 승인 처리
        		Connection con = DriverManager.getConnection(url, id, passwd);
       
        		// ...
        	} catch (SQLException e) { 
        	    
        	}
    }
    
    /* 패스워드에 대한 복잡도 검증없이 가입 승인 처리를 수행하고 있습니다. */
    public void doPost_bad2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
      	try {
        		String id = request.getParameter("id");
        		// 보안약점 : 패스워드 복잡도 검증 없이 가입 승인 처리
        		Connection con = DriverManager.getConnection(url, id, request.getParameter("passwd"));
       
        		// ...
        	} catch (SQLException e) { 
        	    
        	}
    }
    
    
    /* 패스워드 복잡도 검증 후 가입 승인처리를 해야 합니다. */
    public void doPost_good(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
      	try {
          		String id = request.getParameter("id");
          		String passwd = request.getParameter("passwd");
        
        		// 수정
          		if (passwd == null || "".equals(passwd)) return;
          		if (!passwd.matches("") && passwd.indexOf("@!#") > 4 && passwd.length() > 8) {
          		    
          		}
          		
          		Connection con = DriverManager.getConnection(url, id, passwd);
    
      	} catch (SQLException e) { 
      	    
      	}
    }

}