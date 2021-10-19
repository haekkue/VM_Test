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
������ ����� ������� �н����� ������ ������ ��Ű�� ����˴ϴ�.
*/
/**
* Servlet implementation class SqlInjection
*/
public class ServiceBad extends HttpServlet
{
	private final String COMMAND_PARAM = "command";

	// Command ���� ����
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

			// ���Ⱦ���
			response.addCookie(idCookie);
			response.addCookie(passwordCookie);
		}
		//...
	}
	//...
}


/*
��Ű�� SECURE �ɼ��� �� �Ŀ� ���� �����Ͽ���. ��Ű�� SECURE �ɼ��� �ִ� ��� SSL��� - https ��� �ÿ��� ��� �����ϱ� ������ ���ȼ��� ����.
*/
/**
* Servlet implementation class SqlInjection
*/
public class ServiceGood extends HttpServlet
{
	private final String COMMAND_PARAM = "command";

	// Command ���� ����
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

			// ����
			idCookie.setSecure(true);
			passwordCookie.setSecure(true);

			response.addCookie(idCookie);
			response.addCookie(passwordCookie);
		}
		//...
	}
	//...
}
