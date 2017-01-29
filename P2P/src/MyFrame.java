
public interface MyFrame {
	
	class frameUdpRequest
	{
		public byte lenght;							// longueur du chemin, soit le nombre de serveur rencontree
		public IPTYPE IpType;						// type d'adresse IPV4 ou IPV6
		public byte[] addr = new byte[16];			// addresse IP
		public int port;							//numero du port
		public REQUESTTYPE RequestType;				// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];	// hash ou nom du fichier demander
	}
	
	class frameUdpResponse
	{
		public REQUESTTYPE RequestType;				// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];	// hash ou nom du fichier demander
		//Reponse
		public int nbrFile;							// nombre de fichier qui constitue le fichier
		public byte[] racineHash = new byte[16];	// hash du fichier racine
		public byte nameFile;						// nom du fichier
	}
	
	public enum IPTYPE {
		  IPV4,
		  IPV6;
		}
	
	public enum REQUESTTYPE {
		  MerkleRequest,
		  NameRequest;
		}
	
	public static byte[] CastToByte(frameUdpRequest request){
		
		byte[] tBuffer = new byte[16];
		
		tBuffer [0] = request.lenght;
		
		if (request.IpType == IPTYPE.IPV4){
			tBuffer[1] = 0;
		}
		else{
			tBuffer[1] = 0;
		}
		
		for (int i=0;i<16;i++){
			tBuffer[2+i] = request.addr[i];
		}
		
		tBuffer[18] = (byte) request.port;
		
		if (request.RequestType == REQUESTTYPE.MerkleRequest){
			tBuffer[19] = 0;
		}
		else{
			tBuffer[19] = 1;
		}
		
		for (int i=0;i<16;i++){
			tBuffer[19+i] = request.nameOrHash[i];
		}
		
		return tBuffer;
		
	}
	
	public static frameUdpRequest CastToframeUdpRequest(byte[] rBuffer){
		
		frameUdpRequest request = new frameUdpRequest();
		
		
		 request.lenght = rBuffer[0];
		
		if (rBuffer[1] == 0){
			request.IpType = IPTYPE.IPV4;
		}
		else{
			request.IpType = IPTYPE.IPV6;
		}
		
		for (int i=0;i<16;i++){
			request.addr[i+2] = rBuffer[i];
		}
		
		request.port = (int)rBuffer[18];
		
		if (rBuffer[19] == 0){
			request.RequestType = REQUESTTYPE.MerkleRequest;
		}
		else{
			request.RequestType = REQUESTTYPE.NameRequest;
		}
		
		for (int i=0;i<16;i++){
			request.nameOrHash[19+i] = rBuffer[i];
		}
		
		return request;
		
	}	
	/*
    - le type de requête sur un octet : 0 pour une requête sur l'empreinte de la racine de l'arbre de Merkle, 1 pour une requête sur le nom de fichier 

    - pour une requête sur empreinte, on indique les 16 premiers octets de l'empreintes (128 bits) 

    - pour une requête sur nom, on indique l'expression régulière recherchée sous la forme d'une chaîne encodée en UTF-8 et préfixée par la taille en octets de la chaîne sur 1 octet 

    - on peut imaginer d'autres types de requêtes préfixées par un octet autre que 0 et 1 

*/
}
