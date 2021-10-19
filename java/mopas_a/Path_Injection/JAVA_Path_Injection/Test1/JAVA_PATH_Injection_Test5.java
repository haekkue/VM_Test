public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
    InputStream instream = null;
    OutputStream outstream = null;
    try{
		// 보안약점 : 외부 입력값이 검증없이 파일 생성자 인자로 사용됨
        instream = new FileInputStream(request.getParameter("filename")); 
        // 사용자의 요청파일 값을 instream 으로 오픈
       outstream = response.getOutputStream(); // 출력스트림 선언
       int c = 0;
       while( (c = instream.read()) != -1)  // 반복적으로 데이터 읽음
       {
           outstream.write(c);
       }
      outstream.flush();
    }catch(Exception e){
           out.println(e.getMessage());
    }finally{
        if(instream!=null) instream.close();
    }
}

}