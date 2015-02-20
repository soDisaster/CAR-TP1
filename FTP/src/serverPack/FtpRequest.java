package serverPack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
/**
 * @author Anne-Sophie Saint-Omer
 * @author Thibault Rosa
 */

/**
 * Classe correspondant aux requêtes FTP
 */

public class FtpRequest {

	private Map<String, String> users;
	private String user;
	private int port;
	private InetAddress ad;
	private String currentLoc;


	/**
	 * Constructeurs
	 */
	public FtpRequest(Map<String, String> users){
		this.users=users;
		this.currentLoc = "data";
	}


	public void processUSER(Socket s, String user){
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			// Utilisateur reconnu. En attente du mot de passe.
			dout.write(new String("331\n").getBytes());
			System.out.println("Ici");
			this.user = user;


		} catch (IOException e) {

			System.out.println("Erreur d'envoi dans processUSER : "+ e);
		}

	}

	public void processPASS(Socket s, String pwd){
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			if(users.containsKey(user) && users.get(user).equals(pwd)){
				/* User logged in, proceed. Logged out if appropriate. */
				dout.write(new String("230\n").getBytes());
			}
			else {
				/* Identifiant ou mot de passe incorrect*/
				dout.write(new String("430\n").getBytes());
			}
		} catch (IOException e) {

			System.out.println("Erreur d'envoi dans processPASS : "+ e);
		}
	}

	public void processSYST(Socket s, String toc){

		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			// Sous Windows, on doit changer ce code.
			// NAME system type. Where NAME is an official system name from the register
			dout.write(new String("215 UNIX\n").getBytes());
		} catch (IOException e) {
			System.out.println("Erreur d'envoi dans processSYST : "+ e);
		}
	}

	public void processPORT(Socket s, String toc){
		String[] data = toc.split(",");
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			/* Apparait dans la console
			 * Première partie adresse. 
			 * Seconde partie port.
			 */
			ad = InetAddress.getByName(data[0] + "." + data[1] + "." + data[2] + "." + data[3]);
			port = Integer.parseInt(data[4]) * 256 + Integer.parseInt(data[5]);
			// Action demandée accomplie avec succès.
			dout.write(new String("225\n").getBytes());
		} catch (IOException e) {
			System.out.println("Erreur d'envoi dans processPORT : "+ e);
		}
	}

	/**
	 * Utilisé pour prendre un fichier du répertoire distant et
	 * le déposer dans le répertoire local
	 * @param s socket
	 * @param fichierdistant Le fichier que l'on souhaite récupérer
	 */
	public void processRETR(Socket s, String fichierdistant) {

		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			/* File status okay; about to open data connection. */
			dout.write(new String("150\n").getBytes());
			/* Variable obtenues dans processPORT */
			Socket socket = new Socket(ad, port);
			DataOutputStream d = new DataOutputStream(socket.getOutputStream());

			try{
				FileInputStream br = new FileInputStream(new File("data/"+fichierdistant));

				byte [] buffer = new byte[s.getReceiveBufferSize()];
				int nbOfbyte;
				while((nbOfbyte = br.read(buffer)) > 0){
					d.write(buffer,0,nbOfbyte);
				}

				d.flush();
				br.close();
				/* Data connection open; no transfer in progress.*/
				dout.write(new String("226\n").getBytes());
			}catch(FileNotFoundException e){	
				/* Erreur */
				dout.write(new String("550\n").getBytes());

			}


			socket.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * Fichier répertoire local dans répertoire distant
	 * @param s
	 * @param file
	 */
	public void processSTOR(Socket s, String file){

		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			/* File status okay; about to open data connection. */
			dout.write(new String("150\n").getBytes());
			Socket socket = new Socket(ad, port);
			DataInputStream r = new DataInputStream(socket.getInputStream());

			try{
				FileOutputStream br = new FileOutputStream(new File("data/"+file));


				byte [] buffer = new byte[s.getReceiveBufferSize()];
				int nbOfbyte;
				while((nbOfbyte = r.read(buffer))>0){
					br.write(buffer,0,nbOfbyte);
				}
				br.close();
				/* Data connection open; no transfer in progress.*/
				dout.write(new String("226\n").getBytes());
			}catch(FileNotFoundException e){	
				/* Erreur */
				dout.write(new String("550\n").getBytes());

			}


			socket.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Permet à l'utilisateur de demander la liste de fichier
	 * @param s
	 * @param file
	 */
	public void processLIST(Socket s, String file){


		File repertoire;

		if(file.length() > 0)
			repertoire = new File(this.currentLoc+"/"+file);
		else
			repertoire = new File(this.currentLoc);


		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			/* File status okay; about to open data connection. */
			dout.write(new String("150\n").getBytes());
			Socket socket = new Socket(ad, port);


			DataOutputStream d = new DataOutputStream(socket.getOutputStream());
			String [] listefichiers;
			String s1 = "";
			listefichiers=repertoire.list();
			s1+="\r\n";
			for(int i=0;i<listefichiers.length;i++){
				s1 += listefichiers[i] + "\r\n";
				System.out.println(s1);
			}
			s1+="\r\n";
			d.write((s1.getBytes()));
			System.out.println("1");
			d.flush();
			System.out.println("2");
			d.close();
			dout.write(new String("226\n").getBytes());

			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void processQUIT(Socket s,String st){


		DataOutputStream dout;
		try {
			dout = new DataOutputStream(s.getOutputStream());
			// Service closing control connection.
			dout.write(new String("221\n").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public void processFEAT(Socket s, String st) throws IOException{

		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		dout.write(new String("500\n").getBytes());
	}

	public void processPWD(Socket s, String st) throws IOException{

		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		dout.write(new String("257 \""+this.currentLoc+"\"\n").getBytes());
		dout.flush();
	}



	public void processCWD(Socket s, String file) throws IOException{
		
		if(file.equals("..")){
			this.processCDUP(s, file);
		}
		else{
			
			String newPath;
			if(file.charAt(0) =='/'){
				 newPath = file.substring(1);
			}
			else{
				newPath = this.currentLoc+="/"+file;
			}

			File repertoire = new File(newPath);
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			if(repertoire.isDirectory()){
				this.currentLoc=newPath;
				dout.write(new String("250\n").getBytes());
				dout.flush();
			}else{
				dout.write(new String("550\n").getBytes());
				dout.flush();
			}
		}

	}

	public void processCDUP(Socket s, String file) throws IOException{
		int end =this.currentLoc.lastIndexOf("/");

		String newPath = (String) this.currentLoc.subSequence(0,end);



		File repertoire = new File(newPath);
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		if(repertoire.isDirectory()){
			this.currentLoc=newPath;
			dout.write(new String("250\n").getBytes());
			dout.flush();
		}else{
			dout.write(new String("550\n").getBytes());
			dout.flush();
		}

	}
}
