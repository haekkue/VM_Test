public class CS
{
	public void bad()
	{
		Socket socket = null;

		try {
			socket = new Socket(machine, daytimeport);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String time = reader.readLine();
			System.out.printf("%s says it is %s %n", machine, time);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
		  		// socket.close();		// �ڿ� ���� �ּ� ó��
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void bad2()
	{
		Socket socket = null;

		try {
			socket = new Socket(machine, daytimeport);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String time = reader.readLine();
			System.out.printf("%s says it is %s %n", machine, time);
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
		  		// socket.close();		// �ڿ� ���� �ּ� ó��
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public Socket bad3()
	{
		Socket socket = null;

		try {
			socket = new Socket(machine, daytimeport);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String time = reader.readLine();
			System.out.printf("%s says it is %s %n", machine, time);
			
			return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
		  		// socket.close();		// �ڿ� ���� �ּ� ó��
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	public good()
	{
		Socket socket = null;
		try {
			socket = new Socket(machine, daytimeport);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String time = reader.readLine();
			System.out.printf("%s says it is %s %n", machine, time);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
		  		socket.close();		// ���ܹ߻��� ������� �ڿ� ����
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}