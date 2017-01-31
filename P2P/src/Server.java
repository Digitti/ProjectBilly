import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
			/* Cr�ation de la socket utilis� pour envoyer les donn�es au serveur */
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
									
					/* d�codage de nom via le codage UTF-8 */
					String decodedHfName = new String(Request.nameOrHash, 0, Request.nameOrHash.length, "UTF-8");
					
					/* test l'existence d'un dossier portant le m�me nom quedans la requ�te */
					boolean Test = Tank.FileNameTestEquals(decodedHfName);
					
					/* v�rification positive : on prepare la r�ponse positive et le t�l�chargement
					 * v�rification n�gative : on prepare la r�ponse n�gative
					 */
					if (Test == true){
						
												
						/* R�ponse via une trame frameUdpResponse */
						/*
						byte[] rBuffer = new String(msg + "bien recu !").getBytes();
						DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, packet.getAddress(), packet.getPort());
						server.send(response);
						response.setLength(rBuffer.length);
						*/
						
						/* on instancie et remplit l'objet/trame de r�ponse */
						frameUdpResponse Response = new frameUdpResponse();
						
						Response.RequestType = REQUESTTYPE.NameRequest;
						
						byte[] encodedHfWithUTF8 = null;
						try {
							encodedHfWithUTF8 = decodedHfName.getBytes("UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Response.nameOrHash = encodedHfWithUTF8;
						
					}
					else{
						// verification negative on prepare la reponse seulement en incrementant le chemin
						
						/* R�ponse via une trame frameUdpResponse */
						/*
						byte[] rBuffer = new String(msg + "bien recu !").getBytes();
						DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, packet.getAddress(), packet.getPort());
						server.send(response);
						response.setLength(rBuffer.length);
						*/
					}
					

				}
				else if (Request.RequestType == REQUESTTYPE.MerkleRequest){
					
					// verfication dans le Tank de la presence du ou des fichiers
					// verfication positive on prepare la reponse avec tout les bon elements
					// verification negative on prepare la reponse seulement en incrementant le chemin
					System.out.println("affichage de la variable : ");
					System.out.println(Request);
					
					
				}
				
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
				
				/*os.close();
				is.close();
				client.close();
				server.close();*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
