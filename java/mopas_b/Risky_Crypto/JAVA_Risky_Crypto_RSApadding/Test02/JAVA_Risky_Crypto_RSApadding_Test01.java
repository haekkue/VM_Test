public class CS
{

	/*
	아래 예제는 충분한 패딩 없이 RSA 알고리즘을 사용하여 프로그램에 취약점하게 만드는 경우이다.
	*/
	public Cipher getCipher()
	{
		Cipher rsa = null;
	
		try
		{
			// 보안약점 : RSA 사용시 NoPadding 사용
			rsa = javax.crypto.Cipher.getInstance("RSA/NONE/NoPadding");
		} catch (java.security.NoSuchAlgorithmException e) { 
			//…… 
		}
	
		return rsa;
	}
	
	/*
	사용하는 알고리즘에 따라 알려져 있는 적절한 패딩방식을 사용해야 한다. 
	예를 들어 RSA 알고리즘을 사용하는 경우에는 OAEP Padding 방식을 사용하는 것이 바람직하다.
	*/
	public Cipher getCipher()
	{
		Cipher rsa = null;
	
		try
		{
			// 수정
			rsa = javax.crypto.Cipher.getInstance("RSA/CBC/OAEP");
		}
		catch (java.security.NoSuchAlgorithmException e) { 
			//…… 
		}
		
		return rsa;
	}

}