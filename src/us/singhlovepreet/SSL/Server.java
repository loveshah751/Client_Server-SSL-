package us.singhlovepreet.SSL;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import us.singhlovepreet.HashFunction.Hash;
import us.singhlovepreet.RSA.RSA;

public class Server {
	static String user_publickey = "10570327118835298640449612774548292215644301232314142865661606709963209764841510286668984074134587521859845971377653319354709021651593747127274442014812141";

	static String N = "10570327118835298640449612774548292215644301232314142865661606709963209764841510286668984074134587521859845971377653319354709021651593747127274442014812141";
	//private int currentTot;
	static boolean validate = false;
	static ServerSocket serversocket;
	static String s_username = "Lovepreet";
	String s_company = "CalstateLA";
	static Socket sock;
	int bytesRead;
	// Connect c = new Connect();
	static BufferedReader input;
	static PrintWriter output;
	static int port = 2006;

	public void start() throws IOException {
		
		System.out.println("Server Started ");
		System.out.println("Connection Starting on port:" + port);
		
		// make connection to client on port specified
		serversocket = new ServerSocket(port);

		// accept connection from client
		sock = serversocket.accept();

		System.out.println("Waiting for connection from client");

		try {
			logInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logInfo() throws Exception {
		//RSA rsa = new RSA();

		// open buffered reader for reading data from client
		input = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		String username = input.readLine();

		//System.out.println("SERVER SIDE" + username);
		String company = input.readLine();
		//System.out.println("SERVER SIDE" + company);

		// open printwriter for writing data to client
		output = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));

		if (username.equals(s_username) && company.equals(s_company)) {
			validate = true;
			output.println("Welcome, " + username);
		}

		else {
			validate = false;
			output.println("Login Failed! user not found 404!");
			System.out.println(validate);
		}
		// if(validate=true)
		// {
		// serverchat();
		// }

		output.flush();
		// output.close();

	}

	public static void serverchat() throws IOException {
		System.out.println("Server ready for chatting");
		// reading from keyboard (keyRead object)

		input = new BufferedReader(new InputStreamReader(System.in));

		// BufferedReader keyRead = new BufferedReader(new
		// InputStreamReader(sock.getInputStream()));
		// sending to client (pwrite object)
		OutputStream ostream = sock.getOutputStream();
		output = new PrintWriter(ostream, true);
		// PrintWriter pwrite = new PrintWriter(ostream, true);

		// receiving from server ( receiveRead object)
		InputStream istream = sock.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		String s;
		String receiveMessage, sendMessage;
		while (true) {
			s = receiveRead.readLine();
			String[]str_split=s.split("&");
			String assemble=str_split[0];
			String onetimekey=str_split[1];
			String dissemblemessage=Hash.Packet_dissAssemble(assemble, onetimekey);
			String original_message=Hash.convertBinaryStringToString(dissemblemessage);
					
			System.out.println(s_username + ": " + original_message + "\n");
			
			System.out.println("Server: ");
			s = input.readLine();
			 String initial_packet=Hash.PacketGeneration(s);
	    	 String onetimekeyofmain=Hash.keygeneration(initial_packet);
	    	 String assemblemessage=Hash.AssemblingMessage(initial_packet,onetimekeyofmain);
	    	// System.out.println("the value of s is "+s)
			output.println(assemblemessage+"&"+onetimekeyofmain);
			output.flush();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.start();
			// System.out.println("Server is boolean value is "+validate);
			if (validate == true) {
				serverchat();
			} else {
				System.out.println("Server is closed due to unauthorised access!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
