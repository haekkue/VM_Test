using System;

namespace ns
{
    public class CS
    {
        /*
        ��ȣȭ�� �н����带 ����� ���·� �ڵ� ���ο��� ����ϴ� ���� ���α׷� �ҽ��� ����Ǵ� ���, �н����嵵 ���ÿ� ����Ǵ� ���Ⱦ����� ������ �˴ϴ�.
        */
        private Connection conn;
        
        public string encryptString(string usr)
        {
        	string seed = "39f99a99cd02ef92a4990295a";
        	try
        	{
        		// ���Ⱦ��� : ����� ���ǵ� ��ȣȭŰ�� �̿��Ͽ� encrypt�� �����Ѵ�.
        		SecretKeySpec skeySpec = new SecretKeySpec(seed.lenth , "AES");
        	}
        	catch (SQLException e) { 
        		// ...
        	}
        }
        
        /*
        ��ȣȭ Ű�� ȯ�漳�����Ͽ� �����ϰ� ���ÿ��� �� ��ȣȭŰ�� ��ȣȭ�Ͽ� ����Ͽ��� �մϴ�.
        */
        public String encryptString(String usr) 
        {
        	String seed = null;
         
        	try {
        		// ���� : ��ȣȭ Ű�� �ܺ�ȯ�濡�� ����.
            		seed = getPassword("./password.ini");
            		// ��ȣȭ�� ��ȣȭ Ű�� ��ȣȭ��.
            		seed = decrypt(seed);
            		// ����� ���ǵ� ��ȣȭŰ�� �̿��Ͽ� encrypt�� �����Ѵ�.
            		SecretKeySpec skeySpec = new SecretKeySpec(seed.lenth, "AES");
        
            		// �ش� ��ȣȭŰ ����� ��ȣȭ �Ǵ� ��ȣȭ ���� ����.
        		//...
          	} 
        	catch (SQLException e) {
              		// ...
          	}
        	return con;
        }
    }
}