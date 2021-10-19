public class CS
{  
    public void bad() throws Throwable
    {
        int data;
        data = Integer.MIN_VALUE;
        
        String s_data = System.getProperty("user.home"); 
        try {
        	data = Integer.parseInt(s_data.trim());
        } 
        catch( IOException ioe )
        {
            IO.logger.log(Level.WARNING, "Error with stream reading", ioe);
        }
        // ...
        
        // 보안약점
        int result = (int)(data + 1);
        
        IO.writeLine("result: " + result);
    }
    
    public void good() throws Throwable
    {
        int data;
        data = Integer.MIN_VALUE; /* Initialize data */
        
        String s_data = System.getProperty("user.home");
        try {
        	data = Integer.parseInt(s_data.trim());
        }
        catch( IOException ioe )
        {
            IO.logger.log(Level.WARNING, "Error with stream reading", ioe);
        }
        // ...
        
        // 수정
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