public class CS
{
	protected void bad2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String savePath="/var/www/uploads";		// ���ε� ���丮 
		int sizeLimit = 5 * 1024 * 1024 ;			// ���ε� ���� ������ ���� 
		
		try {
			MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy()); 
			Enumeration formNames=multi.getFileNames();
			String formName=(String)formNames.nextElement(); 
			// ���Ⱦ��� : MultipartRequest ��ü�� ���� ���� ���ε�� ���� Ȯ���� ���͸����� ����
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
		String savePath="/var/www/uploads";		// ���ε� ���丮 
		int sizeLimit = 5 * 1024 * 1024 ;			// ���ε� ���� ������ ���� 
		
		try {
			MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy()); 
			Enumeration formNames=multi.getFileNames();
			String formName=(String)formNames.nextElement(); 
			String fileName=multi.getFilesystemName(formName);
			
			if (fileName == null) {
				out.print("Error"); 
			}
			else {
				// ���� : ���ε� ���� Ȯ���� ���͸�
				String file_ext = fileName.substring(fileName.lastIndexOf('.') + 1); 
				if (!( file_ext.equalsIgnoreCase("hwp") || file_ext.equalsIgnoreCase("pdf") || file_ext.equalsIgnoreCase("jpg")) ) {
					out.print("���ε� ���� ����"); 
					// ���� ����
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