public class CS {
	/*
	아래 코드는 bean 객체를 통해 입력받은 외부 입력값이 검증없이 SQL 쿼리문에 사용되는 형태입니다.
	*/
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	
	public static LoginBean bad(LoginBean bean)
	{
		Statement stmt = null;
		String username = bean.getUsername();   // bean 형태로 계정 입력받음
		String password = bean.getPassword(); // bean 형태로 암호 입력받음
		
		
		String searchQuery = "select * from users where uname='" + username + "' AND password='" + password + "'";
		
		
		try
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			
			
			// 보안약점 : bean 외부 입력값 username, password 변수가 검증없이 쿼리문에 사용
			rs = stmt.executeQuery(searchQuery);
			boolean userExists = rs.next();
			//...
		}
		
		//...
	}
}