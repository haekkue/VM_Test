public class CS
{
    
    public void bad() throws Throwable
    {
        int data;
        data = Integer.MAX_VALUE; 
        
        // ...
        
        // ���Ⱦ���
        int result = (int)(data + 1);
        
        IO.writeLine("result: " + result);
    }
    
    public void good() throws Throwable
    {
        int data;
        data = Integer. MAX_VALUE; /* Initialize data */
        
        // ...
        
        // ����
        if (data < Integer.MAX_VALUE)
        {
            int result = (int)(data + 1);
            IO.writeLine("result: " + result);
        }
        else {
            IO.writeLine("data value is too large to perform addition.");
        }
        
        IO.writeLine("result: " + result);
    }
}