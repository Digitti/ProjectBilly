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
	 * Recherche des fichiers : Méthode réalisant la partie réception des requêtes et analyse
	 */
	public void udpServer ( int port, InetAddress addr)
	{
		try {
			/* Création de la socket utilisé pour envoyer les données au serveur */
			DatagramSocket server = new DatagramSocket(port, addr);
			System.out.println("La recherche de fichier est lancé !");
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
				
				/* Traitement de la requête UDP */
				frameUdpRequest Request = new frameUdpRequest();
				Request = MyFrame.CastToframeUdpRequest(receiveBuffer);
				
				if (Request.RequestType == REQUESTTYPE.NameRequest){
									
					/* décodage de nom via le codage UTF-8 */
					String decodedHfName = new String(Request.nameOrHash, 0, Request.nameOrHash.length, "UTF-8");
					
					/* test l'existence d'un dossier portant le même nom quedans la requête */
					boolean Test = Tank.FileNameTestEquals(decodedHfName);
					
					/* vérification positive : on prepare la réponse positive et le téléchargement
					 * vérification négative : on prepare la réponse négative
					 */
					if (Test == true){
						
						
						/* on instancie et remplit l'objet/trame de réponse */
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
						Response.nbrFile = Tank.GetNumberOfFiles(decodedHfName);
						Response.racineHash = Tank.GetFirt128BitFileHash(decodedHfName);
						Response.nameFile =
						
						
						/* Réponse via une trame frameUdpResponse */
						/*
						byte[] rBuffer = new String(msg + "bien recu !").getBytes();
						DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, packet.getAddress(), packet.getPort());
						server.send(response);
						response.setLength(rBuffer.length);
						*/
						
					}
					else{
						// verification negative on prepare la reponse seulement en incrementant le chemin
						
						/* Réponse via une trame frameUdpResponse */
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
	public void tcpServer(String host, int port, String sendFile)
	{
		try {
			// initialisation d'une connexion cote client TCP
			Socket socket = new Socket(host, port);
			System.out.println("Le telechargement est lance !");
			
			// lecture de donnee en provenance du serveur
			File file = new File(sendFile);
	        byte[] buffer = new byte[8192]; 
			InputStream in = new FileInputStream(file);
	        OutputStream out = socket.getOutputStream();
	        System.out.print("Envoi du fichier en cours ");
	        
	        int count;
	        while ((count = in.read(buffer)) > 0)
	        {
	          out.write(buffer, 0, count);
	          System.out.print(".");
	        }
	        System.out.println("");
	        System.out.println("Envoi termine !");
	        
	        out.close();
	        in.close();
	        socket.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
