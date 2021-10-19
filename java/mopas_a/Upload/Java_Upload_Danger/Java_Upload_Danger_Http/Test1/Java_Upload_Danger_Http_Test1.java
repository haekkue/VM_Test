public class CS
{

protected void bad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
  	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
  	String pLine = br.readLine();
  	String filename =  pLine.substring(pLine.lastIndexOf("\\"), pLine.lastIndexOf("\""));
    String uploadLocation = "/usr/local";
    String boundary = "EOF";
    BufferedWriter bw = new BufferedWriter(new FileWriter(uploadLocation+filename, true));
  	try {
    		
    		for (String line; (line=br.readLine())!=null; )  {
      			if (line.indexOf(boundary) == -1) {
				// 보안약점 : 파일 확장자를 필터링하지 않고 저장 
        			bw.write(line);
        			bw.newLine();
        			bw.flush();
      			}
    		}
    		
    
  	}finally{
  		
  	}
	bw.close();
  	 
}

protected void good(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
  	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	String pLine = br.readLine();
  	String filename =  pLine.substring(pLine.lastIndexOf("\\"), pLine.lastIndexOf("\""));
    String uploadLocation = "/usr/local";
    String boundary = "EOF";

	// 수정 : 파일 확장자 필터링
	String file_ext = filename.substring(filename.lastIndexOf('.') + 1); 
	
	if (!( file_ext.equalsIgnoreCase("hwp") || file_ext.equalsIgnoreCase("pdf") || file_ext.equalsIgnoreCase("jpg")) ){
		response.getWriter().print("업로드 금지 파일입니다."); 
	}else{
  		try {
    			BufferedWriter bw = new BufferedWriter(new FileWriter(uploadLocation+filename, true));
    			for (String line; (line=br.readLine())!=null; )  {
      				if (line.indexOf(boundary) == -1) {
        				bw.write(line);
   		     			bw.newLine();
        				bw.flush();
      				}
    			}
    			
    			bw.close(); 
  		}finally{
  			
  		}
  		
	}
}

}