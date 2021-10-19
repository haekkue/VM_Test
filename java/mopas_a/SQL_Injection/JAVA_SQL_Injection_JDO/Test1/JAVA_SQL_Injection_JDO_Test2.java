/*
�Ʒ� ���������� ����ڰ� �����ϰ� �ִ� item�� ���� ������ ������ �ֽ��ϴ�.
itemname�� ���� name' OR 'a'='a'�� �ְ� �Ǹ�, ���ǹ��� ������ ����� SELECT * FROM items�� ������ ����� �����ϰ� �˴ϴ�. 
���� ���ǹ��� ���� �ǵ��ʹ� �ٸ��� ��� ������� ��� item�� ���� ������ ���� �� �ְ� �˴ϴ�.
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
			// ���Ⱦ��� 
			List people = (List)query.execute();
			//...
		}
		//...
	}
	//...
}

/*
�Ʒ� ���������� SQL ���ǹ� ������ �ܺο��� �Է� ���� ���ڿ��� ������� �ʰ�, ���ǹ��� ����� �Է� ���� ���ڿ��� ���ڷ� �����Ͽ����ϴ�.
�̷��� �����ν� �ܺο��� ��� �Է��� ���͵� ���ǹ��� �ǵ��� �ٲ��� ���ϰ� �˴ϴ�.
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

			// ����
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