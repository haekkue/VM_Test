/*
1. 하드코드된 패스워드
2. 하드코드된 사용자 계정
3. 하드코드된 URL 

취약점 예제 소스 
*/

class CTest
{
	public Connection BAD_PWID1() 
	{
		String strUrl = "DBServer";
		String strId = "scott";
		String strPw = "tiger";

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection BAD_PWID2(String url) 
	{
		try {
			conn = DriverManager.getConnection(url, "ABC", "ZYX");
			//...
			con = DriverManager.getConnection("jdbc:oracle:thin:@machine_hosting_database:1521:database_name","scott", "tiger");
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection BAD_PWID3(String strUrl) 
	{
		String str1 = "scott";
		String str2 = "tiger";
		String strId = str1;
		String strPw = str2;

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection BAD_PWID4(String strUrl) 
	{
		String str1 = "scott";
		String str2 = "tiger";
		String strId = str1.ToString();
		String strPw = str2.ToString();

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection BAD_PWID5(String strUrl) 
	{
		String str1 = "scott";
		String str2 = "tiger";
		String str3 = str1;
		String str4 = str2;
		String strId = str3;
		String strPw = str4;

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection BAD_PWID6(String strUrl) 
	{
		String str1 = "scott";
		String str2 = "tiger";
		String strId = str1;
		String strPw;

		strPw = str2;

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection BAD_PWID7(String strUrl) 
	{
		String str1 = "scott";
		String str2 = "tiger";
		String strId = str1;
		String strPw;

		strPw = str1 + str2;

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public BAD_URL1()
	{
		conn = DriverManager.getConnection("jdbc:sqlserver://10.10.10.10;databasename=hello;User=test;Password=1234");

		conn = DriverManager.getConnection("jdbc:sqlserver://10.10.10.10;databasename=hello;User=test");

		conn = DriverManager.getConnection("jdbc:sqlserver://10.10.10.10;databasename=hello;Password=1234");

		String url1 = "jdbc:sqlserver://10.10.10.10;databasename=hello;User=test;Password=1234";
		conn = DriverManager.getConnection(url1);

		String url2 = "jdbc:sqlserver://10.10.10.10;databasename=hello;User=test";
		conn = DriverManager.getConnection(url2);

		String url3 = "jdbc:sqlserver://10.10.10.10;databasename=hello;Password=1234";
		conn = DriverManager.getConnection(url3);

		String url4 = "jdbc:oracle:thin:@machine_hosting_database:1521:database_name","scott", "tiger";
		conn = DriverManager.getConnection(url4);
	}

	public BAD_URL2()
	{
		String url1 = "jdbc:sqlserver://10.10.10.10;databasename=hello;User=test;Password=1234";
		String strUrl2 = url1;
		String strUrl3;
		conn = DriverManager.getConnection(strUrl2);

		strUrl3 = url1;
		conn = DriverManager.getConnection(strUrl3);
	}

	public Connection GOOD_PWID1(String strUrl, String strId, String strPw) 
	{
		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public Connection GOOD_PWID2(String strUrl) 
	{
		String strId = strUrl;
		String strPw = strUrl;

		try {
			conn = DriverManager.getConnection(strUrl, strId, strPw);
		} 
		catch (SQLException e) { }
		
		return conn;
	}

	public void GOOD_PWID3()
	{
		Class.forName(prop.getProperty("Globals.DriverClassName"));
		conn = DriverManager.getConnection(prop.getProperty("Globals.Url"),prop.getProperty("Globals.UserName"), prop.getProperty("Globals.Password"));
		conn.setAutoCommit(true);
	}

	public GOOD_URL1()
	{
		conn = DriverManager.getConnection("jdbc:sqlserver://10.10.10.10;databasename=hello");

		String url1 = "jdbc:sqlserver://10.10.10.10;databasename=hello";
		conn = DriverManager.getConnection(url1);
	}

	public void GOOD_URL2()
	{
		Class.forName(prop.getProperty("Globals.DriverClassName"));
		conn = DriverManager.getConnection(prop.getProperty("Globals.Url"));
		conn.setAutoCommit(true);
	}
}

