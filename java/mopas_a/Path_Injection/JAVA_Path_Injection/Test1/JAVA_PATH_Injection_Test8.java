public class CS
{

	public void bad()
	{
		String path = getInputPath();
		if (path.startsWith("/safe_dir/"))
		{
			// ���Ⱦ��� 
			File f = new File(path); 
			f.delete();
		}
	
	}
}