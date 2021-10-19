public class CS
{

/* 파일을 생성할 때 가장 많은 권한을 허가하는 형태로 umask를 사용하고 있엉, 모든 사용자가 읽기/쓰기 권한을 갖게 됩니다. */
public void bad1()
{
 	// 파일 권한: rw-rw-rw-, 디렉토리 권한: rwxrwxrwx

	// 보안약점 : 모든 권한을 허용
  	String cmd = "umask 0";
  	File file = new File("/home/report/report.txt");
  	// ...
  	Runtime.getRuntime().exec(cmd);
}

/* 파일을 생성할 때 가장 많은 권한을 허가하는 형태로 umask를 사용하고 있엉, 모든 사용자가 읽기/쓰기 권한을 갖게 됩니다. */
public void bad2()
{
 	// 파일 권한: rw-rw-rw-, 디렉토리 권한: rwxrwxrwx

	// 보안약점 : 모든 권한을 허용
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


/* 파일에 대한 설정을 가장 제한이 많도록 즉 사용자를 제외하고는 읽기/쓰기가 가능하지 않도록 umask 를 설정하는 것이 필요합니다. */
public void good()
{
	// 수정 
  	String cmd = "umask 77";
  	File file = new File("/home/report/report.txt");
  	// ...
  	Runtime.getRuntime().exec(cmd);
}

}