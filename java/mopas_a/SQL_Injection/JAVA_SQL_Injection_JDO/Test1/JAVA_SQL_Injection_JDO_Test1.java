public class CS
{
	/*
	아래 코드는 JDO의 경우를 나타내는 것으로, 공격자가 name 값을 name'; DROP TABLE MYTABLE;--로 입력하게 되면, 수행되는 쿼리는 다음과 같이 변경되고 이 쿼리로 수행되어 테이블이 삭제됩니다. 
	SELECT col1 FROM MYTABLE WHERE name = 'name'; DROP TABLE MYTABLE; --'
	*/
	
	public List<Contact> bad1()
	{
		PersistenceManager pm = getPersistenceManagerFactory().getPersistenceManager();
		String query;
		
		try {
			Properties props = new Properties();
			String fileName = "contacts.txt";
			FileInputStream in = new FileInputStream(fileName);
			
			if (in != null) {
				props.load(in);
			}
			
			in.close();
			String name = props.getProperty("name");
			
			if (name != null) {
				query += " where name '" + name + "'";
			}
		}
		catch (IOException e) {
			//...
		}
		
		return (List<Contact>) 
			pm.newQuery(query).execute();	// 보안약점 : 외부 입력값이 검증없이 JDO 쿼리문에 사용되어 실행
	}
	
	public List<Contact> bad2()
	{
		PersistenceManager pm = getPersistenceManagerFactory().getPersistenceManager();
		String query;
		
		try {
			Properties props = new Properties();
			String fileName = "contacts.txt";
			FileInputStream in = new FileInputStream(fileName);
			
			if (in != null) {
				props.load(in);
			}
			
			in.close();
			String name;
			int byteCount = System.in.read( name );
			
			if (name != null) {
				query += " where name '" + name + "'";
			}
		}
		catch (IOException e) {
			//...
		}
		
		return (List<Contact>) 
			pm.newQuery(query).execute();	// 보안약점 : 외부 입력값이 검증없이 JDO 쿼리문에 사용되어 실행
	}
	
	public List<Contact> bad3()
	{
		PersistenceManager pm = getPersistenceManagerFactory().getPersistenceManager();
		String query;
		
		try {
			Properties props = new Properties();
			String fileName = "contacts.txt";
			FileInputStream in = new FileInputStream(fileName);
			
			if (in != null) {
				props.load(in);
			}
			
			in.close();
		}
		catch (IOException e) {
			//...
		}
		
		return (List<Contact>) 
			pm.newQuery(" where name '" +  props.getProperty("name") + "'").execute();	// 보안약점 : 외부 입력값이 검증없이 JDO 쿼리문에 사용되어 실행
	}
	
	/*
	외부 입력값이 위치하는 부분을 ?로 설정하고 실행시에 해당 파라미터가 전달되도록 수정함으로써 외부의 입력(name)이 쿼리의 구조를 변경시키는 것을 방지할 수 있습니다.
	*/
	public List<Contact> good()
	{
		PersistenceManager pm = getPersistenceManagerFactory().getPersistenceManager();
		String query;
		
		try {
      		Properties props = new Properties();
      		String fileName = "contacts.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		if ( in != null ) { props.load(in); }
      		in.close();

      		String name = props.getProperty("name");
      		
			if (name == null || "".equals(name)) return null;
	
			// 수정 : 실행시 파라미터를 받아서 컴파일된 쿼리문 인자로 사용
			query += " where name = ?";
	
		} catch (IOException e) { 
			//...
		}
	
		return (List<Contact>) 
		    pm.newQuery(query).execute();
	}

}