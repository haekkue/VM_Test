import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysema.query.jpa.impl.JPAQuery;

public class QuerydslJpaTest {

	private EntityManagerFactory entityManagerFactory;



	public void test() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		

		QEvent event = QEvent.event;
		JPAQuery query = new JPAQuery(entityManager);
		List<Event> list = query.from(event).list(event);
		System.out.println(list);
		
		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

      		String id = props.getProperty("id");
		
		
		entityManager.createQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + id);

		entityManager.close();
	}
	
	public void test2() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		

		QEvent event = QEvent.event;
		JPAQuery query = new JPAQuery(entityManager);
		List<Event> list = query.from(event).list(event);
		System.out.println(list);
		
		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);

      		String id = props.getProperty("id");
		
		
		entityManager.createQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + id);

		entityManager.close();
	}
	
	public void test3() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		

		QEvent event = QEvent.event;
		JPAQuery query = new JPAQuery(entityManager);
		List<Event> list = query.from(event).list(event);
		System.out.println(list);
		
		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);
      		
		entityManager.createQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + props.getProperty("id"));

		entityManager.close();
	}
	
	public void test4() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		

		QEvent event = QEvent.event;
		JPAQuery query = new JPAQuery(entityManager);
		List<Event> list = query.from(event).list(event);
		System.out.println(list);
		
		Properties props = new Properties();
      		String fileName = "conditions.txt";
      		FileInputStream in = new FileInputStream(fileName);
      		props.load(in);
      		
		entityManagerFactory.createEntityManager().createQuery("SELECT OBJECT(i) FROM Item i WHERE emID > " + props.getProperty("id"));

		entityManager.close();
	}
}