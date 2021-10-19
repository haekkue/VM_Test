
public class CS {
    
    private int badint = 1;
    private final ReentrantLock relock = new ReentrantLock();
    private final Lock relock1 = new Lock();
        
    public void bad() 
    {
        relock.lock(); 
        try{ 
            badint = badint * 2;
            // ���Ⱦ��� : finally ���ȿ� unlock �� �������� ���� 
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
            // ���� : finally ���ȿ� ���ҽ� unlock()
            relock.unlock();
        }
    }
    public void bad1() 
    {
        relock1.lock(); 
        try{ 
            badint = badint * 2;
            // ���Ⱦ��� : finally ���ȿ� unlock �� �������� ���� 
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
            // ���� : finally ���ȿ� ���ҽ� unlock()
            relock1.unlock();
        }
    }
}