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
		for(int i = 0; i < arrayPathDirectory.size(); i++)
		{
			performSplit(arrayPathDirectory, i, pathSplit);
		}
			
		/**
		 * Affiche le contenu dans le dossier des fichier splitter
		 */
		performPath(arrayPathSplitFile, pathSplit);
		
		/**
		 * Genere le hash des fichiers splitter
		 */
		//performHash(arrayPathSplitFile, pathHash);
	}
	
	
	/**
	 * @author KeviN
	 * @param ListeCheminFichiers
	 * @param path
	 */
	public static void performPath(ArrayList<String> ListeCheminFichiers, String path)
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
	public static void performSplit(ArrayList<String> file, int index, String pathSplit) throws IOException
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
			for(int i = 0; i < (lengthFile/4096); i++)
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
	
	
	/**
	 * @author Arnold 
	 * @param file
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void performHash(ArrayList<String> file, String pathHash) throws NoSuchAlgorithmException, IOException
	{
		String pathHashfile = pathHash;
		int cpt =0;
		
		/* on parcours l'ensemble des morceaux de fichier */
		for(int i = 0; i < file.size(); i++)
		{
			File Currentf = new File(file.get(i));
			
			String CurrentPart = Currentf.getName();
					
			/* test s'il s'agit des parts d'un nouveau fichier à hasher */
			if ((CurrentPart.contains("part0")) == true )
			{	
				/* création du dossier contenant les empreintes des morceaux d'un même fichiers */
				CurrentPart = CurrentPart.substring(0, CurrentPart.length()-6);
				File Hashfolder = new File(pathHash+"\\"+CurrentPart);
				pathHashfile = Hashfolder.getPath();
				
				if(Hashfolder.mkdir())
				{
					System.out.println("Out Directory : " + pathHash );
				}
				else
				{
					System.out.println("Output directory already exists");
				}	
				cpt =0;
			}
			else
			{
				CurrentPart = CurrentPart.substring(0, CurrentPart.length()-6);
			}
						
			/* Bloc réalisant le hash de chaque morceaux (l'entrée à hasher est renseigné et mis à jour par la variable : Currentf ) */
			FileOutputStream file1 = new FileOutputStream(pathHashfile+"\\"+CurrentPart+".hash"+cpt); 
			FileInputStream fis = new FileInputStream(Currentf);
			String Ffis = fis.toString();
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bytSHA = md.digest(Ffis.getBytes());
			file1.write(bytSHA);
			
			cpt ++;
			
	        System.out.println("le hash généré est : "+bytSHA);
		}		
	}
}
		
