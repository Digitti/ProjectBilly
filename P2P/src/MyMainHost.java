import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyMainHost implements MyFrame {

	/**
	 * Point d'entrée pour envoyer une requete vers un pair
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		
		/**
		 * Main cote client 
		 */
		Host h = new Host();
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Veuillez entree le hash ou le nom du fichier : ");
		String hf = sc.nextLine();
		System.out.print("Veuillez entree l'adresse ip du serveur: ");
		String ipServer = sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute du serveur :  ");
		int portServer = sc.nextInt();
		
		System.out.print("Veuillez entreer votre adresse ip : ");
		String ipClient = sc.nextLine();
		System.out.print("Veuillez entrer votre port :  ");
		int portClient = sc.nextInt();
		sc.close();
		
		
		/* Bloc pour faire des test, avec un serveur en thread *//*
		Thread thread = new Thread(){
			public void run(){
			
			/**
			 * Main Cote serveur
			 */
			// lancement de l'ecoute serveur
			/*InetAddress ia;
			try {
				ia = InetAddress.getByName(ip1);
				s.udpServer(Integer.parseInt(port1), ia);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		};
		thread.start();*/
		
		/* renseignement de la requête */
		frameUdpRequest Request = new frameUdpRequest();
		
		Request.RequestType = REQUESTTYPE.NameRequest;		/* pour le moment on choisis une requête de type name */
		
		/* encodage de nom de fichier en UTF-8 */
		byte[] encodedHfWithUTF8 = null;
		try {
			encodedHfWithUTF8 = hf.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Request.nameOrHash = encodedHfWithUTF8;	/* pour le moment on désire télécharger le fichier bd */
		Request.lenght = 1;
		Request.IpType = IPTYPE.IPV4;
		Request.addr = ipServer.getBytes(); // 127.0.0.1
		Request.port = portServer;
		
		/* On transmet la requête */
		h.udpHost(Request);
	}
}
