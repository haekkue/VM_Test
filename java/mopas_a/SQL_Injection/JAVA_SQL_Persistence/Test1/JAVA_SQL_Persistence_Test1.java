public class CS
{
	
	private EntityManager em = entityManagerFactory.createEntityManager();
	private EntityTransaction tx = em.getTransaction();

/*
�����ڰ� �ܺ� �Է°�(id)���� foo'; DROP TABLE MYTABLE; -- �� �ְ� �Ǹ� ������ ���� ���ǹ��� ����Ǿ� ���̺��� �����˴ϴ�.
SELECT OBJECT(i) FROM item i WHERE col1 I.itemNAME = 'foo'; DROP TABLE MYTABLE; --'
*/
public void bad1()
{ 
	try {
      		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

      		String id = props.getProperty("id");

		// ���Ⱦ��� : �ܺ� �Է°��� J2EE Persistence API �������� �������� ���Ǿ� ����
      		Query query = em.createNativeQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + id);
      		List<U9103> items = query.getResultList();
      		//...
    	}
}

public void bad2()
{ 
	try {
      		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

      		String id = System.in.read(in);

		// ���Ⱦ��� : �ܺ� �Է°��� J2EE Persistence API �������� �������� ���Ǿ� ����
      		Query query = em.createNativeQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + id);
      		List<U9103> items = query.getResultList();
      		//...
    	}
}

public void bad3()
{ 
	try {
      		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

		// ���Ⱦ��� : �ܺ� �Է°��� J2EE Persistence API �������� �������� ���Ǿ� ����
      		Query query = em.createNativeQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + props.getProperty("id"));
      		List<U9103> items = query.getResultList();
      		//...
    	}
}

/*
�Ķ���͸� �޴� ������ �����ϰ� �Ķ���͸� �����Ͽ� �����ϵ��� �մϴ�. �̸� ���� �ܺ� �Է°��� ���� ������ �����Ű�� ���� ������ �� �ֽ��ϴ�.
*/
public void good()
{
	try {
      		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

      		String id = props.getProperty("id");

		// ���� : �ܺ� �Է°��� setParameter �޼ҵ带 �̿��Ͽ� �������� ����
      		Query query = em.createNativeQuery("SELECT OBJECT(i) FROM Item i WHERE emID > ?");
      		query.setParameter("id", id);
      		List<U9103> items = query.getResultList();
      		//...
	}
}

}