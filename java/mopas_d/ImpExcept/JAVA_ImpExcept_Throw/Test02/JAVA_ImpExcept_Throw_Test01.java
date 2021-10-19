public class CS
{

public void bad()
{
	try
	{
		// 생략 ...
	}
	catch(Exception e) {
		// 보안약점 : throw 가 없는 new Exception
		new Exception("에러");
   	}
	// 생략 ... 
}

public void bad_2()
{
	try {

		/// 강제로 예외 발생
		throw new Exception(); 
	} catch(Exception e) {
	/// 예외 발생시 처리 부분
	}
}

public void bad_3()
{
	try {
		/// 강제로 예외 발생
		Exception ex = new Exception();
	
		throw ex;
	
	} catch(Exception e) {
	
	/// 예외 발생시 처리 부분
	
	}
	// 생략 ... 
}

public void good()
{
	try
	{
		// 생략 ...
	}
	catch(Exception e) {
		// 수정 : throw 예외 이벤트 발생
		throw new Exception("에러");
   	}
	// 생략 ... 
}

}
