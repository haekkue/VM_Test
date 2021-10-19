public class CS
{

/*
아래 예제는 취약한 DES 알고리즘으로 암호화하고 있습니다.
*/
public void bad()
{
	try
	{
		//...

		// 보안약점 : 취약한 DES 사용 
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
아래 예제는 안전하다고 알려진 AES 알고리즘을 사용하고 있습니다.
*/
public void good()
{
	try
	{
		//...

		// 수정 : 안전한 AES 사용
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