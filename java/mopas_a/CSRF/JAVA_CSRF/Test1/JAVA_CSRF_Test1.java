public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  String data;
  Cookie cookieSources[] = request.getCookies();   
 
  if (cookieSources != null)
  {
    data = cookieSources[0].getValue();    
  }
  
  response.getWriter().write(data); 
}

}