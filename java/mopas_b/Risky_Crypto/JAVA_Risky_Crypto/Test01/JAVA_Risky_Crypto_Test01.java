public class CS
{

/*
�Ʒ� ������ ����� DES �˰������� ��ȣȭ�ϰ� �ֽ��ϴ�.
*/
public void bad()
{
	try
	{
		//...

		// ���Ⱦ��� : ����� DES ��� 
		SecretKeySpec k = new SecretKeySpec(byteKey, "DES");

		Cipher c = Cipher.getInstance("DES");
		c.init(Cipher.ENCRYPT_MODE, k);
		rslt = c.update(msg);
	}
	catch (InvalidKeyException e) {
		// ...
	}
}

/*
�Ʒ� ������ �����ϴٰ� �˷��� AES �˰����� ����ϰ� �ֽ��ϴ�.
*/
public void good()
{
	try
	{
		//...

		// ���� : ������ AES ���
		SecretKeySpec k = new SecretKeySpec(byteKey, "AES");

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, k);
		rslt = c.update(msg);
	}
	catch (InvalidKeyException e) {
		// ...
	}
}

}