public class CS
{

	public void xquery_bad(HttpServletRequest request, HttpServletResponse response)
	{
		XQDataSource xqs = new XQDataSource();
		
		XQConnection conn = xqs.getConnection();
		
		String query = "for user in doc(users.xml)//user[username='" + request.getParameter("username") + "'and pass='" + request.getParameter("password") + "'] return user";
		
		XQPreparedExpression xqpe = conn.prepareExpression(query);
		
		XQResultSequence rs = xqpe.executeQuery();
	}
	
	public void bad(Properties props)
	{
	  	// �ܺ� �Է°� 
	  	String name = props.getProperty("name");
	  	Hashtable env = new Hashtable();
	  	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	  	env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");
	  	javax.naming.directory.DirContext ctx = new InitialDirContext(env);
		javax.xml.xquery.XQDataSource xqds =	(javax.xml.xquery.XQDataSource) ctx.lookup("xqj/personnel");
	  	javax.xml.xquery.XQConnection conn = xqds.getConnection();
		
	  	String es = "doc('users.xml')/userlist/user[uname='" + name + "']";
		XQConnection conn = xqs.getConnection();
	  	// �Է°��� Xquery�� ���ڷ� ���
	  	XQPreparedExpression expr = conn.prepareExpression(es);
		// ���Ⱦ��� : �ܺ� �Է°��� �������� �������� ���
	  	XQResultSequence result = expr.executeQuery();
	  	while (result.next()) {
	    		String str = result.getAtomicValue();
	    		if (str.indexOf('>') < 0) {
	      			System.out.println(str);
	    		}
	  	}
	}
}