public class CS
{
   
   public void main(String[] args)
    {
    	int size = new Integer(args[0]).intValue();
    	
    	size += new Integer(args[1]).intValue();
    	
    	// ���Ⱦ��� : size ������ �����÷ο�Ǹ� �迭 ũ�Ⱑ -1
    	MyClass[] data = new MyClass[size];
    	
    	//....
    }
    public void main(String[] args)
    {
    	int size = new Integer(args[0]).intValue();
    	
    	size += new Integer(args[1]).intValue();
    	
    	// ���� : size ������ �������� �˻�
    	if (size < 0) return;
    	
    	MyClass[] data = new MyClass[size];
    	//....
    }
}