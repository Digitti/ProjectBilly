import java.util.Scanner;

public class MyMainHost implements MyFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		
		/**
		 * Main cote client 
		 */
		Host h = new Host();
		Scanner sc = new Scanner(System.in);
		System.out.print("Veuillez entree le hash ou le nom du fichier : ");
		String hf = sc.nextLine();
		System.out.print("Veuillez entree l'adresse ip du votre serveur demander : ");
		String ip = sc.nextLine();
		System.out.print("Veuillez entree le port d'ecoute souhaiter :  ");
		int port = sc.nextInt();
		sc.close();
		
		/* renseignement de la requ�te */
		frameUdpRequest Request = new frameUdpRequest();
		
		Request.RequestType = REQUESTTYPE.NameRequest;		/* pour le moment on choisis une requ�te de type name */
		Request.nameOrHash = new String("bd").getBytes();	/* pour le moment on d�sire t�l�charger le fichier bd */
		Request.lenght = 1;
		Request.IpType = IPTYPE.IPV4;
		Request.addr = ip.getBytes();
		Request.port = port;
		
		/* On transmet la requ�te */
		h.udpHost(Request);
	}
}
