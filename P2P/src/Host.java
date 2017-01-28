import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Host {
	
	public Host()
	{
		
	}

	/**
	 * Recherche des fichiers 
	 * @param msg
	 * @param addr
	 * @param port
	 * @throws IOException
	 */
	public void udpHost( String msg, String addr, int port)
	{
		while(true)
		{
			try {
				// initialisation d'une connexion cote client
				DatagramSocket host = new DatagramSocket();
				System.out.println("Le client est lancé !");
				// creation du packet
				byte[] buffer = msg.getBytes();
				InetAddress ipAddr =  InetAddress.getByName(addr);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ipAddr, port);
				
				// affectation des donnees au packet
				packet.setData(buffer);
				
				// envoie au serveur
				host.send(packet);
				System.out.println("Un message vient d'etre envoyer !");
				
				// reponse du serveur
				byte[] rBuffer = new byte[8196];
				DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, ipAddr, port);
				host.receive(response);
				System.out.println("reponse recu : " + new String(response.getData()));
				host.close();
				
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * Download des fichiers : telechargement des fichiers apres recherche des fichiers
	 */
	public void tcpHost()
	{
		
	}
}
