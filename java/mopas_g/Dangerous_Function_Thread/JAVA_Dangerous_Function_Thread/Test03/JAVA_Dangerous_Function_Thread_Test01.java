class FileAccessThread extends Thread
{
	public synchronized void run()
	{
		BufferedReader br = null;
		//...
	}
}

public class bad extends AbstractTestCase
{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		mainFromParent(args);
	}
	public void bad()
	{
		FileAccessThread fileAccessThread = new FileAccessThread();
		fileAccessThread.start();

		// 보안약점 : 권장되지 않는 Thread.stop 메소드 사용
		fileAccessThread.stop();
	}
}

public class good extends AbstractTestCase
{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		mainFromParent(args);
	}
	public void bad()
	{
		FileAccessThread fileAccessThread = new FileAccessThread();
		fileAccessThread.start();

		// 수정
		fileAccessThread.interrupt();
	}
}