public class CS
{

public void bad(Properties cfg) throws IOException 
{
	// 보안약점 : getProperty로 읽은 외부 입력값이 검증없이 File 인자로 사용
  FileInputStream fis = new  FileInputStream(cfg.getProperty("subject"));
  byte[] arr = new byte[30];
  fis.read(arr);
  System.out.println(arr); 
}

public void good(Properties cfg) throws IOException 
{
  FileInputStream fis;
  String subject = cfg.getProperty("subject");

	// 안전 : 외부 입력값 필터링
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