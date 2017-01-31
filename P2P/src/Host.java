import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
	 * 
	 * Méthode realisant l'envoi de la requete UDP vers un pair
	 */
	public void udpHost(frameUdpRequest request)
	{
		while(true)
		{
			try {
				Thread.sleep(1000);
				
				// initialisation d'une connexion cote client UDP
				DatagramSocket host = new DatagramSocket();
				System.out.println("Le client est lancé !");
				
				/* décode en UTF-8 l'adresse stocké dans un tableau de byte, afin d'en obtenir l'adresse en InetAddress*/
				InetAddress ipAddr = InetAddress.getByName(new String(request.addr, 0, request.addr.length, "UTF-8"));
				
				/* la requête est casté au format d'un tableau de byte pour être envoyé parl a socket UDP */
				byte[] tBuffer = new byte[(MyFrame.CastToByte(request).length)];
				tBuffer = MyFrame.CastToByte(request);
				DatagramPacket packet = new DatagramPacket(tBuffer, tBuffer.length, ipAddr, request.port);
				
				/* affectation des donnees au packet */
				packet.setData(tBuffer);
				
				/* envoi des données */
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
	 * @author KeviN
	 * @param server
	 * @param port
	 * @param nameFile
	 * Download des fichiers : telechargement des fichiers apres recherche des fichiers
	 */
	public void tcpHost(int port, String receiveFile)
	{
		try {
			// creation de la connexion serveur TCP
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Le telechargement est lance !");
			
			// accepte une connexion avec le client
			Socket socket = serverSocket.accept();
			
			// ecriture de donnee a destination du client 
			InputStream in = socket.getInputStream();
			
			// fichier a envoye
			OutputStream out =  new FileOutputStream(receiveFile);
			System.out.print("Reception du fichier ");
			
			byte[] buffer = new byte[8192];
			
			int count;
			while ((count = in.read(buffer)) > 0)
			{
			  out.write(buffer, 0, count);
			  System.out.print(".");
			}
			System.out.println("");
			System.out.println("Reception termine !");
			
			out.close();
			in.close();
			socket.close();
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
