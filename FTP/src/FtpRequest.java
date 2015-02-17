
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.io.FileNotFoundException;
/**
 * @author Anne-Sophie Saint-Omer
 * @author Thibault Rosa
 */

public class FtpRequest {

	private Map<String, String> users;
	private String user;
	private int port;
	private InetAddress ad;

	public FtpRequest(Map<String, String> users){
		this.users=users;
	}

	public void processRequest(){

	}

	public void processUSER(Socket s, String user){
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());

			dout.write(new String("331\n").getBytes());
			this.user = user;


		} catch (IOException e) {

			System.out.println("Erreur d'envoi dans processUSER : "+ e);
		}

	}

	public void processPASS(Socket s, String pwd){
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			if(users.containsKey(user) && users.get(user).equals(pwd)){
				dout.write(new String("230\n").getBytes());
			}
			else {
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
			dout.write(new String("215\n").getBytes());
		} catch (IOException e) {
			System.out.println("Erreur d'envoi dans processSYST : "+ e);
		}
	}

	public void processPORT(Socket s, String toc){
		String[] data = toc.split(",");
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			ad = InetAddress.getByName(data[0] + "." + data[1] + "." + data[2] + "." + data[3]);
			port = Integer.parseInt(data[4]) * 256 + Integer.parseInt(data[5]);
			dout.write(new String("200\n").getBytes());
		} catch (IOException e) {
			System.out.println("Erreur d'envoi dans processPORT : "+ e);
		}
	}


	public void processRETR(Socket s, String fichierdistant) {

		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			/* On veut commencer */
			dout.write(new String("150\n").getBytes());
			Socket socket = new Socket(ad, port);
			DataOutputStream d = new DataOutputStream(socket.getOutputStream());

			try{
				FileInputStream br = new FileInputStream(new File("data/"+fichierdistant));


				byte [] buffer = new byte[s.getReceiveBufferSize()];
				while(br.read(buffer) > 0){
					d.write(buffer);
				}
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


	public void processSTOR(Socket s, String file){

		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			/* On veut commencer */
			dout.write(new String("150\n").getBytes());
			Socket socket = new Socket(ad, port);
			DataInputStream r = new DataInputStream(socket.getInputStream());

			try{
				FileOutputStream br = new FileOutputStream(new File("data/"+file));


				byte [] buffer = new byte[s.getReceiveBufferSize()];
				while(r.read(buffer)>0){
					br.write(buffer);
				}
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


	public void processLIST(Socket s, String st){

	}


	public void processQUIT(Socket s,String st){
		

		DataOutputStream dout;
		try {
			dout = new DataOutputStream(s.getOutputStream());
			dout.write(new String("221\n").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
