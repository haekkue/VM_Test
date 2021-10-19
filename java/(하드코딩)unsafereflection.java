
public class CTest 
{
	public void BAD00()
	{
		Class<?> c = Class.forName(System.getenv("ADD"));	
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	public void BAD01() throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		data = System.getenv("ADD");						// 탐지 - 입력부

		Class<?> c = Class.forName(data);					// 탐지 - 취약점
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	public void BAD02() throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");	

		data = ""; 

		Properties props = new Properties();
		FileInputStream finstr = null;
		try {
			finstr = new FileInputStream("../common/config.properties");
			props.load(finstr);

			data = props.getProperty("data");					// 탐지 - 입력부
		}
		catch( IOException ioe )
		{
			log_bad.warning("Error with stream reading");
		}
		finally {
			try {
				if( finstr != null )
				{
					finstr.close();
				}
			}
			catch( IOException ioe )
			{
				log_bad.warning("Error closing buffread");
			}
		}

		Class<?> c = Class.forName(data);						// 탐지 - 취약점
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	public void BAD03() throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");			

		data = ""; 

		URLConnection conn = (new URL("http://www.example.org/")).openConnection();		
		BufferedReader buffread = null;
		InputStreamReader instrread = null;
		try {

			instrread = new InputStreamReader(conn.getInputStream());
			buffread = new BufferedReader(instrread);

			data = buffread.readLine();									// 탐지 - 입력부 
		}
		
		................

		Class<?> c = Class.forName(data);							// 탐지 - 취약점
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	public void BAD04(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		
		Cookie cookieSources[] = request.getCookies();				// 탐지 - 입력부
		if (cookieSources != null)
		{
			data = cookieSources[0].getValue();					
		}
		else
		{
			data = null;
		}

		Class<?> c = Class.forName(data);								// 탐지 - 취약점
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	public void BAD05(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");	

		
		data = request.getParameter("name");				// 탐지 - 입력부

		Class<?> c = Class.forName(data);					// 탐지 - 취약점
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	public void BAD06(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");	
		data = "";

		
		String id_str = null;
		StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");		// 탐지 - 입력부
		
		.........

		if (id_str != null)
		{
			Connection conn = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				int id = Integer.parseInt(id_str);
				conn = IO.getDBConnection();	
				statement = conn.prepareStatement("select * from pages where id=?");
				
				statement.setInt(1, id);
				rs = statement.executeQuery();
				data = rs.toString();					
			}
			
			..........

		Class<?> c = Class.forName(data);					// 탐지 - 취약점
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}


	private void GOOD01() throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		data = System.getenv("ADD");

		if (!data.equals("Testing.test") && !data.equals("Test.test"))					//불러오는 값이 원하는 것과 같은지 확인하는 부분이 필요
		{
			return;
		}

		Class<?> c = Class.forName(data);
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	private void GOOD02() throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		data = ""; 

		Properties props = new Properties();
		FileInputStream finstr = null;
		try {
			finstr = new FileInputStream("../common/config.properties");
			props.load(finstr);

			data = props.getProperty("data");
		}
		
		..............

		if (!data.equals("Testing.test") && 	!data.equals("Test.test"))						//불러오는 값이 원하는 것과 같은지 확인하는 부분이 필요
		{
			return;
		}

		Class<?> c = Class.forName(data);
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	private void GOOD03() throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		data = ""; 

		URLConnection conn = (new URL("http://www.example.org/")).openConnection();
		BufferedReader buffread = null;
		InputStreamReader instrread = null;
		try {

			instrread = new InputStreamReader(conn.getInputStream());
			buffread = new BufferedReader(instrread);

			data = buffread.readLine();
		}
		
		if (!data.equals("Testing.test") && 	!data.equals("Test.test"))						//불러오는 값이 원하는 것과 같은지 확인하는 부분이 필요
		{
			return;
		}

		Class<?> c = Class.forName(data);
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	private void GOOD04(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		
		Cookie cookieSources[] = request.getCookies();
		if (cookieSources != null)
		{
			data = cookieSources[0].getValue();
		}
		else
		{
			data = null;
		}

		if (!data.equals("Testing.test") &&	!data.equals("Test.test"))							//불러오는 값이 원하는 것과 같은지 확인하는 부분이 필요
		{
			return;
		}

		Class<?> c = Class.forName(data);
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

	private void GOOD05(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");

		
		data = request.getParameter("name");

		if (!data.equals("Testing.test") &&	!data.equals("Test.test"))		//불러오는 값이 원하는 것과 같은지 확인하는 부분이 필요
		{
			return;
		}

		Class<?> c = Class.forName(data);
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());

	}

	private void GOOD06(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;

		Logger log_bad = Logger.getLogger("local-logger");
		data = "";

		
		String id_str = null;
		StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");
	 
		
		...........

		if (id_str != null)
		{
			Connection conn = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			try
			{
				int id = Integer.parseInt(id_str);
				conn = IO.getDBConnection();
				statement = conn.prepareStatement("select * from pages where id=?");
				
				statement.setInt(1, id);
				rs = statement.executeQuery();
				data = rs.toString();
			}
			
			.............

		if (!data.equals("Testing.test") &&						//불러오는 값이 원하는 것과 같은지 확인하는 부분이 필요
				!data.equals("Test.test"))
		{
			return;
		}

		Class<?> c = Class.forName(data);
		Object instance = c.newInstance();

		IO.writeLine(instance.toString());
	}

}
