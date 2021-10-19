public class CS
{

	/*
	�Ʒ� ������ ����� �е� ���� RSA �˰����� ����Ͽ� ���α׷��� ������ϰ� ����� ����̴�.
	*/
	public Cipher getCipher()
	{
		Cipher rsa = null;
	
		try
		{
			// ���Ⱦ��� : RSA ���� NoPadding ���
			rsa = javax.crypto.Cipher.getInstance("RSA/NONE/NoPadding");
		} catch (java.security.NoSuchAlgorithmException e) { 
			//���� 
		}
	
		return rsa;
	}
	
	/*
	����ϴ� �˰��� ���� �˷��� �ִ� ������ �е������ ����ؾ� �Ѵ�. 
	���� ��� RSA �˰����� ����ϴ� ��쿡�� OAEP Padding ����� ����ϴ� ���� �ٶ����ϴ�.
	*/
	public Cipher getCipher()
	{
		Cipher rsa = null;
	
		try
		{
			// ����
			rsa = javax.crypto.Cipher.getInstance("RSA/CBC/OAEP");
		}
		catch (java.security.NoSuchAlgorithmException e) { 
			//���� 
		}
		
		return rsa;
	}

}