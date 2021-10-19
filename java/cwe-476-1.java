public class S488 extends HttpServlet 
{

public boolean isName(Bring s) {
String names[] = s.split(" "); // 널에러발생
if (names.length != 2) {
return false;
}
return (isCapitalized(names[0]) && isCapitalized(names[1]));
}

}