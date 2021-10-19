/*
아래 예제에서는 사용자가 소유하고 있는 item에 대한 정보를 얻어오고 있습니다.
itemname의 값을 name' OR 'a'='a'로 주게 되면, 질의문을 수행한 결과는 SELECT * FROM items를 수행한 결과와 동일하게 됩니다. 
따라서 질의문의 원래 의도와는 다르게 모든 사용자의 모든 item에 대한 정보를 얻을 수 있게 됩니다.
*/
public class ServiceBad extends HttpServlet
{
	private final String COMMAND_PARAM = "command";

	// Command
	private final String GET_USER_INFO_CMD = "get_user_info";

	private final String USER_ID_PARM = "user_id";
	private final String ITEM_NAME_PARM = "itemName";


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String command = request.getParameter(COMMAND_PARAM);
		
		//...
		
		if (command.equals(GET_USER_INFO_CMD))
		{
			String userId = request.getParameter(USER_ID_PARM);
			String itemName = request.getParameter(ITEM_NAME_PARM);

			PersistenceManager pm = factory.getPersistenceManager();

			String sql = "SELECT * FROM items WHERE owner = '" + userId + "' AND itemname = '" + itemName + "'";

			Query query = pm.newQuery(Query.SQL, sql);
			query.setClass(Person.class);
			// 보안약점 
			List people = (List)query.execute();
			//...
		}
		//...
	}
	//...
}

/*
아래 예제에서는 SQL 질의문 생성시 외부에서 입력 받은 문자열을 사용하지 않고, 질의문의 실행시 입력 받은 문자열을 인자로 전달하였습니다.
이렇게 함으로써 외부에서 어떠한 입력이 들어와도 질의문의 의도를 바꾸지 못하게 됩니다.
*/
public class ServiceGood extends HttpServlet
{
	private final String COMMAND_PARAM = "command";

	// Command
	private final String GET_USER_INFO_CMD = "get_user_info";

	private final String USER_ID_PARM = "user_id";

	private final String ITEM_NAME_PARM = "itemName";


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String command = request.getParameter(COMMAND_PARAM);
		
		//...
		
		if (command.equals(GET_USER_INFO_CMD))
		{
			String userId = request.getParameter(USER_ID_PARM);
			String itemName = request.getParameter(ITEM_NAME_PARM);

			PersistenceManager pm = factory.getPersistenceManager();

			// 수정
			String sql = "SELECT * FROM items WHERE owner = ? AND itemname = ?";
			Query query = pm.newQuery(Query.SQL, sql);
			query.setClass(Person.class);

			List people = (List)query.execute(userId, itemName);
			//...
		}
		//...
	}
	//...
}