import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Tank {
	
	/**
	 * @author KeviN
	 * déclaration des tableaux contenant les différentes ressources utiles (réservoir de fichier)   
	 */
	private static ArrayList<String> arrayPathDirectory = new ArrayList<String>();
	private static ArrayList<String> arrayPathSplitFile = new ArrayList<String>();
	private static ArrayList<String> arrayPathSplitHash = new ArrayList<String>();
	
	/**
	 * 
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		String pathInput = args[0];
		System.out.println("Input Directory : " + pathInput );
		performPath(arrayPathDirectory, pathInput);
		
		/**
		 *  Creation du dossier part en fonction du dossier d'entrée 
		 */
		File pathFolder = new File(pathInput);
		String pathParent = pathFolder.getParent();
		File pathOutput = new File(pathParent+"\\"+pathFolder.getName()+"Split");
		String pathSplit = pathOutput.getPath();
		
		if(pathOutput.mkdir())
		{
			System.out.println("Out Split Directory : " + pathSplit );
		}
		else
		{
			System.out.println("Output directory already exists");
		}
		
		/**
		 *  Création du dossier contenant les empreintes des morceaux de fichiers
		 */
		File pathOutputHash = new File(pathParent+"\\"+pathFolder.getName()+"Hash");	
		String pathHash = pathOutputHash.getPath();
		
		if(pathOutputHash.mkdir())
		{
			System.out.println("Out Hash Directory : " + pathHash );
		}
		else
		{
			System.out.println("Output directory already exists");
		}
		
		/**
		 *  Split les documents present dans le dossier 
		 */
		performSplit(arrayPathDirectory, pathSplit);
			
		/**
		 * Affiche le contenu dans le dossier des fichier splitter
		 */
		performPath(arrayPathSplitFile, pathSplit);
		
		/**
		 * Genere le hash des fichiers splitter
		 */		
		performHash(arrayPathSplitFile, pathHash);
		
		/**
		 * Affiche le contenu dans le dossier des fichier splitter
		 */
		performPath(arrayPathSplitHash, pathHash);
		
		MerkleTree ArbreDeMerkle = new MerkleTree(arrayPathSplitHash, args);
		ArbreDeMerkle.CreationDossierMerkle();
		ArbreDeMerkle.CreationArbreMerkle();
		
		
	}
	
	
	/**
	 * @author KeviN
	 * @param ListeCheminFichiers
	 * @param path
	 */
	private static void performPath(ArrayList<String> ListeCheminFichiers, String path)
	{
		File directory = new File(path);
		
		/* vérifie si le chemin de fichier existe */
		if(directory.exists())
		{
			/* verifie si ce shemin est un dossier */
			if(directory.isDirectory())
			{
				System.out.println("Ce dossier contient :");
				
				/* affiche tout les éléments du dossier */
				for(File file : directory.listFiles())
				{
					System.out.println(file.getAbsolutePath());
					ListeCheminFichiers.add(file.getPath());
				}
				System.out.println("Ce dossier contient : " + ListeCheminFichiers.size() + " fichiers" );
			}
			else
			{
				/* ce n'est pas un dossier mais un fichier  */
				ListeCheminFichiers.add(directory.getPath());
			}
		}
	}
	
	
	/**
	 * @author KeviN
	 * @param file
	 * @param index
	 * @param outputDirectory
	 * @throws IOException
	 */
	private static void performSplit(ArrayList<String> file, String pathSplit) throws IOException
	{
		for(int index = 0; index < file.size(); index++)
		{
			FileInputStream fis;
			FileOutputStream fos;
			int cptSplit = 0;
			File f = new File(file.get(index));
			long lengthFile = f.length();
			System.out.println("Le fichier "+f.getName()+" est de taille : "+ lengthFile + " octets");
			
			//Cree un dossier pour les split du fichier selectionner
			File splitFolder = new File(pathSplit+"\\"+f.getName());
			String pathSplitFile = splitFolder.getPath();
			
			if(splitFolder.mkdir())
			{
				System.out.println("Out Directory : " + pathSplit );
			}
			else
			{
				System.out.println("Output directory already exists");
			}
			
			//Decoupe le fichier
			if(lengthFile > 4096)
			{
				fis = new FileInputStream(f);
				for(int j = 0; j < (lengthFile/4096); j++)
				{
					byte[] buffer = new byte[4096];
					fis.read(buffer, 0, 4096);
					fos = new FileOutputStream(pathSplitFile+"\\"+f.getName()+".split"+cptSplit);
					fos.write(buffer);
					fos.flush();
					cptSplit++;
				}
				System.out.println("Il a été divisé en " + cptSplit + " parties");
				System.out.println();
				
			}
			else
			{
				System.out.println("Il n'a pas été divisé ");
				System.out.println();		
			}
		}
	}
	
	
	/**
	 * @author Arnold 
	 * @param file
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	protected static void performHash(ArrayList<String> ListeDossierFichierSplit, String pathHash) throws NoSuchAlgorithmException, IOException
	{
		int NumeroFichierHash =0;
		
		/* Il s'agit dans cette partie de parcourir l'ensemble des fichiers splits du dossier split pour calculer les hashs de chaque fichiers */
		for(int i = 0; i < ListeDossierFichierSplit.size(); i++)
		{
			File DossierCourant = new File(ListeDossierFichierSplit.get(i));
			
			/* creation du dossier contenant les empreintes des morceaux d'un même fichiers */
			File DossierHashCourant = new File(pathHash+"\\"+DossierCourant.getName());
			
			if(DossierHashCourant.mkdir())
			{
				System.out.println("Out Directory : " + pathHash );
			}
			else
			{
				System.out.println("Output directory already exists");
			}			
			
			/* On parcours le dossier courant de fichier split */
			for(File FichierSplitcourant : DossierCourant.listFiles())
			{
				/* mis à jour des flux d'entres et de sortie */
				FileInputStream FichierSplitCourant = new FileInputStream(FichierSplitcourant);
				String NomFichierSplitCourantRaccourcis = FichierSplitcourant.getName().substring(0, FichierSplitcourant.getName().length()-7);
				FileOutputStream HashCourant = new FileOutputStream(DossierHashCourant.getAbsolutePath()+"\\"+NomFichierSplitCourantRaccourcis+".hash"+NumeroFichierHash); 
				
				/* Bloc réalisant le hash de chaque morceaux (l'entrée à hasher est renseigné et mis à jour par la variable : FichierSplitCourant ) */
				String NomFichierSplitCourant = FichierSplitCourant.toString();
				MessageDigest md = MessageDigest.getInstance("SHA-512");
				byte[] bytSHA = md.digest(NomFichierSplitCourant.getBytes());
				HashCourant.write(bytSHA);
				
				/* la creation de hash est terminée. le numéro de hash est mis a jour, les flux de sortie et d'entrée sont refermes */
				NumeroFichierHash ++;
				FichierSplitCourant.close();
				HashCourant.close();
				
				System.out.println("le hash genere est : "+bytSHA);
			}	
			NumeroFichierHash =0;
		}	
		
	}
}
		
