public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  String data;
  
  data = System.getenv("ADD"); 
  
  response.getWriter().write(data); 
}

}