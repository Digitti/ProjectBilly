import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyMain {

	/**
	 * @author KeviN
	 * @param args
	 * 
	 * Main du programme P2P
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		InetAddress ia;
		try {
			ia = InetAddress.getByName("25.65.128.108");
			server.udpServer(20000, ia);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
