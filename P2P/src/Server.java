import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

	public Server()
	{
		
	}
	
	/**
	 * Recherche des fichiers
	 * @param port
	 * @param addr
	 * @throws IOException
	 */
	public void udpServer ( int port, InetAddress addr)
	{
		try {
			// creation de la connexion serveur
			DatagramSocket server = new DatagramSocket(port, addr);
			System.out.println("Ecoute sur le port : " + port);
			while(true)
			{
				// packet pour recuperer la requete client 
				byte [] receiveBuffer =  new byte [8196];
				DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				
				// recuperation du packet
				server.receive(packet);
				
				// test affichage des données
				String msg = new String(packet.getData());
				System.out.println("Message recu : " + msg);
				
				// reponse de la bonne reception du packet
				byte[] rBuffer = new String(msg + "bien recu !").getBytes();
				DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, packet.getAddress(), packet.getPort());
				server.send(response);
				response.setLength(rBuffer.length);
				server.close();
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
