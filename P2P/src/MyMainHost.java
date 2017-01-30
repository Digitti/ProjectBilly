import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyMainHost implements MyFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		
		/**
		 * Main cote client 
		 */
		Host h = new Host();
		Scanner sc = new Scanner(System.in);
		
		/* lancement du serveur pour test */
		Server s = new Server();
		Scanner sc1 =  new Scanner(System.in);
		
		String hf = new String("bd");
		String ip = "127002";
		byte[] adresse = new byte[4];
		adresse[0] = 127;
		adresse[1] = 0;
		adresse[2] = 0;
		adresse[3] = 1;
		int port = 10005;
		//System.out.print("Veuillez entree le hash ou le nom du fichier : ");
		//String hf = sc.nextLine();
		//System.out.print("Veuillez entree l'adresse ip du votre serveur demander : ");
		//String ip = sc.nextLine();
		//System.out.print("Veuillez entree le port d'ecoute souhaiter :  ");
		//int port = sc.nextInt();
		//sc.close();
		
		/* lancement du serveur pour test */
		String ip1 = "127.0.0.1";
		String port1 = "10005";
		//System.out.print("Veuillez entree l'adresse ip de votre machine : ");
		//String ip1 =  sc1.nextLine();
		//System.out.print("Veuillez entree le port d'ecoute souhaiter : ");
		//String port1 = sc1.nextLine();
		sc.close();
		sc1.close();
		
		/* lancement du serveur pour test */
		Thread thread = new Thread(){
			public void run(){
			
			/**
			 * Main Cote serveur
			 */
			// lancement de l'ecoute serveur
			InetAddress ia;
			try {
				ia = InetAddress.getByName(ip1);
				s.udpServer(Integer.parseInt(port1), ia);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		};
		thread.start();
		
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
		Request.addr = adresse;
		Request.port = port;
		
		/* On transmet la requête */
		h.udpHost(Request);
	}
}
