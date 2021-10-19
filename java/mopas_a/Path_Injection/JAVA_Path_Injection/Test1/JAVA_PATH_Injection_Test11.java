/*
[ 룰 규칙 ]
1. FileInputStream 객체는 안 잡히자만 FileInputStream 객체 생성자에서 바로 외부 입력값이 들어오면 잡음
*/

public class CS
{

/*
외부 입력(name)이 삭제할 파일의 경로설정에 사용되고 있습니다. 
만일 공격자에 의해 name의 값이 ../../../rootFile.txt와 같은 값이면 의도하지 않았던 파일이 삭제되어 시스템에 악영향을 줍니다. 
*/
public void bad1(Properties request)
{
	//...
	String name = request.getProperty("filename");
	if (name != null)
	{
		// 보안약점 : 외부 입력값이 필터링없이 파일 생성인자로 사용
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
		// 보안약점 : 외부 입력값이 필터링없이 파일 생성인자로 사용
		File file = new File("/usr/local/tmp/" + name);
		if (file != null) file.delete();
	}
	//...
}

public void bad2(Properties request)
{
	//...
	// 보안약점 : 외부 입력값이 필터링없이 파일 생성인자로 사용
	File file = new File("/usr/local/tmp/" + request.getProperty("filename"));
	if (file != null) file.delete();
	//...
}

/*
아래 코드는 외부 입력값에 대하여 Null 여부를 체크하고 상대경로를 설정할 수 없도록 replaceAll()을 이용하여 특수문자(/,\,&,. 등)를 제거합니다.
*/
public void good(Properties request)
{
	//...
	String name = request.getProperty("filename");
	if (name != null && !"".equals(name))
	{
		// 수정 : 외부 입력값 필터링
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