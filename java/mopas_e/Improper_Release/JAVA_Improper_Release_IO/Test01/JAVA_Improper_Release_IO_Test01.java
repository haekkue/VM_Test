public class CS
{
    public void bad()  
    {
    	Writer writer = new OutputStreamWriter(System.out);
    
    	try
    	{
    		writer.writer("Java World");
    		// ...
    		// ���Ⱦ��� : ���ܰ� �߻��ϸ� �ڿ����� �ȵ�
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
    		// ���� 
    		writer.close();
    	}
    }

}