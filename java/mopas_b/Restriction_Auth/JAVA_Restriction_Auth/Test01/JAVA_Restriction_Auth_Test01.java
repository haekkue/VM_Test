public class CS
{

/* 
로그인 정보를 잘못 입력하였을 경우 다시 입력을 시도하는데 있어 제한이 없습니다. 따라서 공격자는 여러가지 비밀번호로 인증 재시도하여 정확한 비밀번호를 알아내어 로그인에 성공할 수 있습니다. 
*/
public void bad()
{
	String username;
	String password;
	Socket socket;
	int OK = FAIL; 

	try {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		// ...
		// 보안약점
		while (OK == FAIL) {
			// 사용자 이름과 패스워드를 입력받음
			OK = verifyUser(username, password);
		}
	}
	// ...
}

/* 
사용자 인증시도 횟수를 기록하는 MAX_ATTEMPTS 변수를 정의하고, 이를 반복적으로 인증시도 횟수를 제한하는 카운터로 사용함으로써 무차별 공격에 대응합니다. 
*/
public void login_good()
{
	String username;
	String password;
	Socket socket;
	int OK = FAIL;
	int count = 0;

	try {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		// ...
		// 수정
		while ((OK == FAIL) && (count < MAX_ATTEMPTS)) {
			// 사용자 이름과 패스워드를 입력받음
			OK = verifyUser(username, password);
			count++;
		}
	}
	// ...
}

}