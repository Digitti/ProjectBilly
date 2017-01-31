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
		sc.close();
		
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
