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
		  		// socket.close();		// 磊盔 秦力 林籍 贸府
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
		  		// socket.close();		// 磊盔 秦力 林籍 贸府
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
		  		// socket.close();		// 磊盔 秦力 林籍 贸府
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
		  		socket.close();		// 抗寇惯积苞 惑包绝捞 磊盔 秦力
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}