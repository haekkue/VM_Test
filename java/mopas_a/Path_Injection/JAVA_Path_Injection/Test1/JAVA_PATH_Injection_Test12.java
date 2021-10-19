public class CS
{

/*
시스템 입력을 통해 읽은 데이터를 그대로 파일로 저장합니다. 저장되는 파일이 악성코드일 경우 시스템 보안이 취약해 집니다.
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
			
		// 보안약점 : 시스템 입력으로 읽은 파일 인자값이 아무 필터링없이 생성됨
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