
 
import java.util.List;
 
import org.hibernate.Query;
import org.hibernate.Session;

import com.javacodegeeks.utils.HibernateUtil;

public class HibernateExecute
{
    public void execute(String id)
    {
    		
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Query query2 = session.createQuery("from Student where studentId = " + id);
 
        // You can replace the above to commands with this one
        // Query query = session.createQuery("from Student where studentId = 1 ");
        List<?> list = query2.list();
 
        Student student = (Student)list.get(0);
 
        System.out.println(student);
    }
    
    public List execute2(Customer cust)
    {
    		
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Query query2 = session.createQuery("from Student where studentId = " + cust.getCust_name());
 
        // You can replace the above to commands with this one
        // Query query = session.createQuery("from Student where studentId = 1 ");
        List<?> list = query2.list();
 
        //Student student = (Student)list.get(0);
 
        //System.out.println(student);
        
        return list;
    }
}
