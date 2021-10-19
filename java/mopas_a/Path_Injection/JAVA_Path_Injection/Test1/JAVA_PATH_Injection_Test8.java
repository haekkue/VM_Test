public class CS
{

	public void bad()
	{
		String path = getInputPath();
		if (path.startsWith("/safe_dir/"))
		{
			// 보안약점 
			File f = new File(path); 
			f.delete();
		}
	
	}
}