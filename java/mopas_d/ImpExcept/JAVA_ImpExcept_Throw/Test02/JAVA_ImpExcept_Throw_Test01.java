public class CS
{

public void bad()
{
	try
	{
		// ���� ...
	}
	catch(Exception e) {
		// ���Ⱦ��� : throw �� ���� new Exception
		new Exception("����");
   	}
	// ���� ... 
}

public void bad_2()
{
	try {

		/// ������ ���� �߻�
		throw new Exception(); 
	} catch(Exception e) {
	/// ���� �߻��� ó�� �κ�
	}
}

public void bad_3()
{
	try {
		/// ������ ���� �߻�
		Exception ex = new Exception();
	
		throw ex;
	
	} catch(Exception e) {
	
	/// ���� �߻��� ó�� �κ�
	
	}
	// ���� ... 
}

public void good()
{
	try
	{
		// ���� ...
	}
	catch(Exception e) {
		// ���� : throw ���� �̺�Ʈ �߻�
		throw new Exception("����");
   	}
	// ���� ... 
}

}
