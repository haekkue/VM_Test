
public class CS {
    
    private int badint = 1;
    private final ReentrantLock relock = new ReentrantLock();
    private final Lock relock1 = new Lock();
        
    public void bad() 
    {
        relock.lock(); 
        try{ 
            badint = badint * 2;
            // 보안약점 : finally 블럭안에 unlock 이 존재하지 않음 
            relock.unlock();
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
            // 수정 : finally 블럭안에 리소스 unlock()
            relock.unlock();
        }
    }
    public void bad1() 
    {
        relock1.lock(); 
        try{ 
            badint = badint * 2;
            // 보안약점 : finally 블럭안에 unlock 이 존재하지 않음 
            relock1.unlock();
        } finally {
            //...
        }
    }
    
    public void good1() 
    {
	    relock1.lock(); 
      
        try{ 
            good1int = good1int * 2;
        } finally {
            // 수정 : finally 블럭안에 리소스 unlock()
            relock1.unlock();
        }
    }
}