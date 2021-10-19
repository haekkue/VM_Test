public class CS
{
public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
 // .. omitted ..
		try
		{
			 int id = Integer.parseInt(id_str);
			 conn = IO.getDBConnection();
			 statement = conn.prepareStatement("select * from pages where id=?"); 
			
			 statement.setInt(1, id);
			 rs = statement.executeQuery();
		 	data = rs.toString();
		}
	 // .. omitted ..	
	response.getWriter().write(data);
}


}