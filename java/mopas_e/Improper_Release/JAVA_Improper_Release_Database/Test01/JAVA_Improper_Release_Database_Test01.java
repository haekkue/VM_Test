public class CS
{
	private Connection globalConn;
/* 
데이터베이스에 연결된 후 사용 중 예외가 발생하면, 할당된 데이터베이스 커넥션 및 JDBC 자원이 반환되지 않습니다. 
*/
	public void bad() throws SQLException
	{
		Connection conn = null;
		final String url = "jdbc:mysql://127.0.0.1/example?user=root&password=1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			// ...
			conn.close();  // 예외발생시 할당 받는 자원이 반환되지 않는다.
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
			//conn.close();  // 예외발생시 할당 받는 자원이 반환되지 않는다.
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
예외상황이 발생하여 함수가 종료될 때, 예외의 발생 여부와 상관없이 finally 블록에서 할당받은 자원을 반환합니다. 
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
			conn.close();	 // 예외발생시에도 할당 받는 자원이 반환된다.
		}
	}
}