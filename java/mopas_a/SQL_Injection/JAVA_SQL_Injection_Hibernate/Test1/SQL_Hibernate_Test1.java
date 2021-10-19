
 
import java.util.List;
 
import org.hibernate.Query;
import org.hibernate.Session;

import com.javacodegeeks.utils.HibernateUtil;

public class Hibernate
{
    public static void main(HttpServletRequest request)
    {
    		
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        String id = request.getParameter("id");
        
        Customer cust = new Customer();
        
        cust.setCust_name(id);
 
        Query query = session.createQuery("from Student where studentId = :id ");
        query.setParameter("id", id);
        
        
	      Query query2 = session.createQuery("from Student where studentId = " + id);
	      
	      Query query3 = HibernateUtil.getSessionFactory().openSession().createQuery("from Student where studentId = " + id);
 
				HibernateExecute he = new HibernateExecute();
				he.execute(id);
				
				he.execute2(cust);
				
				
				
        List<?> list = query.list();
 
        Student student = (Student)list.get(0);
 
        System.out.println(student);
    }
}
