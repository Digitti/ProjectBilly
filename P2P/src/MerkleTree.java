import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MerkleTree {
	
	
	ArrayList<String> arrayHashFolder = new ArrayList<String>();
	ArrayList<String> arrayConcatenationFolder = new ArrayList<String>();
	ArrayList<String> arrayConcatenationHashFolder = new ArrayList<String>();
	
	String CheminDossierCommun;
	String CheminDossierCommunMerkle;

	public MerkleTree(ArrayList<String> arrayPathSplitHash,String[] args)
	{
		arrayHashFolder = arrayPathSplitHash;
		CheminDossierCommun = args[0];
	}

	public void CreationArbreMerkle() throws IOException
	{
		int NumeroFichierMerkle =0; 
		
		/* Il s'agit dans cette partie de parcourir l'ensemble des fichiers hash du dossier hash pour calculer les hashs de chaque fichiers */
		for(int i = 0; i < arrayHashFolder.size(); i++)
		{
			File DossierCourant = new File(arrayHashFolder.get(i));
			
			/* creation du dossier contenant les empreintes des morceaux d'un m�me fichiers */
			File DossierMerkleCourant = new File(CheminDossierCommunMerkle+"\\"+DossierCourant.getName());
			
			if(DossierMerkleCourant.mkdir())
			{
				System.out.println("Out Directory : " + DossierMerkleCourant.getName() );
			}
			else
			{
				System.out.println("Output directory already exists");
			}	
			
			/* On parcours le dossier courant de fichier hash */
			for(File FichierHashcourant : DossierCourant.listFiles())
			{
				/* mis � jour des flux d'entres et de sortie */
				FileInputStream FichierHashCourant = new FileInputStream(FichierHashcourant);
				String NomFichierMerkleCourantRaccourcis = FichierHashcourant.getName().substring(0, FichierHashcourant.getName().length()-6);
				FileOutputStream HashCourant = new FileOutputStream(DossierMerkleCourant.getAbsolutePath()+"\\"+NomFichierMerkleCourantRaccourcis+".hash"+NumeroFichierMerkle);				
				
				byte[] EmpreinteCourante128bits = new byte[16];
				FichierHashCourant.read(EmpreinteCourante128bits, 0, 16);
				HashCourant.write(EmpreinteCourante128bits);
				HashCourant.flush();
				
				FichierHashCourant.close();
				HashCourant.close();
				NumeroFichierMerkle ++;
			}
			NumeroFichierMerkle = 0;
		}
	}
	
	public void CreationDossierMerkle() throws FileNotFoundException
	{	
		/**
		 *  Creation du dossier part en fonction du dossier d'entr�e 
		 */
		File pathFolder = new File(CheminDossierCommun);
		File pathOutput = new File(pathFolder.getParent()+"\\"+pathFolder.getName()+"Merkle");
		
		if(pathOutput.mkdir())
		{
			System.out.println("Out Split Directory : " + pathOutput.getPath() );
		}
		else
		{
			System.out.println("Output directory already exists");
		}
		
		CheminDossierCommunMerkle = pathOutput.getPath();
	}

	/**
	 * Realise l'arbre de Merkle
	 * @param hashFolder
	 */
	protected static void createTree(ArrayList<String> hashFolder)
	{
		
	}
	
	/**
	 * Hash les fichier apres concatenation
	 * @param concatenationHashFolder
	 */
	private static void performConcatenationHash(ArrayList<String> concatenationHashFolder)
	{
		
	}
}
