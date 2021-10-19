public class CS
{
	public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		Connection conn = null;
  		PreparedStatement statement = null;
  		ResultSet rs = null;
  		try {
    			conn = IO.getDBConnection();
    			statement = conn.prepareStatement("select name from users where id=0");
    			rs = statement.executeQuery();
    			data = rs.getString(1); 
  		}

  		if (data != null)
  		{
    			response.sendError(404, "<br>bad(): data = " + data);
  		}
	}

	public void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
  		String data;

  		Connection conn = null;
  		PreparedStatement statement = null;
 		ResultSet rs = null;
  		try {
    			conn = IO.getDBConnection();
    			statement = conn.prepareStatement("select name from users where id=0");
    			rs = statement.executeQuery();
    			data = rs.getString(1); 
  		}

  		if (data != null)
  		{
    			response.sendError(404, "<br>bad(): data = " + data.replaceAll("<", ""));
  		}
	}
}