public class CS
{

/*
외부 입력값을 포트번호로 그대로 사용하고 있습니다. 
만일, 공격자가 Service No의 값으로 -2920 과 같은 값을 지정하면, 기존의 80 포트에서 구동되는 서비스와 충돌되는 문제를 발생시킬 수 있는 취약한 코드입니다. 
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

	// 보안약점 : 외부 입력값이 0 이외의 값은 검증하지 않고 소켓 포트로 사용
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

	// 보안약점 : 외부 입력값이 0 이외의 값은 검증하지 않고 소켓 포트로 사용
	if(port != 0)
		serverSocket = new ServerSocket(Integer.parseInt(props.getProperty("Service No")) + 3000);
	else
		serverSocket = new ServerSocket(def + 3000);
}

/* 
내부자원에 접근할 때 입력값을 포트번호와 같은 식별자로 직접 사용하는 것은 바람직하지 않습니다. 
꼭 필요한 경우엔 다음 코드와 같이 가능한 리스트를 설정하고, 해당 범위 내에서 할당되도록 작성합니다.
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

	// 수정 : 특정값만 소켓 포트로 사용
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