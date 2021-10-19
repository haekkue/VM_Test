public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  String data;
  data = System.getProperty("user.home");
 
  response.getWriter().write(data); 
}

}