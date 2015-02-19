package serveurPack;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import serverPack.Serveur;


public class ServeurTest {

	@Test
	public void testInitUser() throws IOException {
		Serveur serv= new Serveur();
		serv.initUser();
		serv.getUser();
		assertTrue("LISTE non chargée",serv.getUser()!=null);
	}

}
