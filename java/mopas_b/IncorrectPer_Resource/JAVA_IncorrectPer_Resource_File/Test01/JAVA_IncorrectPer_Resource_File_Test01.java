public class CS
{

public void bad() throws Throwable
{
	String fn = ".\\src\\testcases\\CWE379_File_Creation_in_Insecure_Dir\\insecureDir";
	File dir = new File(fn);
	
	if (dir.exists())
	{
		IO.writeLine("Directory already exists");
		if (dir.delete()) {
			IO.writeLine("Directory deleted");
		}
		else {
			return;
		}
	}
	if (!dir.getParentFile().canWrite())
	{
		IO.writeLine("Cannot write to parent dir");
	}
	try {
		// ���Ⱦ��� : ���� �������� ����
		boolean success = dir.mkdir();
		if ( success )
		{
			IO.writeLine("Directory created");
			File file = new File(dir.getAbsolutePath() + "\\newFile.txt");
			file.createNewFile();
		}
	}
	catch ( Exception e )
	{
		System.out.println(e.getMessage());
	}
}

private void good() throws Throwable
{

	String fn = ".\\src\\testcases\\CWE379_File_Creation_in_Insecure_Dir\\basic\\insecureDir";
	File dir = new File(fn);
	
	if (dir.exists())
	{
		IO.writeLine("Directory already exists");
		if (dir.delete()) {
			IO.writeLine("Directory deleted");
		}
		else 	{
			return;
		}
	}
	if (!dir.getParentFile().canWrite())
	{
		IO.writeLine("Cannot write to parent dir");
	}

	// ���� : ���� ����
	dir.setExecutable(false, true);
	dir.setReadable(true, true);
	dir.setWritable(false, true);
	try {
		boolean success = dir.mkdir();
		if (success)
		{
			IO.writeLine("Directory created");
			File file = new File(dir.getAbsolutePath() + "\\newFile.txt");
			file.createNewFile();
		}
	}
	catch ( Exception e )
	{
		System.out.println(e.getMessage());
	}
}

}