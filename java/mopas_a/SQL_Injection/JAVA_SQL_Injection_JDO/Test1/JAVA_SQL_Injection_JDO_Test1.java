public class CS
{
	/*
	�Ʒ� �ڵ�� JDO�� ��츦 ��Ÿ���� ������, �����ڰ� name ���� name'; DROP TABLE MYTABLE;--�� �Է��ϰ� �Ǹ�, ����Ǵ� ������ ������ ���� ����ǰ� �� ������ ����Ǿ� ���̺��� �����˴ϴ�. 
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
			pm.newQuery(query).execute();	// ���Ⱦ��� : �ܺ� �Է°��� �������� JDO �������� ���Ǿ� ����
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
			pm.newQuery(query).execute();	// ���Ⱦ��� : �ܺ� �Է°��� �������� JDO �������� ���Ǿ� ����
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
			pm.newQuery(" where name '" +  props.getProperty("name") + "'").execute();	// ���Ⱦ��� : �ܺ� �Է°��� �������� JDO �������� ���Ǿ� ����
	}
	
	/*
	�ܺ� �Է°��� ��ġ�ϴ� �κ��� ?�� �����ϰ� ����ÿ� �ش� �Ķ���Ͱ� ���޵ǵ��� ���������ν� �ܺ��� �Է�(name)�� ������ ������ �����Ű�� ���� ������ �� �ֽ��ϴ�.
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
	
			// ���� : ����� �Ķ���͸� �޾Ƽ� �����ϵ� ������ ���ڷ� ���
			query += " where name = ?";
	
		} catch (IOException e) { 
			//...
		}
	
		return (List<Contact>) 
		    pm.newQuery(query).execute();
	}

}