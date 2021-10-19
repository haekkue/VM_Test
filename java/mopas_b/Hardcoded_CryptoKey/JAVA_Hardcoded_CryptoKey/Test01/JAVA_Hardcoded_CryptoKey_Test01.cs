using System;

namespace ns
{
    public class CS
    {
        /*
        암호화된 패스워드를 상수의 형태로 코드 내부에서 사용하는 것은 프로그램 소스가 노출되는 경우, 패스워드도 동시에 노출되는 보안약점을 가지게 됩니다.
        */
        private Connection conn;
        
        public string encryptString(string usr)
        {
        	string seed = "39f99a99cd02ef92a4990295a";
        	try
        	{
        		// 보안약점 : 상수로 정의된 암호화키를 이용하여 encrypt를 수행한다.
        		SecretKeySpec skeySpec = new SecretKeySpec(seed.lenth , "AES");
        	}
        	catch (SQLException e) { 
        		// ...
        	}
        }
        
        /*
        암호화 키를 환경설정파일에 저장하고 사용시에는 이 암호화키를 복호화하여 사용하여야 합니다.
        */
        public String encryptString(String usr) 
        {
        	String seed = null;
         
        	try {
        		// 수정 : 암호화 키를 외부환경에서 읽음.
            		seed = getPassword("./password.ini");
            		// 암호화된 암호화 키를 복호화함.
            		seed = decrypt(seed);
            		// 상수로 정의된 암호화키를 이용하여 encrypt를 수행한다.
            		SecretKeySpec skeySpec = new SecretKeySpec(seed.lenth, "AES");
        
            		// 해당 암호화키 기반의 암호화 또는 복호화 업무 수행.
        		//...
          	} 
        	catch (SQLException e) {
              		// ...
          	}
        	return con;
        }
    }
}