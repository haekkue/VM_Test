import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
인증을 통과한 사용자의 패스워드 정보가 평문으로 쿠키에 저장됩니다.
*/
/**
* Servlet implementation class SqlInjection
*/
public class ServiceBad extends HttpServlet
{
	private final String COMMAND_PARAM = "command";

	// Command 관련 정의
	private final String LOGIN_CMD = "login";
	private final String USER_ID_PARM = "user_id";
	private final String PASSWORD_PARM = "password";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String command = request.getParameter("command");
		if (command.equals(LOGIN_CMD))
		{
			String userId = request.getParameter(USER_ID_PARM);
			String password = request.getParameter(PASSWORD_PARM);
			//...
			Cookie idCookie = new Cookie("id", userId);
			Cookie passwordCookie = new Cookie("password", password);
			//...

			// 보안약점
			response.addCookie(idCookie);
			response.addCookie(passwordCookie);
		}
		//...
	}
	//...
}


/*
쿠키에 SECURE 옵션을 준 후에 값을 저장하였다. 쿠키에 SECURE 옵션을 주는 경우 SSL통신 - https 사용 시에만 사용 가능하기 때문에 보안성이 높다.
*/
/**
* Servlet implementation class SqlInjection
*/
public class ServiceGood extends HttpServlet
{
	private final String COMMAND_PARAM = "command";

	// Command 관련 정의
	private final String LOGIN_CMD = "login";
	private final String LOGOUT_CMD = "logout";
	private final String OTHER_ACTION_CMD = "other_action";
	private final String USER_ID_PARM = "user_id";
	private final String PASSWORD_PARM = "password";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String command = request.getParameter("command");
		if (command.equals(LOGIN_CMD))
		{
			String userId = request.getParameter(USER_ID_PARM);
			String password = request.getParameter(PASSWORD_PARM);
			//...
			Cookie idCookie = new Cookie("id", userId);
			Cookie passwordCookie = new Cookie("password", password);

			// 수정
			idCookie.setSecure(true);
			passwordCookie.setSecure(true);

			response.addCookie(idCookie);
			response.addCookie(passwordCookie);
		}
		//...
	}
	//...
}
