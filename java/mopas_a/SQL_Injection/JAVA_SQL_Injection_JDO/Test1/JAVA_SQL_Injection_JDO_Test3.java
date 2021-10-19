/*
����� ���̵忡�� "SQL ���� ����: Persistence"��� ��������
JDO ������ �ִ´�. 
*/
public class CS
{
	private Pattern unsecuredCharPattern;
	private final static String UNSECURED_CHAR_REGULAR_EXPRESSION = "[^\\p{Alnum}]|select|delete|update|insert|create|alter|create|drop";

	public void initialize()
	{
		unsecuredCharPattern = Pattern.compile(UNSECURED_CHAR_REGULAR_EXPRESSION,
		Pattern.CASE_INSENSITIVE);
	}

	/*
	�ܺηκ��� �Է¹��� �������� ���� ���� userID�� passwd�� SQL ���ǿ� �״�� ����ϰ� �ֽ��ϴ�.
	*/
	public void applyBad()
	{
		// 1. get a PersistenceManager instance
		PersistenceManager manager = factory.getPersistenceManager();
		System.out.println("The list of available products:");
		
		Properties props = new Properties();

		try
		{
			String userId = props.getProperty("userId");
			String password = props.getProperty("id");

			// clear cache to provoke query against database
			PersistenceBrokerFactory.defaultPersistenceBroker().clearCache();

			// 2. start tx and form query
			manager.currentTransaction().begin();
			String queryStr = "SELECT * FROM members WHERE username= '" + userId + "'AND password = '" + password + "'";
			Query query = manager.newQuery(queryStr);

			// ���Ⱦ���, 3. perform query
			Collection allProducts = (Collection)query.execute();

			// 4. now iterate over the result to print each
			// product and finish tx
			java.util.Iterator iter = allProducts.iterator();
			if (! iter.hasNext())
			{
				System.out.println("No Product entries found!");
			}
			while (iter.hasNext())
			{
				System.out.println(iter.next());
			}
				manager.currentTransaction().commit();
			}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		finally
		{
			manager.close();
		}
	}


	/*
	�ܺηκ��� �Է¹��� userID�� passwd�� ���͸��� SQL ���ǿ� ����ϰ� �ֽ��ϴ�.
	*/
	public void applyGood()
	{
		// 1. get a PersistenceManager instance
		PersistenceManager manager = factory.getPersistenceManager();
		System.out.println("The list of available products:");
		Properties props = new Properties();

		try
		{
			String userId = props.getProperty("userId");
			String password = props.getProperty("id");

			// clear cache to provoke query against database
			PersistenceBrokerFactory.defaultPersistenceBroker().clearCache();

			// 2. start tx and form query
			manager.currentTransaction().begin();
			String queryStr = "SELECT * FROM members WHERE username= '" + makeSecureString(userId) + "' AND password = '" + makeSecureString(password) + "'";
			Query query = manager.newQuery(queryStr);

			// 3. perform query
			Collection allProducts = (Collection)query.execute();

			// 4. now iterate over the result to print each
			// product and finish tx
			java.util.Iterator iter = allProducts.iterator();
			if (! iter.hasNext())
			{
				System.out.println("No Product entries found!");
			}
			while (iter.hasNext())
			{
				System.out.println(iter.next());
			}
			manager.currentTransaction().commit();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		finally
		{
			manager.close();
		}
	}

	private String makeSecureString(final String str, int maxLength)
	{
		String secureStr = str.substring(0, maxLength);
		Matcher matcher = unsecuredCharPattern.matcher(secureStr);
		return matcher.replaceAll("");
	}
}