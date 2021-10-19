public class CS
{
	
	private EntityManager em = entityManagerFactory.createEntityManager();
	private EntityTransaction tx = em.getTransaction();

/*
공격자가 외부 입력값(id)으로 foo'; DROP TABLE MYTABLE; -- 을 주게 되면 다음과 같은 질의문이 실행되어 테이블이 삭제됩니다.
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

		// 보안약점 : 외부 입력값이 J2EE Persistence API 쿼리문에 검증없이 사용되어 실행
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

		// 보안약점 : 외부 입력값이 J2EE Persistence API 쿼리문에 검증없이 사용되어 실행
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

		// 보안약점 : 외부 입력값이 J2EE Persistence API 쿼리문에 검증없이 사용되어 실행
      		Query query = em.createNativeQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + props.getProperty("id"));
      		List<U9103> items = query.getResultList();
      		//...
    	}
}

/*
파라미터를 받는 쿼리를 생성하고 파라미터를 설정하여 실행하도록 합니다. 이를 통해 외부 입력값이 쿼리 구조를 변경시키는 것을 방지할 수 있습니다.
*/
public void good()
{
	try {
      		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

      		String id = props.getProperty("id");

		// 수정 : 외부 입력값이 setParameter 메소드를 이용하여 쿼리문에 설정
      		Query query = em.createNativeQuery("SELECT OBJECT(i) FROM Item i WHERE emID > ?");
      		query.setParameter("id", id);
      		List<U9103> items = query.getResultList();
      		//...
	}
}

}