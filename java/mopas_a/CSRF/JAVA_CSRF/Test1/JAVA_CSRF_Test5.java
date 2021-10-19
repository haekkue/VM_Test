public class CS
{

public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  String data;

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
  
  response.getWriter().write(data); 
}

}