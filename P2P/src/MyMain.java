import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyMain {

	/**
	 * @author KeviN
	 * @param args
	 * 
	 * Main du programme P2P
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server s = new Server();
		Scanner sc =  new Scanner(System.in);
		System.out.print("Veuillez entree l'adresse ip de votre machine : ");
		String ip =  sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute souhaitee : ");
		String port = sc.nextLine();
		sc.close();
		
		InetAddress ia;
		try {
			ia = InetAddress.getByName(ip);
			s.udpServer(Integer.parseInt(port), ia);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
