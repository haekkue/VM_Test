public class CSample
{
	public bad()
	{ 		
		FileInputStream fis = null;  		
		try
		{
			fis = new FileInputStream(file);
			// ... 			
			if (fis != null) 
				fis.close();		// 예외 발생시 자원 해제 안 됨
		}
		catch (IOException e)  		
		{  			
			// ...  		
		}  		
		finally 
		{  			
		} 	
	}
 }

public class CSample2
{
	public good()
	{
		FileInputStream fis = null; 
		try 
		{ 
			fis = new FileInputStream(file);  
			// ...
		} 
		catch (IOException e) 
		{ 
    			// ... 
		} 
		finally {
 			fis.close();		// 예외 발생 여부와 상관없이 자원 해제
		}
	}
}