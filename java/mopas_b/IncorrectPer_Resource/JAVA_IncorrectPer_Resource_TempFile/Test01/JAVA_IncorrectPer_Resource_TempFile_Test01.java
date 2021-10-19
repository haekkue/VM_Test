public class CS
{

public void bad() throws Throwable
{
	// 보안약점 : 임시 파일을 권한 설정없이 생성
	File temp = File.createTempFile("temp", "1234");
	BufferedWriter bufOut = new BufferedWriter(new FileWriter(temp.getAbsolutePath()));
	bufOut.write("This is temporary data");
	bufOut.close();
}

private void good() throws Throwable
{
	File temp = File.createTempFile("temp", "1234");

	// 수정 : 권한 설정
	temp.setWritable(false, true);
	temp.setReadable(true, true);
	temp.setExecutable(false);

	BufferedWriter bufOut = new BufferedWriter(new FileWriter(temp.getAbsolutePath()));
	bufOut.write("This is temporary data");
	bufOut.close();
}
}