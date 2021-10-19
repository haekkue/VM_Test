/*
[ 룰 규칙 ]

1. 두번째 "테스트 케이스 2"에서 "[\w\[\]\.]+\.(?:get\w+FileName|getFilesystemName)" 규칙에 의해 getFilesystemName() 메소드를 잡게 했는데 KISA 검증에는 못 잡게하는거 같아서 일단 삭제 

2. 메소드파라미터 탐지는 일단 뺌
*/

public class CS
{

/*
업로드 할 파일에 대한 유효성을 검사하지 않으면, 위험한 유형의 파일을 공격자가 업로드하거나 전송할 수 있습니다.
*/
protected void bad(HttpServletRequest request, HttpServletResponse response) throws ServletException
{
	MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
	String next = (String)mRequest.getFileNames().next();
	MutipartFile file = mRequest.getFile(next);
	
	// 보안약점 : MultipartFile로부터 file을 얻음
	String fileName = file.getOriginalFilename();

	// upload 파일에 대한 확장자 체크를 하지 않음 
	File uploadDir = new File("/app/webappp/data/upload/notice");
	String uplodaFilePath = uploadDir.getAbsolutePath() + "/" + fileName;

	//...
}

/*
업로드 파일의 확장자를 검사하여 허용되지 않은 확장자일 경우 업로드를 제한하고 있으면, 저장시 외부에서 입력된 파일명을 그대로 사용하지 않고 있습니다.
*/
protected void good(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
	String next = (String)mRequest.getFileNames().next();
	MutipartFile file = mRequest.getFile(next);
	if (file == null) return;

	// 수정 : 화이트 방식으로 업로드 파일의 확장자를 체크합니다.
	String fileName = file.getOriginalFilename();
	if (fileName.endsWith(".doc") || fileName.endsWith(".hwp") || fileName.endsWith(".pdf") || fileName.endsWidth(".xls"))
	{
		// file 업로드 루틴 
		//...
	}
	else
		throw new ServletException("허용하지 않는 확장자");

	//...
}

}