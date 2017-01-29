import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server implements MyFrame {

	public Server()
	{
		
	}
	
	/**
	 * Recherche des fichiers
	 * @param port
	 * @param addr
	 * @throws IOException
	 */
	public void udpServer  ( int port, InetAddress addr)
	{
		try {
			// creation de la connexion serveur
			DatagramSocket server = new DatagramSocket(port, addr);
			System.out.println("Le serveur est lancé !");
			while(true)
			{
				// packet pour recuperer la requete client 
				byte [] receiveBuffer =  new byte [8196];
				DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				
				// recuperation du packet
				server.receive(packet);
				System.out.println("Vous avez un nouveau message !");
						
				// test affichage des données
				String msg = new String(packet.getData());
				System.out.println("Message recu : " + msg);
				
				/* renseignement de la requête */
				frameUdpRequest Request = new frameUdpRequest();
				Request = MyFrame.CastToframeUdpRequest(packet.getData());
				
				
				if (Request.RequestType == REQUESTTYPE.NameRequest){
					
					// verfication dans le Tank de la presence du ou des fichiers
					// verfication positive on prepare la reponse avec tout les bon elements
					// verification negative on prepare la reponse seulement en incrementant le chemin
				}
				else if (Request.RequestType == REQUESTTYPE.MerkleRequest){
					
					// verfication dans le Tank de la presence du ou des fichiers
					// verfication positive on prepare la reponse avec tout les bon elements
					// verification negative on prepare la reponse seulement en incrementant le chemin
				}
				
				/*
				Request.nameOrHash = new String("bd").getBytes();	/* pour le moment on désire télécharger le fichier bd *//*
				Request.lenght = 1;
				*/
				
				
				// reponse de la bonne reception du packet
				byte[] rBuffer = new String(msg + "bien recu !").getBytes();
				DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, packet.getAddress(), packet.getPort());
				server.send(response);
				response.setLength(rBuffer.length);
				
				// debut du telechargement -> Thread performHash or performFile
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Download des fichier : Envoie des fichier apres la recherche des fichiers
	 */
	public void tcpServer()
	{
		
	}
}
