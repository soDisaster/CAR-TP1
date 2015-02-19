package serveurPack;

import static org.junit.Assert.*;

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
	      
	    }

	@Test
	public void testProcessUSER() {
		
		
	}

	@Test
	public void testProcessPASS() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessSYST() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessPORT() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessRETR() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessSTOR() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessLIST() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessQUIT() {
		fail("Not yet implemented");
	}

	
	@After 
	public void finalize() throws IOException{

		socket.close();
		dout.close();
		din.close();
	}
}
