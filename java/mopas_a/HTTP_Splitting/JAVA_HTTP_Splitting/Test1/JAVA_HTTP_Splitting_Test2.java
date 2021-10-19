public class CS
{

public class CWE113_HTTP_Response_Splitting__console_readLine_addCookieServlet_01 extends AbstractTestCaseServlet
{
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;

        data = ""; /* Initialize data */

        /* read user input from console with readLine */
        try {
            InputStreamReader isr = new InputStreamReader(System.in, "UTF-8");
            BufferedReader buffread = new BufferedReader(isr);

            /* POTENTIAL FLAW: Read data from the console using readLine() */
            data = buffread.readLine();
        }
        catch( IOException ioe )
        {
            IO.logger.log(Level.WARNING, "Error with stream reading", ioe);
        }
        /* NOTE: Tools may report a flaw here because buffread and isr are not closed.  Unfortunately, closing those will close System.in, which will cause any future attempts to read from the console to fail and throw an exception */

        if (data != null)
        {
            Cookie cookieSink = new Cookie("lang", data);
            /* POTENTIAL FLAW: Input not verified before inclusion in the cookie */
            response.addCookie(cookieSink);
        }
    }
}

public class S113 extends HttpServlet 
{
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
  {
    response.setContentType("text/html");

    // 사용자 정보를 읽어온다.
    String author = request.getParameter("authorName");
    if (author == null || "".equals(author)) return;

    // 해더값이 두개로 나뉘어지는 것을 방지하기 위해 외부에서 입력되는 \n과 \r등을 제거한다.
    String filtered_author = author.replaceAll("\r", "").replaceAll("\n", "");
    Cookie cookie = new Cookie("replidedAuthor", filtered_author);
    cookie.setMaxAge(1000);
    cookie.setSecure(true);

    response.addCookie(cookie);
    RequestDispatcher frd = request.getRequestDispatcher("cookieTest.jsp");
    frd.forward(request, response);
  }
}

}