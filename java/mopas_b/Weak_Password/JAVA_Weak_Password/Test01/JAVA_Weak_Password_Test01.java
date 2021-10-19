public class CS
{

    /* �н����忡 ���� ���⵵ �������� ���� ���� ó���� �����ϰ� �ֽ��ϴ�. */
    public void doPost_bad1(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
      	try {
        		String id = request.getParameter("id");
        		String passwd = request.getParameter("passwd");
        		// ���Ⱦ��� : �н����� ���⵵ ���� ���� ���� ���� ó��
        		Connection con = DriverManager.getConnection(url, id, passwd);
       
        		// ...
        	} catch (SQLException e) { 
        	    
        	}
    }
    
    /* �н����忡 ���� ���⵵ �������� ���� ���� ó���� �����ϰ� �ֽ��ϴ�. */
    public void doPost_bad2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
      	try {
        		String id = request.getParameter("id");
        		// ���Ⱦ��� : �н����� ���⵵ ���� ���� ���� ���� ó��
        		Connection con = DriverManager.getConnection(url, id, request.getParameter("passwd"));
       
        		// ...
        	} catch (SQLException e) { 
        	    
        	}
    }
    
    
    /* �н����� ���⵵ ���� �� ���� ����ó���� �ؾ� �մϴ�. */
    public void doPost_good(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
      	try {
          		String id = request.getParameter("id");
          		String passwd = request.getParameter("passwd");
        
        		// ����
          		if (passwd == null || "".equals(passwd)) return;
          		if (!passwd.matches("") && passwd.indexOf("@!#") > 4 && passwd.length() > 8) {
          		    
          		}
          		
          		Connection con = DriverManager.getConnection(url, id, passwd);
    
      	} catch (SQLException e) { 
      	    
      	}
    }

}