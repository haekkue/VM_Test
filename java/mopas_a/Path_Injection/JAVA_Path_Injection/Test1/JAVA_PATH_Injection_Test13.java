import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*; 

public class File1_bad extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    	throws ServletException, IOException
    {
        res.setContentType("text/html");
        ServletOutputStream out = res.getOutputStream();
        out.println("<HTML><HEAD><TITLE>Test</TITLE></HEAD><BODY><blockquote><pre>");

		String name = req.getParameter("name");   // Ž��(1)
		String msg = req.getParameter("msg");     // Ž��(2)
		if(name != null) {
			try {
				File f = new File("/tmp", name);		/* BAD */  // Ž��(3) ���ϰ�ü �����Է�����
				if(msg != null) {
					FileWriter fw = new FileWriter(f);	/* BAD */   // Ž��(4) ���ϰ�ü�� ��������
					fw.write(msg, 0, msg.length());
					fw.close();
					out.println("message stored");
				} else {
					String line;
					BufferedReader fr = new BufferedReader(new FileReader(f));	/* BAD */ // �Ǵ� Ž��(5) ���ϰ�ü�� �����б�
					while((line = fr.readLine()) != null)
						out.println(line);
				}
			} catch(Exception e) {
				throw new ServletException(e);
			}
		} else {
			out.println("specify a name and an optional msg");
		}

        out.println("</pre></blockquote></BODY></HTML>");
        out.close();
    }
}