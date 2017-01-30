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


public class Server implements MyFrame {

	public Server()
	{
		
	}
	
	/**
	 * @author KeviN
	 * @param port
	 * @param addr
	 * @throws IOException
	 * Recherche des fichiers : M�thode r�alisant la partie r�ception des requ�tes et analyse
	 */
	public void udpServer  ( int port, InetAddress addr)
	{
		try {
			// creation de la connexion serveur UDP
			DatagramSocket server = new DatagramSocket(port, addr);
			System.out.println("La recherche de fichier est lanc� !");
			while(true)
			{
				// packet pour recuperer la requete client 
				byte [] Buffer =  new byte [8196];
				DatagramPacket packet = new DatagramPacket(Buffer, Buffer.length);
				
				// recuperation du packet
				server.receive(packet);
				System.out.println("Nouvelle requete recu !");
				
				byte [] receiveBuffer =  new byte [packet.getLength()];
				
				for (int i=0; i < packet.getLength(); i++){
					receiveBuffer[i] = packet.getData()[i];
				}
				
				/* Traitement de la requ�te */
				frameUdpRequest Request = new frameUdpRequest();
				Request = MyFrame.CastToframeUdpRequest(receiveBuffer);
				
				if (Request.RequestType == REQUESTTYPE.NameRequest){
					
					// verfication dans le Tank de la presence du ou des fichiers
					// verfication positive on prepare la reponse avec tout les bon elements
					// verification negative on prepare la reponse seulement en incrementant le chemin
					System.out.println("affichage de la variable : ");
					System.out.println(Request);
					
					/* d�codage de nom */
					String decodedHfName = new String(Request.nameOrHash, 0, Request.nameOrHash.length, "UTF-8");
					
				}
				else if (Request.RequestType == REQUESTTYPE.MerkleRequest){
					
					// verfication dans le Tank de la presence du ou des fichiers
					// verfication positive on prepare la reponse avec tout les bon elements
					// verification negative on prepare la reponse seulement en incrementant le chemin
					System.out.println("affichage de la variable : ");
					System.out.println(Request);
					
					
					
				}
				
				/*
				Request.nameOrHash = new String("bd").getBytes();	/* pour le moment on d�sire t�l�charger le fichier bd *//*
				Request.lenght = 1;
				*/
				
				
				// reponse de la bonne reception du packet
				//byte[] rBuffer = new String(msg + "bien recu !").getBytes();
				//DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, packet.getAddress(), packet.getPort());
				//server.send(response);
				//response.setLength(rBuffer.length);
				
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
	 * @author KeviN
	 * @param port
	 * @param sendFile
	 * Telechargement des fichiers : Envoie des fichier apres la recherche des fichiers
	 */
	public void tcpServer(int port, String sendFile)
	{
		try {
			// creation de la connexion serveur TCP
			ServerSocket server = new ServerSocket(port);
			System.out.println("Le telechargement est lance");
			while(true)
			{
				// accepte une connexion avec le client
				Socket client = server.accept();
				
				// ecriture de donnee a destination du client 
				InputStream is = client.getInputStream();
				
				// fichier a envoye
				OutputStream os =  new FileOutputStream(sendFile);
				System.out.print("Envoie du fichier ");
				int count;
				byte[] buffer = new byte[4096];
				while ((count = is.read(buffer)) > 0)
				{
				  os.write(buffer, 0, count);
				  System.out.print(".");
				}
				System.out.println("");
				System.out.println("Envoi termine !");
				
				os.close();
				is.close();
				client.close();
				server.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
