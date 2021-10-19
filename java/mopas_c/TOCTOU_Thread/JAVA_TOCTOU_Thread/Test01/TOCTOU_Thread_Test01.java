/* 파일의 존재를 확인하는 부분과 실제로 파일을 사용하는 부분을 실행하는 과정에서 시간차가 발생하는 경우, 파일에 대한 삭제가 발생하여 프로그램이 예상하지 못하는 형태로 수행될 수 있습니다. 이는 시간차를 이용하여 파일을 변경하는 등의 공격에 취약할 수 있습니다. */
public class FileControl extends Thread
{
	private boolean isDelete;
	private static final String Li  = "aaaaa";
	private String tmp;

	// 보안약점 : 동기화 미사용
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
		// 생략 ...
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