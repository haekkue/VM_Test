public class CS
{



/* 
함수의 파라미터로 fileName에 대한 NULL 여부를 검사하지 않고 File 객체를 생성하였으며, 광범위한 예외 클래스인 Exception을 사용하여 예외처리를 하고 있습니다. 
*/
public void bad(String fileName)
{
	try {						
		// 생략 ...
		File myFile = new File(fileName);
		FileReader fr = new FileReader(myFile);
		// 생략 ...
	}
	// 보안약점 : 광범위한 예외 처리 클래스 사용
	catch (Exception e)		
	{
		IO.writeLine("Caught Exception");
		throw e;
	}
}

public void bad2(String fileName)
{
	try {						
		// 생략 ...
		if (fileName == null) throw new MyException("에러");
		File myFile = new File(fileName);
		FileReader fr = new FileReader(myFile);
		// 생략 ...
	}
	// 수정 : 함수 루틴에서 모든 가능한 예외에 대해서 처리한다.
	catch (Exception e)		
	{
		// 생략 ...
	}
	catch (IOException e)		
	{
		// 생략 ...
	}
}


/*
fileName이 Null 값인지 검사하고, Null이면 에러 메시지를 출력과 예외를 발생시킵니다. 또한 발생 가능한 모든 예외에 대한 구체적인 예외처리를 합니다.
*/
public void good(String fileName) throws FileNotFoundException, IOException, MyException
{
	try {						
		// 생략 ...
		if (fileName == null) throw new MyException("에러");
		File myFile = new File(fileName);
		FileReader fr = new FileReader(myFile);
		// 생략 ...
	}
	// 수정 : 함수 루틴에서 모든 가능한 예외에 대해서 처리한다.
	catch (FileNotFoundException e)		
	{
		// 생략 ...
	}
	catch (IOException e)		
	{
		// 생략 ...
	}
}

}