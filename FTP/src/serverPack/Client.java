package serverPack;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

	private static int    _port;
	private static Socket _socket;

	public static void main(String[] args)
	{
		String message      = "ping";
		InputStream input   = null;

		try
		{
			_port   = (args.length == 1) ? Integer.parseInt(args[0]) : 9999;
			_socket = new Socket((String) null, _port);

			// Open stream
			input = _socket.getInputStream();

			// Show the server response
			String response = new BufferedReader(new InputStreamReader(input)).readLine();
			System.out.println("Server message: " + response);

			//OutputStream output = _socket.getOutputStream();
			//DataOutputStream d = new DataOutputStream(output);
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Veuillez saisir un mot :");
			String str = sc.nextLine();
			System.out.println("Vous avez saisi : " + str);
			
			//d.write(str.getBytes());
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				input.close();
				_socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


}
