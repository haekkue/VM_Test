public class CS
{
    /* 
    ������ ����� ������� �н����� ������ ������ DB�� ����˴ϴ�. 
    */
    //private String a = "password";
    
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String a = "password";
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String password1 = request.getParameter(a);
    	PreparedStatement p = null;
    	try {
    		// ...
    		if (!isAuthenticatedUser(username, password)) {
    			throw new MyException("���� ����");
    		}
    		// ���Ⱦ���
    		p = conn.prepareStatement("INSERT INTO employees VALUES(?,?)");
     		p.setString(1,username);
    		p.setString(2,password);
    		p.execute();
    		// ...
    	}
    }
    
    /* 
    �н����� �� �߿� �����͸� �ؽ������� ��ȯ�Ͽ� �����ϰ� �ֽ��ϴ�. 
    */
    public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	PreparedStatement p = null;
    	try {
    		// ...
    		if (!isAuthenticatedUser(username, password)) {
    			throw new MyException("���� ����");
    		}
    
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
    		md.reset();
    		// ...
    		// ���� : �н������ �ؽ� �Լ��� �̿��Ͽ� DB�� ���� 
    		password = md.digest(password.getBytes());
    		p = conn.prepareStatement("INSERT INTO employees VALUES(?,?)");
    		p.setString(1,username);
    		p.setString(2,password);
    		p.execute();
    		// ...
    	}
    }

}