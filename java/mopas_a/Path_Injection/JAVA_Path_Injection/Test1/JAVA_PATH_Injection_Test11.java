/*
[ �� ��Ģ ]
1. FileInputStream ��ü�� �� �����ڸ� FileInputStream ��ü �����ڿ��� �ٷ� �ܺ� �Է°��� ������ ����
*/

public class CS
{

/*
�ܺ� �Է�(name)�� ������ ������ ��μ����� ���ǰ� �ֽ��ϴ�. 
���� �����ڿ� ���� name�� ���� ../../../rootFile.txt�� ���� ���̸� �ǵ����� �ʾҴ� ������ �����Ǿ� �ý��ۿ� �ǿ����� �ݴϴ�. 
*/
public void bad1(Properties request)
{
	//...
	String name = request.getProperty("filename");
	if (name != null)
	{
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ���� �������ڷ� ���
		File file = new File("/usr/local/tmp/" + name);
		if (file != null) file.delete();
	}
	//...
}

public void bad2(Properties request)
{
	//...
	String name;
	int byteCount = System.in.read( name );
	if (name != null)
	{
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ���� �������ڷ� ���
		File file = new File("/usr/local/tmp/" + name);
		if (file != null) file.delete();
	}
	//...
}

public void bad2(Properties request)
{
	//...
	// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ���� �������ڷ� ���
	File file = new File("/usr/local/tmp/" + request.getProperty("filename"));
	if (file != null) file.delete();
	//...
}

/*
�Ʒ� �ڵ�� �ܺ� �Է°��� ���Ͽ� Null ���θ� üũ�ϰ� ����θ� ������ �� ������ replaceAll()�� �̿��Ͽ� Ư������(/,\,&,. ��)�� �����մϴ�.
*/
public void good(Properties request)
{
	//...
	String name = request.getProperty("filename");
	if (name != null && !"".equals(name))
	{
		// ���� : �ܺ� �Է°� ���͸�
		name = name.replaceAll("/","");
		name = name.replaceAll("\\","");
		name = name.replaceAll(".","");
		name = name.replaceAll("&","");
		name = name + "-report";

		File file = new File("/usr/local/tmp/" + name);
		if (file != null) file.delete();
	}
	//...
}

}