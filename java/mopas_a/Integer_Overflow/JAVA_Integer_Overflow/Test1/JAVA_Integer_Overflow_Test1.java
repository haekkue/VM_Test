public class CS
{
/*
�Ʒ� �ڵ�� �ܺ� �Է� (args[0], args[1])�� �̿��Ͽ� �������� ����� ���� �迭�� ũ��(size)�� �����ϴµ� ����ϰ� �ֽ��ϴ�. 
����, �ܺ� �Է°��� �����÷ο쿡 ���� �������� �Ǹ� �迭 ũ�Ⱑ ������ �Ǿ� �ý��ۿ� ������ �߻��� �� �ֽ��ϴ�.
*/
public void main(String[] args)
{
	int size = new Integer(args[0]).intValue();
	size += new Integer(args[1]).intValue();
	// ���Ⱦ��� : size ������ �����÷ο�Ǹ� �迭 ũ�Ⱑ -1
	MyClass[] data = new MyClass[size];
	//....
}

public void bad_2(HttpServletRequest request, HttpServletResponse response)
{
	int size = Integer.parseInt(request.getParameter("size"));
	size += new Integer(args[1]).intValue();
	// ���Ⱦ��� : size ������ �����÷ο�Ǹ� �迭 ũ�Ⱑ -1
	
	MyClass[] data = new MyClass[size];
	//....
}

public void good_2(HttpServletRequest request, HttpServletResponse response)
{
	int size = Integer.parseInt(request.getParameter("size"));
	size += new Integer(args[1]).intValue();
	// ���Ⱦ��� : size ������ �����÷ο�Ǹ� �迭 ũ�Ⱑ -1
	if (size < 0) return;
	
	if(size < 0){
		return;
	}else{

		MyClass[] data = new MyClass[size];
	}
	//....
}

/*
���� �޸� �Ҵ��� ���� ũ�⸦ ����ϴ� ���, �� ���� �������� �˻��ϴ� �۾��� �ʿ��մϴ�.
*/
public void good(String[] args)
{
	int size = new Integer(args[0]).intValue();
	size += new Integer(args[1]).intValue();
	// ���� : size ������ �������� �˻�
	if (size < 0) return;
	MyClass[] data = new MyClass[size];
	//....
}

}