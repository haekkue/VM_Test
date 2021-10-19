public class CS
{
	public void bad() throws Throwable
	{
  		String encodedPayload = "Y2FsYy5leGU=";
	 	try
	 	{
			Runtime.getRuntime().exec(new String(Base64.decodeBase64(encodedPayload), "UTF-8"));
	 	}
	 	catch( IOException e )
	 	{
			IO.logger.log(Level.WARNING, "Error executing command", e);
	 	}
	}
}