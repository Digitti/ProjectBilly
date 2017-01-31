import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MyMainServer implements MyFrame {

	
	
	/**
	 * @author KeviN
	 * @param args
	 * Main du programme P2P
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * Main Cote serveur
		 */
		
		Server s = new Server();
		Scanner sc =  new Scanner(System.in);
		System.out.print("Veuillez entree l'adresse ip de votre machine : ");
		String ip =  sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute souhaiter : ");
		String port = sc.nextLine();
		
		System.out.print("Veuillez entree l'adresse ip du client : ");
		String ipclient =  sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute du client : ");
		String portclient = sc.nextLine();
		
		sc.close();
		
		String sendFile = "C:\\Users\\KeviN\\Desktop\\test.img";
		s.tcpServer(ip, Integer.parseInt(port), sendFile);;
		
		// lancement de l'ecoute serveur
		/*InetAddress ia;
		try {
			ia = InetAddress.getByName(ip);
			s.udpServer(Integer.parseInt(port), ia);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
