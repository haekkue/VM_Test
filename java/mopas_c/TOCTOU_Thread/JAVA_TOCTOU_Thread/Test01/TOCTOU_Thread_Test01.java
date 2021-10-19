/* ������ ���縦 Ȯ���ϴ� �κа� ������ ������ ����ϴ� �κ��� �����ϴ� �������� �ð����� �߻��ϴ� ���, ���Ͽ� ���� ������ �߻��Ͽ� ���α׷��� �������� ���ϴ� ���·� ����� �� �ֽ��ϴ�. �̴� �ð����� �̿��Ͽ� ������ �����ϴ� ���� ���ݿ� ����� �� �ֽ��ϴ�. */
public class FileControl extends Thread
{
	private boolean isDelete;
	private static final String Li  = "aaaaa";
	private String tmp;

	// ���Ⱦ��� : ����ȭ �̻��
	@Override
	public void run()
	{
		String temp = "";
		if (isDelete) {
			temp = "abc";
			System.out.println("File Delete Thread");
			delete();
		} else {
			System.out.println("File Access Thread");
			access();
		}
	}

	public void access() 
	{
		try {
			File f = new File("test1.txt");
			if (f.exist()) {
				BufferedReader br = new BufferedReader(new FileReader(f));
				while (br.ready()) {
					br.readLine();
				}
				br.close();
			}
		}
		// ���� ...
	}

	public void delete() {
		File f = new File("test1.txt");
		if (f.exist()) {
			boolean b = f.delete();
		}
	}

	public synchronized FileControl(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public static void main(String[] args) {
		FileControl accessThread = new FileControl(false);
		FileControl deleteThread = new FileControl(true);
		accessThread.start();
		deleteThread.start();
	}
}