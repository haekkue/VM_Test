public class CS
{
    /* 
    인증을 통과한 사용자의 패스워드 정보가 평문으로 DB에 저장됩니다. 
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
    			throw new MyException("인증 에러");
    		}
    		// 보안약점
    		p = conn.prepareStatement("INSERT INTO employees VALUES(?,?)");
     		p.setString(1,username);
    		p.setString(2,password);
    		p.execute();
    		// ...
    	}
    }
    
    /* 
    패스워드 등 중요 데이터를 해쉬값으로 변환하여 저장하고 있습니다. 
    */
    public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	PreparedStatement p = null;
    	try {
    		// ...
    		if (!isAuthenticatedUser(username, password)) {
    			throw new MyException("인증 에러");
    		}
    
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
    		md.reset();
    		// ...
    		// 수정 : 패스워드는 해쉬 함수를 이용하여 DB에 저장 
    		password = md.digest(password.getBytes());
    		p = conn.prepareStatement("INSERT INTO employees VALUES(?,?)");
    		p.setString(1,username);
    		p.setString(2,password);
    		p.execute();
    		// ...
    	}
    }

}