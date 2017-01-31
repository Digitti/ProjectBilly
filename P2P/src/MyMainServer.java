import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class MyMainServer implements MyFrame {

	
	
	/**
	 * @author KeviN
	 * @param args
	 * Main du programme P2P
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub
		
		/**
		 * Main Cote serveur
		 */
		Tank t = new Tank();
		Server s = new Server();
		
		Scanner sc =  new Scanner(System.in);
		System.out.print("Veuillez entree le repertoire initiale : ");
		String dir =  sc.nextLine();
		t.initialiseTank(dir);
		
		//
		System.out.print("Veuillez entree l'adresse ip de votre machine : ");
		String iplocal =  sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute souhaiter : ");
		String port = sc.nextLine();
		
		System.out.print("Veuillez entree l'adresse ip du client : ");
		String ipclient =  sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute du client : ");
		String portclient = sc.nextLine();
		
		sc.close();
		
		// lancement de l'ecoute serveur
		InetAddress ia;
		InetAddress ib;
		try {
			ia = InetAddress.getByName(iplocal);
			ib = InetAddress.getByName(ipclient);
			s.udpServer(Integer.parseInt(port), ia, Integer.parseInt(portclient), ib);;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
