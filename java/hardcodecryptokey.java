/*
Java 하드코드된 암호화 예제
*/

class CTest
{
	private Connection conn;

	public String BAD_encryptString(String usr) 
	{
		String seed = "68af404b513073584c4b6f22b6c63e6b";

		try {
			// 상수로 정의된 암호화키를 이용하여 encrypt를 수행한다.
			SecretKeySpec skeySpec = new SecretKeySpec(seed.getBytes(), "AES");

			// 해당 암호화키 기반의 암호화 또는 복호화 업무 수행
		} 
		catch (SQLException e) 
		{ 
			...
		}
		
		return conn;
	}

	public String GOOD_encryptString (String usr) 
	{
		String seed = null;
 
		try {
    			// 암호화 키를 외부환경에서 읽음.
    			seed = getPassword("./password.ini");
    			// 암호화된 암호화 키를 복호화함.
    			seed = decrypt(seed);
    			// 상수로 정의된 암호화키를 이용하여 encrypt를 수행한다.
    			SecretKeySpec skeySpec = new SecretKeySpec(seed.getBytes(), "AES");

    			// 해당 암호화키 기반의 암호화 또는 복호화 업무 수행.
			...
  		} 
		catch (SQLException e) {
      			...
  		}
  		
		return con;
	}
}

