package serveurPack;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FtpRequestTest {

	
	Socket socket;
	DataInputStream din;
	DataOutputStream dout;
	
	
	 @Before 
	 public void initialize() throws IOException {
		 
		
		 socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
		 dout=new DataOutputStream(socket.getOutputStream());
		 din=new DataInputStream(socket.getInputStream());
		 String retourServ = din.readLine(); /*deprecated*/
		 System.out.println(retourServ);
	    }

	@Test
	public void testProcessSequenceFTP() throws IOException {
		
		/*user*/
		dout.write(new String("USER saintomer\n").getBytes());
		dout.flush();
		System.out.println("to");
		String retourServ = din.readLine(); /*deprecated*/
		System.out.println(retourServ);
		assertTrue("Retour Fail",Integer.parseInt(retourServ)== 331);
		
		/*password*/
		dout.write(new String("PASS Azerty1\n").getBytes());
		dout.flush();
		System.out.println("to");
		retourServ = din.readLine(); /*deprecated*/
		System.out.println(retourServ);
		assertTrue("Retour Fail",Integer.parseInt(retourServ)== 230);
		assertFalse("Retour Fail",Integer.parseInt(retourServ)!= 230);
		
		/*password*/
		dout.write(new String("PORT 127.0.0.1\n").getBytes());
		dout.flush();
		System.out.println("to");
		retourServ = din.readLine(); /*deprecated*/
		System.out.println(retourServ);
		assertTrue("Retour Fail",Integer.parseInt(retourServ)== 230);
		assertFalse("Retour Fail",Integer.parseInt(retourServ)!= 230);
		
		
		
		
	}


	@Test
	public void testProcessSequenceWrongFTP() throws IOException {
		
		/*user*/
		dout.write(new String("USER saintomer\n").getBytes());
		dout.flush();
		System.out.println("to");
		String retourServ = din.readLine(); /*deprecated*/
		System.out.println(retourServ);
		assertTrue("Retour Fail",Integer.parseInt(retourServ)== 331);
		
		/*password false*/
		dout.write(new String("PASS Azertyp1\n").getBytes());
		dout.flush();
		System.out.println("to");
		retourServ = din.readLine(); /*deprecated*/
		System.out.println(retourServ);
		assertTrue("Retour Fail",Integer.parseInt(retourServ)== 430);
		assertFalse("Retour Fail",Integer.parseInt(retourServ)!= 430);
	}

	@After 
	public void finalize() throws IOException{

		socket.close();
		dout.close();
		din.close();
	}
}
