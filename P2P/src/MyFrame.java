
public interface MyFrame {
	
	class frameUdpRequest
	{
		public byte lenght;		// longueur du chemin, soit le nombre de serveur rencontree
		public byte ipType;		// type d'adresse IPV4 ou IPV6
		public byte[] addr = new byte[16];		// addresse IP
		public short port;		//numero du port
		public byte requestType;		// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];	// hash ou nom du fichier demander
	}
	
	class frameUdpResponse
	{
		public byte requestType;		// type de requete par nom ou racine
		public byte[] nameOrHash = new byte[16];		// hash ou nom du fichier demander
		//Reponse
		public int nbrFile;		// nombre de fichier qui constitue le fichier
		public byte[] racineHash = new byte[16];		// hash du fichier racine
		public byte nameFile;		// nom du fichier
	}
	
	
}
