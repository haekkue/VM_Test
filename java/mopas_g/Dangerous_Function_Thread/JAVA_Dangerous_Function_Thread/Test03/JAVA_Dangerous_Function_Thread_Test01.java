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

		// ���Ⱦ��� : ������� �ʴ� Thread.stop �޼ҵ� ���
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

		// ����
		fileAccessThread.interrupt();
	}
}