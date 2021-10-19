public class CS
{
   
   public void main(String[] args)
    {
    	int size = new Integer(args[0]).intValue();
    	
    	size += new Integer(args[1]).intValue();
    	
    	// 보안약점 : size 변수가 오버플로우되면 배열 크기가 -1
    	MyClass[] data = new MyClass[size];
    	
    	//....
    }
    public void main(String[] args)
    {
    	int size = new Integer(args[0]).intValue();
    	
    	size += new Integer(args[1]).intValue();
    	
    	// 수정 : size 변수가 음수인지 검사
    	if (size < 0) return;
    	
    	MyClass[] data = new MyClass[size];
    	//....
    }
}