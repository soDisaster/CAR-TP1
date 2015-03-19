package serverPack;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** 
 * @author Thibault Rosa
 * @author Anne-Sophie Saint-Omer
 *
 */

/**
 *  Classe Serveur 
 */

public class Serveur {
	
	/*
	 * On travaille sur le port 9999
	 */
	

	private static int port = 9999;
	private static boolean boucle;
	
	/** 
	 * users contient le nom des utilisateurs et le mot de passe qui leur est associé.
	 */
	private Map<String, String> users;
	private ServerSocket soc;

	/** 
	 * Constructeur
	 * @throws IOException
	 */
	
	public Serveur() throws IOException{
		boucle=true;
		this.users = new HashMap<String, String>();
		this.initUser();
		this.soc=new ServerSocket(port);
		System.out.println("FTP Server Started on Port Number 9999");
		
	}
	
	/**
	 * Boucle infinie
	 * accept() permet d'accepter les demandes de connexion.
	 */
	
	public void loop() throws IOException{
		
		while(boucle)
		{
			System.out.println("Waiting for Connection ...");
			ThreadServeur t=new ThreadServeur(this.soc.accept(),this.getUser());
			t.start();
		}
		
	}

	public void stop() throws IOException{
		boucle=false;
		soc.close();
	}
	/**
	 * Permet de mettre le contenu du fichier correspondant aux utilisateurs
	 * et leur mots de passe dans la Map users
	 */
	public void initUser(){


		String fileName = "data/users.txt";	
		String[] tab;

		try{
			Scanner scanner = new Scanner(new File(fileName));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
				tab=line.split(";");
				users.put(tab[0],tab[1]);
			}

			scanner.close();
		}catch(Exception e){
			System.out.println("fail : " + e);
		}



	}

	/** 
	 * Retourne la Map contenant les utilisateurs et leur mot de passe.
	 * @return users
	 */
	public Map<String,String> getUser(){

		return this.users;
	}


	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Serveur serv = new Serveur();
		/* On lance le serveur qui grâce à la méthode loop écoute infiniment */
		serv.loop();

	}
}

