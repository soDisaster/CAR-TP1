package serveurPack;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import serverPack.Serveur;
import serverPack.ThreadServeur;

public class ThreadServeurTest {
		

	Socket socket;
	DataInputStream din;
	DataOutputStream dout;
	
	 @Before 
	 public void initialize() throws IOException {
		 
		
		 socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
		 dout=new DataOutputStream(socket.getOutputStream());
		 din=new DataInputStream(socket.getInputStream());
	      
	    }
	@Test
	public void testRun() throws UnknownHostException, IOException {
		
		
		
		
		
		String retourServ = din.readLine(); /*deprecated*/
		System.out.println(retourServ);
		
		assertTrue("Retour Fail",Integer.parseInt(retourServ)== 220);
		
		
		
	}

	@Test
	public void testKeyWord() throws IOException{
		
		ThreadServeur t=new ThreadServeur(socket,null);
		
		assertEquals("KeyWord methode failure.",t.keyWord("TOTO york"),"TOTO");

		assertFalse("KeyWord methode failure.",t.keyWord("TOTO york").equals("york"));
		
	
	}

	@Test
	public void testArg() throws IOException {
	
		ThreadServeur t=new ThreadServeur(socket,null);
		
		assertEquals("KeyWord methode failure.",t.arg("TOTO york"),"york");
		assertFalse("KeyWord methode failure.",t.arg("TOTO york").equals("TOTO"));
		
	}
	
	@After 
	public void finalize() throws IOException{

		socket.close();
		dout.close();
		din.close();
	}

}
