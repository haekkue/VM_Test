public class CS
{
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		Socket sock = null;
  		BufferedReader buffread = null;
  		InputStreamReader instrread = null;
  
  		try {
    			sock = new Socket("host.example.org", 39544); 
    			instrread = new InputStreamReader(sock.getInputStream(), "UTF-8"); 
    			buffread = new BufferedReader(instrread);
    			data = buffread.readLine();
  		}

  		if (data != NULL) {
    			response.sendError(404, "<br>bad(): data = " + data);    
  		}
	}

	public void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		Socket sock = null;
  		BufferedReader buffread = null;
  		InputStreamReader instrread = null;
  
  		try {
    			sock = new Socket("host.example.org", 39544); 
    			instrread = new InputStreamReader(sock.getInputStream(), "UTF-8"); 
    			buffread = new BufferedReader(instrread);
    			data = buffread.readLine();
  		}

  		if (data != null)
  		{
    			response.sendError(404, "<br>bad(): data = " + data.replaceAll("<", ""));
  		}
	}
}