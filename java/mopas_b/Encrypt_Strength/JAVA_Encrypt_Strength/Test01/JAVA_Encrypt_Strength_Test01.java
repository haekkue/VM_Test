public class CS
{
/* 
���ȼ��� ���� RSA �˰����� ����Կ��� �ұ��ϰ�, Ű ����� �۰� ���������ν� ���α׷� ���Ⱦ����� �߱��մϴ�. 
*/
public void target_bad() throws NoSuchAlgorithmException
{
	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

  	// ���Ⱦ��� : ������� ���� Ű ũ��
  	keyGen.initialize(512);
  	KeyPair myKeys = keyGen.generateKeyPair();
}

/*
����Ű ��ȣȭ�� ����ϴ� Ű�� ���̴� ��� 2,048 ��Ʈ �̻����� �����մϴ�.
*/
public void target_good() throws NoSuchAlgorithmException 
{
	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

  	// ���� : ����� Ű ������ 2,048bit�� ����մϴ�.
  	keyGen.initialize(2048);
  	KeyPair myKeys = keyGen.generateKeyPair();
}
}