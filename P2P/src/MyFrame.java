import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author KeviN
 * 
 */
public interface MyFrame {
	
	/**
	 * @author KeviN
	 * 
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
	 * 
	 */
	class frameUdpResponse
	{
		public REQUESTTYPE RequestType;				// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];	// hash ou nom du fichier demander
		//Reponse
		public int nbrFile;							// nombre de fichier qui constitue le fichier
		public byte[] racineHash = new byte[16];	// hash du fichier racine
		public byte nameFile;						// nom du fichier
	}
	
	/**
	 * @author Arnold
	 * 
	 */
	public enum IPTYPE {
		  IPV4,
		  IPV6;
		}
	
	/**
	 * @author Arnold
	 * 
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
	/*
    - le type de requête sur un octet : 0 pour une requête sur l'empreinte de la racine de l'arbre de Merkle, 1 pour une requête sur le nom de fichier 

    - pour une requête sur empreinte, on indique les 16 premiers octets de l'empreintes (128 bits) 

    - pour une requête sur nom, on indique l'expression régulière recherchée sous la forme d'une chaîne encodée en UTF-8 et préfixée par la taille en octets de la chaîne sur 1 octet 

    - on peut imaginer d'autres types de requêtes préfixées par un octet autre que 0 et 1 

*/
	
}


