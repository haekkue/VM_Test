
public class CS {
    
    private int badint = 1;
    private final ReentrantLock relock = new ReentrantLock();
        
    public void free() {
        
        relock.unlock();    
    }   
    
    public void bad() 
    {
        relock.lock(); 
        try{ 
            badint = badint * 2;
            
        } finally {
            //...
        }
    }
    
    public void good() 
    {
	    relock.lock(); 
      
        try{ 
            good1int = good1int * 2;
        } finally {
            // ���� : finally ���ȿ� ���ҽ� unlock()
            free();
        }
    }
}