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
		String port = sc.nextLine();
		sc.close();
		
		// composition de la requete udp
		frameUdpRequest fr = null;
		
		// lancement de la communication client
		h.udpHost(" UDP DATAGRAM ", ip, Integer.parseInt(port));
	}

}
