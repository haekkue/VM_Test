public class CS
{
	private Connection globalConn;
/* 
�����ͺ��̽��� ����� �� ��� �� ���ܰ� �߻��ϸ�, �Ҵ�� �����ͺ��̽� Ŀ�ؼ� �� JDBC �ڿ��� ��ȯ���� �ʽ��ϴ�. 
*/
	public void bad() throws SQLException
	{
		Connection conn = null;
		final String url = "jdbc:mysql://127.0.0.1/example?user=root&password=1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			// ...
			conn.close();  // ���ܹ߻��� �Ҵ� �޴� �ڿ��� ��ȯ���� �ʴ´�.
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException occured");
		} catch (SQLException e) {
			System.err.println("SQLException occured");
		} finally {
			// ...
		}
	}
	
	public void bad2() throws SQLException
	{
		Connection conn = null;
		final String url = "jdbc:mysql://127.0.0.1/example?user=root&password=1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			// ...
			//conn.close();  // ���ܹ߻��� �Ҵ� �޴� �ڿ��� ��ȯ���� �ʴ´�.
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException occured");
		} catch (SQLException e) {
			System.err.println("SQLException occured");
		} finally {
			// ...
		}
	}
	
	public Connection good2() throws SQLException
	{
		
		Connection conn = null;
		final String url = "jdbc:mysql://127.0.0.1/example?user=root&password=1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			// ...
			// conn.close();
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException occured");
		} catch (SQLException e) {
			System.err.println("SQLException occured");
		} finally {
		    try {
		        conn.close();    
		    } catch (SQLException e) {
		    }
			// ...
		}
	}

/* 
���ܻ�Ȳ�� �߻��Ͽ� �Լ��� ����� ��, ������ �߻� ���ο� ������� finally ��Ͽ��� �Ҵ���� �ڿ��� ��ȯ�մϴ�. 
*/
	public void good() throws SQLException
	{
		Connection conn = null;
		final String url = "jdbc:mysql://127.0.0.1/example?user=root&password=1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			// ...
			// conn.close();
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException occured");
		} catch (SQLException e) {
			System.err.println("SQLException occured");
		} finally {
			conn.close();	 // ���ܹ߻��ÿ��� �Ҵ� �޴� �ڿ��� ��ȯ�ȴ�.
		}
	}
}