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
				fis.close();		// ���� �߻��� �ڿ� ���� �� ��
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
 			fis.close();		// ���� �߻� ���ο� ������� �ڿ� ����
		}
	}
}