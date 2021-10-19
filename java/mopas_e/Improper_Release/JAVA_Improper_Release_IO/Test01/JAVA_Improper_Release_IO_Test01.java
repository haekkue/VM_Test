public class CS
{
    public void bad()  
    {
    	Writer writer = new OutputStreamWriter(System.out);
    
    	try
    	{
    		writer.writer("Java World");
    		// ...
    		// 보안약점 : 예외가 발생하면 자원해제 안됨
    		writer.close();
      	}
       	catch( IOException ioe )
       	{
    		IO.logger.log(Level.WARNING, "Error closing Writer", ioe);
    	}
    }
    
    public void good()  
    {
    	Writer writer = new OutputStreamWriter(System.out);
    
    	try
    	{
    		writer.writer("Java World");
    		// ...
      	}
       	catch( IOException ioe )
       	{
    		IO.logger.log(Level.WARNING, "Error closing Writer", ioe);
    	}
    	finally {
    		// 수정 
    		writer.close();
    	}
    }

}