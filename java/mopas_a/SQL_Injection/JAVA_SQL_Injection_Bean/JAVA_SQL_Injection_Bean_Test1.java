public class CS {
	/*
	�Ʒ� �ڵ�� bean ��ü�� ���� �Է¹��� �ܺ� �Է°��� �������� SQL �������� ���Ǵ� �����Դϴ�.
	*/
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	
	public static LoginBean bad(LoginBean bean)
	{
		Statement stmt = null;
		String username = bean.getUsername();   // bean ���·� ���� �Է¹���
		String password = bean.getPassword(); // bean ���·� ��ȣ �Է¹���
		
		
		String searchQuery = "select * from users where uname='" + username + "' AND password='" + password + "'";
		
		
		try
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			
			
			// ���Ⱦ��� : bean �ܺ� �Է°� username, password ������ �������� �������� ���
			rs = stmt.executeQuery(searchQuery);
			boolean userExists = rs.next();
			//...
		}
		
		//...
	}
}