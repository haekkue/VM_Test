public class CS
{
/*
아래 코드는 외부 입력 (args[0], args[1])을 이용하여 동적으로 계산한 값을 배열의 크기(size)를 결정하는데 사용하고 있습니다. 
만일, 외부 입력값이 오버플로우에 의해 음수값이 되면 배열 크기가 음수가 되어 시스템에 문제가 발생할 수 있습니다.
*/
public void main(String[] args)
{
	int size = new Integer(args[0]).intValue();
	size += new Integer(args[1]).intValue();
	// 보안약점 : size 변수가 오버플로우되면 배열 크기가 -1
	MyClass[] data = new MyClass[size];
	//....
}

public void bad_2(HttpServletRequest request, HttpServletResponse response)
{
	int size = Integer.parseInt(request.getParameter("size"));
	size += new Integer(args[1]).intValue();
	// 보안약점 : size 변수가 오버플로우되면 배열 크기가 -1
	
	MyClass[] data = new MyClass[size];
	//....
}

public void good_2(HttpServletRequest request, HttpServletResponse response)
{
	int size = Integer.parseInt(request.getParameter("size"));
	size += new Integer(args[1]).intValue();
	// 보안약점 : size 변수가 오버플로우되면 배열 크기가 -1
	if (size < 0) return;
	
	if(size < 0){
		return;
	}else{

		MyClass[] data = new MyClass[size];
	}
	//....
}

/*
동적 메모리 할당을 위해 크기를 사용하는 경우, 그 값이 음수인지 검사하는 작업이 필요합니다.
*/
public void good(String[] args)
{
	int size = new Integer(args[0]).intValue();
	size += new Integer(args[1]).intValue();
	// 수정 : size 변수가 음수인지 검사
	if (size < 0) return;
	MyClass[] data = new MyClass[size];
	//....
}

}