public class CS
{

/* 
�α��� ������ �߸� �Է��Ͽ��� ��� �ٽ� �Է��� �õ��ϴµ� �־� ������ �����ϴ�. ���� �����ڴ� �������� ��й�ȣ�� ���� ��õ��Ͽ� ��Ȯ�� ��й�ȣ�� �˾Ƴ��� �α��ο� ������ �� �ֽ��ϴ�. 
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
		// ���Ⱦ���
		while (OK == FAIL) {
			// ����� �̸��� �н����带 �Է¹���
			OK = verifyUser(username, password);
		}
	}
	// ...
}

/* 
����� �����õ� Ƚ���� ����ϴ� MAX_ATTEMPTS ������ �����ϰ�, �̸� �ݺ������� �����õ� Ƚ���� �����ϴ� ī���ͷ� ��������ν� ������ ���ݿ� �����մϴ�. 
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
		// ����
		while ((OK == FAIL) && (count < MAX_ATTEMPTS)) {
			// ����� �̸��� �н����带 �Է¹���
			OK = verifyUser(username, password);
			count++;
		}
	}
	// ...
}

}