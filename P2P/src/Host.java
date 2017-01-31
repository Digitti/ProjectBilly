import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
	 * Méthode réalisant l'envoi de la requête UDP vers un pair
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
	public void tcpHost(InetAddress server, int port, String nameFile)
	{
		try {
			// initialisation d'une connexion cote client TCP
			Socket host = new Socket(server, port);
			System.out.println("Le telechargement est lance");
			while(true)
			{	
				// lecture de donnee en provenance du serveur
				File f = new File(nameFile);
				InputStream is = new FileInputStream(f);
		        OutputStream os = host.getOutputStream();
		        System.out.print("Reception du fichier en cours ");
		        int count;
		        byte[] buffer = new byte[4096]; 
		        while ((count = is.read(buffer)) > 0)
		        {
		          os.write(buffer, 0, count);
		          System.out.print(".");
		        }
		        System.out.println("");
		        System.out.println("Reception termine !");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
