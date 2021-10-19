public class CS
{
    public void xpath_bad()
    {
    	Properties props = new Properties();
    	// 외부 입력값
    	String name = props.getProperty("name");
    	String passwd = props.getProperty("password");
    	// ...
    	XPathFactory factory = XPathFactory.newInstance();
    	XPath xpath = factory.newXPath();
    	// ...
    	// 외부 입력이 xpath의 파라미터로 사용
    	XPathExpression expr = xpath.compile("//users/user[login/text()='" + name + "' and password/text() = '" + password + "']/home_dir/text()");
    	// 보안약점 : 외부 입력값이 검증없이 XPath 쿼리문에 사용
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
    	// 외부 입력값
    	String name = props.getProperty("name");
    	String passwd = props.getProperty("password");
    	Document doc = new Builder().build("users.xml");
    
    	// 수정 : XQuery를 이용해서 XPath Injection 방지
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