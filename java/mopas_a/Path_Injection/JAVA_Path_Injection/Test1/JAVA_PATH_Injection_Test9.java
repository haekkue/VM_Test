public class CS
{

public void bad(Properties cfg) throws IOException 
{
	// ���Ⱦ��� : getProperty�� ���� �ܺ� �Է°��� �������� File ���ڷ� ���
  FileInputStream fis = new  FileInputStream(cfg.getProperty("subject"));
  byte[] arr = new byte[30];
  fis.read(arr);
  System.out.println(arr); 
}

public void good(Properties cfg) throws IOException 
{
  FileInputStream fis;
  String subject = cfg.getProperty("subject");

	// ���� : �ܺ� �Է°� ���͸�
  if (subject.equals("math"))
  fis = new FileInputStream("math");
  else if (subject.equals("physics"))
  fis = new FileInputStream("physics");
  else if (subject.equals("chemistry"))
  fis = new FileInputStream("chemistry");
  else
  fis = new FileInputStream("default");

  byte[] arr = new byte[30];
  fis.read(arr);
  System.out.println(arr);
}

}