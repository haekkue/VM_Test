public class CS
{
/* 
보안성이 강한 RSA 알고리즘을 사용함에도 불구하고, 키 사이즈를 작게 설정함으로써 프로그램 보안약점을 야기합니다. 
*/
public void target_bad() throws NoSuchAlgorithmException
{
	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

  	// 보안약점 : 충분하지 않은 키 크기
  	keyGen.initialize(512);
  	KeyPair myKeys = keyGen.generateKeyPair();
}

/*
공개키 암호화에 사용하는 키의 길이는 적어도 2,048 비트 이상으로 설정합니다.
*/
public void target_good() throws NoSuchAlgorithmException 
{
	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

  	// 수정 : 충분한 키 길이인 2,048bit를 사용합니다.
  	keyGen.initialize(2048);
  	KeyPair myKeys = keyGen.generateKeyPair();
}
}