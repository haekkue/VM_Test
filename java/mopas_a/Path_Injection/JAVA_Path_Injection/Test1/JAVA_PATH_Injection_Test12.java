public class CS
{

/*
�ý��� �Է��� ���� ���� �����͸� �״�� ���Ϸ� �����մϴ�. ����Ǵ� ������ �Ǽ��ڵ��� ��� �ý��� ������ ����� ���ϴ�.
*/
public bad()
{
	byte inputBuffer[] = new byte[ 128 ];
		
	// Data to write
	byte data[] = { 1,0,1,1,1,1,1,1,0,0,0,0 };
 
	try
	{
		// Read data from the standard input
		int byteCount = System.in.read( inputBuffer );
			
		// Check whether data has been read or not
		if( byteCount <= 0 )
		{
			return;
		}
	
		// Turn data into a String
		String s = new String( inputBuffer );
		s = s.substring( 0, byteCount-2 );
			
		// ���Ⱦ��� : �ý��� �Է����� ���� ���� ���ڰ��� �ƹ� ���͸����� ������
		FileOutputStream f = new FileOutputStream( s );

		try
		{
			// Try to write data in the file
			f.write( data );                             
		}
		catch( IOException e )
		{
			final Logger logger = Logger.getAnonymousLogger();
			String exception = "Exception " + e;
			logger.warning( exception );
		}
		f.close();
	}
	catch( IOException e )
	{
		final Logger logger = Logger.getAnonymousLogger();
		String exception = "Exception " + e;
		logger.warning( exception );
	}
}

}