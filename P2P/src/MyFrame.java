import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author KeviN
 * 
 */
public interface MyFrame {
	
	/**
	 * @author KeviN
	 * Definis la trame de la requete
	 */
	class frameUdpRequest
	{
		public byte lenght;							// longueur du chemin, soit le nombre de serveur rencontree
		public IPTYPE IpType;						// type d'adresse IPV4 ou IPV6
		public byte[] addr = new byte[16];			// addresse IP
		public int port;							//numero du port
		public REQUESTTYPE RequestType;				// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];	// hash ou nom du fichier demander
	}
	
	/**
	 * @author KeviN
	 * Definis la trame de la reponse
	 */
	class frameUdpResponse
	{
		public REQUESTTYPE RequestType;				// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];	// hash ou nom du fichier demander
		//Reponse
		public int nbrFile;							// nombre de fichier qui constitue le fichier
		public byte[] racineHash = new byte[16];	// hash du fichier racine
		//public byte nameFile;						// nom du fichier
	}
	
	/**
	 * @author Arnold
	 * Enum pour definir le type d'adresse IP
	 */
	public enum IPTYPE {
		  IPV4,
		  IPV6;
		}
	
	/**
	 * @author Arnold
	 * Enum pour definir l'objet de la recherche par empreinte ou nom de fichier
	 */
	public enum REQUESTTYPE {
		  MerkleRequest,
		  NameRequest;
		}
	
	/**
	 * @author Arnold
	 * Méthode pour caster du type frameUdpRequest-->byte[]
	 */
	public static byte[] CastToByte(frameUdpRequest TransmitRequest){
		
		if (TransmitRequest.IpType == IPTYPE.IPV4){
			
			byte[] tBuffer = new byte[9+TransmitRequest.nameOrHash.length]; 
			
			tBuffer [0] = TransmitRequest.lenght;
			tBuffer[1] = 0;
			
			for (int i=0;i<4;i++){
				tBuffer[2+i] = TransmitRequest.addr[i];
			}
			
			/* découpe de l'entier sur 2 bytes 
			 * !!!!!!! ne pas dépasser 65535 comme numéro de port 
			 * sinon augmenter le nombre de byte pour stocker */
			tBuffer[6] = (byte) TransmitRequest.port;
			tBuffer[7] = (byte) (TransmitRequest.port >> 8);
			
			if (TransmitRequest.RequestType == REQUESTTYPE.MerkleRequest){
				tBuffer[8] = 0;
				
			}
			else{
				tBuffer[8] = 1;
			}
			for (int i=0;i<TransmitRequest.nameOrHash.length;i++){
				tBuffer[9+i] = TransmitRequest.nameOrHash[i];
			}
			
			return tBuffer;
		}
		else{
			
			byte[] tBuffer = new byte[21+TransmitRequest.nameOrHash.length]; 
			
			tBuffer [0] = TransmitRequest.lenght;
			tBuffer[1] = 0;
			
			for (int i=0;i<16;i++){
				tBuffer[2+i] = TransmitRequest.addr[i];
			}
			
			/* découpe de l'entier sur 2 bytes 
			 * !!!!!!! ne pas dépasser 65535 comme numéro de port 
			 * sinon augmenter le nombre de byte pour stocker */
			tBuffer[18] = (byte) TransmitRequest.port;
			tBuffer[19] = (byte) (TransmitRequest.port >> 8);
			
			if (TransmitRequest.RequestType == REQUESTTYPE.MerkleRequest){
				tBuffer[20] = 0;
			}
			else{
				tBuffer[20] = 1;
			}
			
			for (int i=0;i<16;i++){
				tBuffer[21+i] = TransmitRequest.nameOrHash[i];
			}
			
			return tBuffer;
		}
	}
	
	/**
	 * @author Arnold
	 * Méthode pour caster byte[]-->frameUdpRequest
	 */
	public static frameUdpRequest CastToframeUdpRequest(byte[] rBuffer){
		
		
		
		
		
		frameUdpRequest ReceiveRequest = new frameUdpRequest();
		
		ReceiveRequest.lenght = rBuffer[0];
		
		if (rBuffer[1] == 0){
			ReceiveRequest.IpType = IPTYPE.IPV4;
			
			for (int i=0;i<4;i++){
				ReceiveRequest.addr[i] = rBuffer[i+2];
			}
			
			ReceiveRequest.port  = (rBuffer[7] <<8)&0xff00|(rBuffer[6]<< 0)&0x00ff ;
			
			if (rBuffer[8] == 0){
				ReceiveRequest.RequestType = REQUESTTYPE.MerkleRequest;
			}
			else{
				ReceiveRequest.RequestType = REQUESTTYPE.NameRequest;
			}
			
			for (int i=0;i<(rBuffer.length-9);i++){
				ReceiveRequest.nameOrHash[i] = rBuffer[i+9];
			}	
		}
		else{
			ReceiveRequest.IpType = IPTYPE.IPV6;
			
			for (int i=0;i<16;i++){
				ReceiveRequest.addr[i] = rBuffer[i+2];
			}
			ReceiveRequest.port  = (rBuffer[19] <<8)&0xff00|(rBuffer[18]<< 0)&0x00ff ;
			
			if (rBuffer[20] == 0){
				ReceiveRequest.RequestType = REQUESTTYPE.MerkleRequest;
			}
			else{
				ReceiveRequest.RequestType = REQUESTTYPE.NameRequest;
			}
			
			for (int i=0;i<(rBuffer.length-21);i++){
				ReceiveRequest.nameOrHash[i] = rBuffer[i+21];
			}
			
		}
		
		return ReceiveRequest;	
	}	

	public static byte[] CastToByteResponse(frameUdpResponse TransmitRequest){
		
		byte[] tBuffer = new byte[37];
		
		if (TransmitRequest.RequestType == REQUESTTYPE.NameRequest){
			tBuffer[0] = 1;
			
			for (int i=0;i<16;i++){
				tBuffer[1+i] = TransmitRequest.nameOrHash[i];
			}
			
			/* découpe de l'entier sur 2 bytes 
			* !!!!!!! ne pas dépasser 65535 comme numéro de port 
			* sinon augmenter le nombre de byte pour stocker */
			tBuffer[17] = (byte) TransmitRequest.nbrFile;
			tBuffer[18] = (byte) (TransmitRequest.nbrFile >> 8);	
			tBuffer[19] = (byte) (TransmitRequest.nbrFile >> 16);
			tBuffer[20] = (byte) (TransmitRequest.nbrFile >> 24);
			
			for (int i=0;i<16;i++){
				tBuffer[21+i] = TransmitRequest.racineHash[i];
			}
			
		}
		else{
			
			tBuffer[0] = 0;
		}
		
		return tBuffer;
	}
}


