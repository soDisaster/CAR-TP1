
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * @author Anne-Sophie Saint-Omer
 * @author Thibault Rosa
 */

public class FtpRequest {

	private Map<String, String> users;
	private String user;
	int cpt =0;

	public FtpRequest(Map<String, String> users){
		this.users=users;
	}

	public void processRequest(){

	}

	public void processUSER(Socket s, String user){
		cpt++;
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


	public void processRETR(Socket s){

	}


	public void processSTOR(Socket s){

	}


	public void processLIST(Socket s){

	}


	public void processQUIT(Socket s){

	}

}
