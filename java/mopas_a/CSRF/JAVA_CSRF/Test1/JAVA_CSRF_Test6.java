public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  String data;

  StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");
  while (st.hasMoreTokens())
  {
     String token = st.nextToken(); /* a token will be like "id=foo" */
     if(token.startsWith("id="))   /* check if we have the "id" parameter" */
     {
        data = token.substring(3); /* set data to "foo" */
        break; /* exit while loop */
     }
  }
  
  response.getWriter().write(data); 
}

}