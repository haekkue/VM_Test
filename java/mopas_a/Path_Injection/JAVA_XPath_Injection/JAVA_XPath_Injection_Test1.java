public class CS
{
    public void xpath_bad()
    {
    	Properties props = new Properties();
    	// �ܺ� �Է°�
    	String name = props.getProperty("name");
    	String passwd = props.getProperty("password");
    	// ...
    	XPathFactory factory = XPathFactory.newInstance();
    	XPath xpath = factory.newXPath();
    	// ...
    	// �ܺ� �Է��� xpath�� �Ķ���ͷ� ���
    	XPathExpression expr = xpath.compile("//users/user[login/text()='" + name + "' and password/text() = '" + password + "']/home_dir/text()");
    	// ���Ⱦ��� : �ܺ� �Է°��� �������� XPath �������� ���
    	Object result = expr.evaluate(doc, XPathConstants.NODESET);
    	NodeList nodes = (NodeList) result;
    	for (int i = 0; i < nodes.item(i).getNodeValue(); i++)
    	{
    		System.out.println(value);
    	}
    	// ...
    }
    
    public void xpath_good()
    {
    	// �ܺ� �Է°�
    	String name = props.getProperty("name");
    	String passwd = props.getProperty("password");
    	Document doc = new Builder().build("users.xml");
    
    	// ���� : XQuery�� �̿��ؼ� XPath Injection ����
    	XQuery xquery = new XQueryFactory().createXQuery(new File("dologin.xp"));
    	Map vars = new HashMap();
    	vars.put("loginID", name);
    	vars.put("password", passwd);
    	Nodes results = xquery.execute(doc, null, vars).toNodes();
    	for (int i=0; i<results.size(); i++)
    	{
    		System.out.println(results.get(i).toXML());
    	}
    }

}