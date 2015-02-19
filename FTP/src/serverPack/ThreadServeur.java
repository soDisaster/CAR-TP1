package serverPack;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;


public class ThreadServeur extends Thread {

	Socket ClientSoc;
	DataInputStream din;
	DataOutputStream dout;
	Map<String,String> users;


	public ThreadServeur(Socket soc,Map<String,String> users)
	{
		this.users=users;

		ClientSoc=soc;                        
		try {

			din=new DataInputStream(ClientSoc.getInputStream());
			dout=new DataOutputStream(ClientSoc.getOutputStream());

		} catch (IOException e) {
			System.out.println("Erreur DataStream constructeur ThreadServeur : "+e);
		}

		System.out.println("FTP Client Connected ...");



	}

	public String keyWord(String ligne){


		String[] tab;
		tab=ligne.split(" ");
		return(tab[0]);




	}

	/* Retourne argument de la commande si il existe*/
	public String arg(String ligne){

		String[] tab;
		tab=ligne.split(" ");
		if(tab.length>1){

			return(tab[1]);
		}
		return "";


	}

	@SuppressWarnings("deprecation")
	public void run(){	
		try {
			dout.write(new String("220\n").getBytes());
		} catch (IOException e) {
			System.out.println("Erreur write run ThreadServeur : "+e);
		}

		Class<FtpRequest> c = FtpRequest.class; 
		try {

			Constructor<FtpRequest> ct = c.getConstructor(Map.class);

			Object t = ct.newInstance(this.users);
			String command ="";
			while(command != null)
			{

				System.out.println("Waiting for Command ...");


				try {
					command = din.readLine();

					if(command !=null){
						System.out.println(command);

						String methodeName = "process"+keyWord(command);
						System.out.println(methodeName);
						Method m = c.getMethod(methodeName,Socket.class, String.class);

						if(m != null){
							String arg = this.arg(command);
							System.out.println(arg.length());
							m.invoke(t,ClientSoc,arg);
						}
					}
				} catch (IOException e) {

					System.out.println("Erreur DataStream read in ThreadServeur : "+e);
				}

			}
		} catch (InstantiationException e) {

			System.out.println("Erreur instantiation de FtpRequest dans ThreadServeur : "+e);

		} catch (IllegalAccessException e) {

			System.out.println("Erreur invocation de méthode, Illegal Access dans ThreadServeur : "+e);

		} catch (SecurityException e) {

			System.out.println("Erreur sécurité readLine deprecatred  dans ThreadServeur : "+e);

		} catch (NoSuchMethodException e) {

			System.out.println("Erreur invocation de méthode dans ThreadServeur : "+e);

		} catch (IllegalArgumentException e) {

			System.out.println("Erreur invocation de méthode, illegal arguement dans ThreadServeur : "+e);

		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}


