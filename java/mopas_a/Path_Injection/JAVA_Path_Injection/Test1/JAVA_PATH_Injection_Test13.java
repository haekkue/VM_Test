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

		String name = req.getParameter("name");   // 탐지(1)
		String msg = req.getParameter("msg");     // 탐지(2)
		if(name != null) {
			try {
				File f = new File("/tmp", name);		/* BAD */  // 탐지(3) 파일객체 변수입력추적
				if(msg != null) {
					FileWriter fw = new FileWriter(f);	/* BAD */   // 탐지(4) 파일객체로 변수쓰기
					fw.write(msg, 0, msg.length());
					fw.close();
					out.println("message stored");
				} else {
					String line;
					BufferedReader fr = new BufferedReader(new FileReader(f));	/* BAD */ // 또는 탐지(5) 파일객체로 변수읽기
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