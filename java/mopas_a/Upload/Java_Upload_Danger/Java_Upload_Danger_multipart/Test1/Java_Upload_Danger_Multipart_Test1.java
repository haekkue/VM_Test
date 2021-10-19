/*
[ �� ��Ģ ]

1. �ι�° "�׽�Ʈ ���̽� 2"���� "[\w\[\]\.]+\.(?:get\w+FileName|getFilesystemName)" ��Ģ�� ���� getFilesystemName() �޼ҵ带 ��� �ߴµ� KISA �������� �� ����ϴ°� ���Ƽ� �ϴ� ���� 

2. �޼ҵ��Ķ���� Ž���� �ϴ� ��
*/

public class CS
{

/*
���ε� �� ���Ͽ� ���� ��ȿ���� �˻����� ������, ������ ������ ������ �����ڰ� ���ε��ϰų� ������ �� �ֽ��ϴ�.
*/
protected void bad(HttpServletRequest request, HttpServletResponse response) throws ServletException
{
	MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
	String next = (String)mRequest.getFileNames().next();
	MutipartFile file = mRequest.getFile(next);
	
	// ���Ⱦ��� : MultipartFile�κ��� file�� ����
	String fileName = file.getOriginalFilename();

	// upload ���Ͽ� ���� Ȯ���� üũ�� ���� ���� 
	File uploadDir = new File("/app/webappp/data/upload/notice");
	String uplodaFilePath = uploadDir.getAbsolutePath() + "/" + fileName;

	//...
}

/*
���ε� ������ Ȯ���ڸ� �˻��Ͽ� ������ ���� Ȯ������ ��� ���ε带 �����ϰ� ������, ����� �ܺο��� �Էµ� ���ϸ��� �״�� ������� �ʰ� �ֽ��ϴ�.
*/
protected void good(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
	String next = (String)mRequest.getFileNames().next();
	MutipartFile file = mRequest.getFile(next);
	if (file == null) return;

	// ���� : ȭ��Ʈ ������� ���ε� ������ Ȯ���ڸ� üũ�մϴ�.
	String fileName = file.getOriginalFilename();
	if (fileName.endsWith(".doc") || fileName.endsWith(".hwp") || fileName.endsWith(".pdf") || fileName.endsWidth(".xls"))
	{
		// file ���ε� ��ƾ 
		//...
	}
	else
		throw new ServletException("������� �ʴ� Ȯ����");

	//...
}

}