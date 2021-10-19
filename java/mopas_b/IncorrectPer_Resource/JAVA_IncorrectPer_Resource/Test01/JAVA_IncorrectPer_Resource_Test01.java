public class CS
{

/* ������ ������ �� ���� ���� ������ �㰡�ϴ� ���·� umask�� ����ϰ� �־�, ��� ����ڰ� �б�/���� ������ ���� �˴ϴ�. */
public void bad1()
{
 	// ���� ����: rw-rw-rw-, ���丮 ����: rwxrwxrwx

	// ���Ⱦ��� : ��� ������ ���
  	String cmd = "umask 0";
  	File file = new File("/home/report/report.txt");
  	// ...
  	Runtime.getRuntime().exec(cmd);
}

/* ������ ������ �� ���� ���� ������ �㰡�ϴ� ���·� umask�� ����ϰ� �־�, ��� ����ڰ� �б�/���� ������ ���� �˴ϴ�. */
public void bad2()
{
 	// ���� ����: rw-rw-rw-, ���丮 ����: rwxrwxrwx

	// ���Ⱦ��� : ��� ������ ���
  	String cmd = "chmod 777";
  	File file = new File("/home/report/report.txt");
  	// ...
  	Runtime.getRuntime().exec(cmd);
}

public void bad3(){
	String folder = "/home/aaaa"; 
	String cmd = "chmod 777 " + folder; 
	Runtime rt = Runtime.getRuntime(); 
	Process p = rt.exec(cmd); 
	p.waitFor(); 
}

public void bad4(){
	String folder = "/home/aaaa"; 
	String cmd = "chmod 777 " + folder; 
	Runtime rt = Runtime.getRuntime(); 
	Process p = rt.exec("chmod 777 " + folder); 
	p.waitFor(); 
}

public void bad5(){
	String folder = "/home/aaaa"; 
	String cmd = "chmod 777 " + folder; 
	Process process = new ProcessBuilder(cmd).start();
	process.waitFor(); 
}


/* ���Ͽ� ���� ������ ���� ������ ������ �� ����ڸ� �����ϰ�� �б�/���Ⱑ �������� �ʵ��� umask �� �����ϴ� ���� �ʿ��մϴ�. */
public void good()
{
	// ���� 
  	String cmd = "umask 77";
  	File file = new File("/home/report/report.txt");
  	// ...
  	Runtime.getRuntime().exec(cmd);
}

}