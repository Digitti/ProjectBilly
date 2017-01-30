import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Host implements MyFrame{
	
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
	public void udpHost(frameUdpRequest request)
	{
		while(true)
		{
			try {
				Thread.sleep(3000);
				
				// initialisation d'une connexion cote client
				DatagramSocket host = new DatagramSocket();
				System.out.println("Le client est lancé !");
				
				InetAddress ipAddr = InetAddress.getByAddress(request.addr);
				//InetAddress ipAddr = InetAddress.;
				
				byte[] tBuffer = new byte[(MyFrame.CastToByte(request).length)];
				tBuffer = MyFrame.CastToByte(request);
				
				DatagramPacket packet = new DatagramPacket(tBuffer, tBuffer.length, ipAddr, request.port);
				
				// affectation des donnees au packet
				packet.setData(tBuffer);
				
				// envoi au serveur
				host.send(packet);
				System.out.println("Un message vient d'etre envoyer !");
				
				// reponse du serveur
				//byte[] rBuffer = new byte[8196];
				//DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, ipAddr, port);
				//host.receive(response);
				//System.out.println("reponse recu : " + new String(response.getData()));
				
				// Si reponse positive lancement du Thread du telechargement -> Thread tcpHost
				
				
				
				host.close();
				
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
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
