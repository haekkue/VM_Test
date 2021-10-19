public class CS
{

public void bad() throws Throwable
{
	// ���Ⱦ��� : �ӽ� ������ ���� �������� ����
	File temp = File.createTempFile("temp", "1234");
	BufferedWriter bufOut = new BufferedWriter(new FileWriter(temp.getAbsolutePath()));
	bufOut.write("This is temporary data");
	bufOut.close();
}

private void good() throws Throwable
{
	File temp = File.createTempFile("temp", "1234");

	// ���� : ���� ����
	temp.setWritable(false, true);
	temp.setReadable(true, true);
	temp.setExecutable(false);

	BufferedWriter bufOut = new BufferedWriter(new FileWriter(temp.getAbsolutePath()));
	bufOut.write("This is temporary data");
	bufOut.close();
}
}