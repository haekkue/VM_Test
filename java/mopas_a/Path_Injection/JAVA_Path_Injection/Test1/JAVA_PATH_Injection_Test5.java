public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
    InputStream instream = null;
    OutputStream outstream = null;
    try{
		// ���Ⱦ��� : �ܺ� �Է°��� �������� ���� ������ ���ڷ� ����
        instream = new FileInputStream(request.getParameter("filename")); 
        // ������� ��û���� ���� instream ���� ����
       outstream = response.getOutputStream(); // ��½�Ʈ�� ����
       int c = 0;
       while( (c = instream.read()) != -1)  // �ݺ������� ������ ����
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