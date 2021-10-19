public class CS
{
	protected void bad2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String savePath="/var/www/uploads";		// 업로드 디렉토리 
		int sizeLimit = 5 * 1024 * 1024 ;			// 업로드 파일 사이즈 제한 
		
		try {
			MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy()); 
			Enumeration formNames=multi.getFileNames();
			String formName=(String)formNames.nextElement(); 
			// 보안약점 : MultipartRequest 객체를 통한 파일 업로드시 파일 확장자 필터링하지 않음
			String fileName=multi.getFilesystemName(formName);
			
			if (fileName == null) {
				out.print("Error");
			} 
			else {
				fileName=new String(fileName.getBytes("8859_1"),"euc-kr"); 
				out.print("User Name : " + multi.getParameter("userName") + "<BR>"); 
				out.print("Form Name : " + formName + "<BR>"); 
				out.print("File Name : " + fileName); 
			}
		} catch (InvalidKeyException e) {
			//
		}
	}
	
	protected void good2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String savePath="/var/www/uploads";		// 업로드 디렉토리 
		int sizeLimit = 5 * 1024 * 1024 ;			// 업로드 파일 사이즈 제한 
		
		try {
			MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy()); 
			Enumeration formNames=multi.getFileNames();
			String formName=(String)formNames.nextElement(); 
			String fileName=multi.getFilesystemName(formName);
			
			if (fileName == null) {
				out.print("Error"); 
			}
			else {
				// 수정 : 업로드 파일 확장자 필터링
				String file_ext = fileName.substring(fileName.lastIndexOf('.') + 1); 
				if (!( file_ext.equalsIgnoreCase("hwp") || file_ext.equalsIgnoreCase("pdf") || file_ext.equalsIgnoreCase("jpg")) ) {
					out.print("업로드 금지 파일"); 
					// 파일 삭제
					File uf = new File(savePath, fileName);
					if (uf.exists()) uf.delete();
				}
				else {
					fileName=new String(fileName.getBytes("8859_1"),"euc-kr"); 
					out.print("User Name : " + multi.getParameter("userName") + "<BR>");
					out.print("Form Name : " + formName + "<BR>"); 
					out.print("File Name : " + fileName); 
				}
			}
		} catch (InvalidKeyException e) {
		
		//...
		}
	}
}