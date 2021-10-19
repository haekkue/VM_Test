public class CS
{


private boolean doLogin_bad(String loginID, char[] password)
  throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
 
  DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
  domFactory.setNamespaceAware(true);
  DocumentBuilder builder = domFactory.newDocumentBuilder();
  Document doc = builder.parse("users.xml");
  String pwd = hashPassword( password);
 
  XPathFactory factory = XPathFactory.newInstance();
  XPath xpath = factory.newXPath();
  XPathExpression expr = xpath.compile("//users/user[login/text()='" +
       loginID + "' and password/text()='" + pwd + "' ]");
  Object result = expr.evaluate(doc, XPathConstants.NODESET);
  NodeList nodes = (NodeList) result;
 
  // Print first names to the console
  for (int i = 0; i < nodes.getLength(); i++) {
    Node node = nodes.item(i).getChildNodes().item(1).getChildNodes().item(0);
    System.out.println( "Authenticated: " + node.getNodeValue());
 	}
  
  return (nodes.getLength() >= 1);
}

private boolean doLogin_bad_1(HttpServletRequest request, HttpServletResponse response)
  throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
 
  DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
  domFactory.setNamespaceAware(true);
  DocumentBuilder builder = domFactory.newDocumentBuilder();
  Document doc = builder.parse("users.xml");
  String pwd = request.getParameter("pwd");
 
  XPathFactory factory = XPathFactory.newInstance();
  XPath xpath = factory.newXPath();
  XPathExpression expr = xpath.compile("//users/user[login/text()='" +
       loginID + "' and password/text()='" + pwd + "' ]");
  Object result = expr.evaluate(doc, XPathConstants.NODESET);
  NodeList nodes = (NodeList) result;
 
  // Print first names to the console
  for (int i = 0; i < nodes.getLength(); i++) {
    Node node = nodes.item(i).getChildNodes().item(1).getChildNodes().item(0);
    System.out.println( "Authenticated: " + node.getNodeValue());
 	}
 	
  return (nodes.getLength() >= 1);
}



private boolean doLogin_good(String loginID, String pwd)
  throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
 
  DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
  domFactory.setNamespaceAware(true);
  DocumentBuilder builder = domFactory.newDocumentBuilder();
  Document doc = builder.parse("users.xml");
 
  XQuery xquery = new XQueryFactory().createXQuery(new File("login.xry"));
  Map queryVars = new HashMap();
  queryVars.put("loginid", loginID);
  queryVars.put("password", pwd);
  NodeList nodes = xquery.execute(doc, null, queryVars).toNodes();
 
  // Print first names to the console
  for (int i = 0; i < nodes.getLength(); i++) {
    Node node = nodes.item(i).getChildNodes().item(1).getChildNodes().item(0);
    System.out.println( node.getNodeValue());
  }
 
  return (nodes.getLength() >= 1);
}

}