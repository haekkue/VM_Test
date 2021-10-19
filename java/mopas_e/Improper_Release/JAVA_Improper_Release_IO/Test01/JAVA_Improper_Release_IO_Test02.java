public class CSample
{
	public void bad()  
	{
		BufferedReader buffread = null;
		InputStreamReader instrread = null;
		
		try
		{
			instrread = new InputStreamReader(System.in, "UTF-8");
			buffread = new BufferedReader(instrread);
	       		String readString = null;

			try
            		{
				readString = buffread.readLine();
		        	IO.writeLine(readString);
				buffread.close();		// 예외 발생시 자원 해제 안 됨
            		}
            		catch( IOException ioe )
            		{
           			IO.logger.log(Level.WARNING, "Error closing BufferedReader", ioe);
            		}
  		}
	}
}

public class CSample2
{
	public void good()  
	{
		BufferedReader buffread = null;
		InputStreamReader instrread = null;
		
		try
		{
			instrread = new InputStreamReader(System.in, "UTF-8");
			buffread = new BufferedReader(instrread);
	       		String readString = null;

			try
            		{
				readString = buffread.readLine();
		        	IO.writeLine(readString);
				//buffread.close();
            		}
            		catch( IOException ioe )
            		{
           			IO.logger.log(Level.WARNING, "Error closing BufferedReader", ioe);
            		}
			finally 
			{
				buffread.close();		// 예외 발생 여부와 상관없이 자원 해제
			}
  		}
	}
}