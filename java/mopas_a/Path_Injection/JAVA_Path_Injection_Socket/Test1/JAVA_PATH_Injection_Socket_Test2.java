public class CS
{

/*
�ܺ� �Է°��� ��Ʈ��ȣ�� �״�� ����ϰ� �ֽ��ϴ�. 
����, �����ڰ� Service No�� ������ -2920 �� ���� ���� �����ϸ�, ������ 80 ��Ʈ���� �����Ǵ� ���񽺿� �浹�Ǵ� ������ �߻���ų �� �ִ� ����� �ڵ��Դϴ�. 
*/
public void bad1() throws IOException 
{
	int def = 1000;
	ServerSocket serverSocket;
	Properties props = new Properties();
	String fileName = "file_list";
	FileInputStream in = new FileInputStream(fileName); 
	props.load(in);

	String service = props.getProperty("Service No");
	int port = Integer.parseInt(service);

	// ���Ⱦ��� : �ܺ� �Է°��� 0 �̿��� ���� �������� �ʰ� ���� ��Ʈ�� ���
	if(port != 0)
		serverSocket = new ServerSocket(port + 3000);
	else
		serverSocket = new ServerSocket(def + 3000);
}

public void bad2() throws IOException 
{
	int def = 1000;
	ServerSocket serverSocket;
	Properties props = new Properties();
	String fileName = "file_list";
	FileInputStream in = new FileInputStream(fileName); 
	props.load(in);

	// ���Ⱦ��� : �ܺ� �Է°��� 0 �̿��� ���� �������� �ʰ� ���� ��Ʈ�� ���
	if(port != 0)
		serverSocket = new ServerSocket(Integer.parseInt(props.getProperty("Service No")) + 3000);
	else
		serverSocket = new ServerSocket(def + 3000);
}

/* 
�����ڿ��� ������ �� �Է°��� ��Ʈ��ȣ�� ���� �ĺ��ڷ� ���� ����ϴ� ���� �ٶ������� �ʽ��ϴ�. 
�� �ʿ��� ��쿣 ���� �ڵ�� ���� ������ ����Ʈ�� �����ϰ�, �ش� ���� ������ �Ҵ�ǵ��� �ۼ��մϴ�.
*/
public void good() throws IOException
{
	ServerSocket serverSocket;
	Properties props = new Properties();
	String fileName = "file_list";
	FileInputStream in = new FileInputStream(fileName);
	String service = "";

	if (in != null && in.available() > 0) {
		props.load(in);
		service = props.getProperty("Service No");
  	}

  	if ("".equals(service)) service = "8080";

  	int port = Integer.parseInt(service);

	// ���� : Ư������ ���� ��Ʈ�� ���
  	switch (port) {
    	case 1:
      		port = 3001; break;
    	case 2:
      		port = 3002; break;
    	case 3:
      		port = 3003; break;
    	default:
      		port = 3000;
  	}

  	serverSocket = new ServerSocket(port);
}

public void good2() throws IOException
{
	ServerSocket serverSocket;
	Properties props = new Properties();
	String fileName = "file_list";
	FileInputStream in = new FileInputStream(fileName);
	String service = "";

	if (in != null && in.available() > 0) {
		props.load(in);
		service = props.getProperty("Service No");
  	}

  	if ("".equals(service)) service = "8080";

  	int port = Integer.parseInt(service);

	
    if (port == 3000) {
        serverSocket = new ServerSocket(port);    
    }
  	
}


}