import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Serveur {

	private static int port = 9999;
	private static String RootFTP;
	private Map<String, String> users;
	private ServerSocket soc;


	public Serveur() throws IOException{
		this.users = new HashMap<String, String>();
		this.initUser();
		

		this.soc=new ServerSocket(port);
		System.out.println("FTP Server Started on Port Number 9999");
		
	}
	
	
	public void loop() throws IOException{
		
		while(true)
		{
			System.out.println("Waiting for Connection ...");
			ThreadServeur t=new ThreadServeur(this.soc.accept(),this.getUser());
			t.start();
		}
		
	}


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

	public Map<String,String> getUser(){

		return this.users;
	}


	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Serveur serv = new Serveur();
		serv.loop();

	}
}

