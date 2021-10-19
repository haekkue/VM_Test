public class CS
{



/* 
�Լ��� �Ķ���ͷ� fileName�� ���� NULL ���θ� �˻����� �ʰ� File ��ü�� �����Ͽ�����, �������� ���� Ŭ������ Exception�� ����Ͽ� ����ó���� �ϰ� �ֽ��ϴ�. 
*/
public void bad(String fileName)
{
	try {						
		// ���� ...
		File myFile = new File(fileName);
		FileReader fr = new FileReader(myFile);
		// ���� ...
	}
	// ���Ⱦ��� : �������� ���� ó�� Ŭ���� ���
	catch (Exception e)		
	{
		IO.writeLine("Caught Exception");
		throw e;
	}
}

public void bad2(String fileName)
{
	try {						
		// ���� ...
		if (fileName == null) throw new MyException("����");
		File myFile = new File(fileName);
		FileReader fr = new FileReader(myFile);
		// ���� ...
	}
	// ���� : �Լ� ��ƾ���� ��� ������ ���ܿ� ���ؼ� ó���Ѵ�.
	catch (Exception e)		
	{
		// ���� ...
	}
	catch (IOException e)		
	{
		// ���� ...
	}
}


/*
fileName�� Null ������ �˻��ϰ�, Null�̸� ���� �޽����� ��°� ���ܸ� �߻���ŵ�ϴ�. ���� �߻� ������ ��� ���ܿ� ���� ��ü���� ����ó���� �մϴ�.
*/
public void good(String fileName) throws FileNotFoundException, IOException, MyException
{
	try {						
		// ���� ...
		if (fileName == null) throw new MyException("����");
		File myFile = new File(fileName);
		FileReader fr = new FileReader(myFile);
		// ���� ...
	}
	// ���� : �Լ� ��ƾ���� ��� ������ ���ܿ� ���ؼ� ó���Ѵ�.
	catch (FileNotFoundException e)		
	{
		// ���� ...
	}
	catch (IOException e)		
	{
		// ���� ...
	}
}

}