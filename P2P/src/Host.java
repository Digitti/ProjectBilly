import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Host {

	public void udpHost( String msg, String addr, int port) throws IOException
	{
		// initialisation d'une connexion cote client
		DatagramSocket host = new DatagramSocket();
		
		// creation du packet
		byte[] buffer = msg.getBytes();
		InetAddress ipAddr =  InetAddress.getByName(addr);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ipAddr, port);
		
		// affectation des donnees au packet
		packet.setData(buffer);
		
		// envoie au serveur
		host.send(packet);
		
		// reponse du serveur
		byte[] rBuffer = new byte[8196];
		DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, ipAddr, port);
		host.receive(response);
		System.out.println("reponse recu : " + new String(response.getData()));
	}
	
	public void tcpHost()
	{
		
	}
}
