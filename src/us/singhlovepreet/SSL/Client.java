package us.singhlovepreet.SSL;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import us.singhlovepreet.HashFunction.Hash;
import us.singhlovepreet.RSA.RSA;
public class Client
{
	Scanner sc=new Scanner(System.in);
	static Socket socket;
	  static BufferedReader read;
	static  boolean validate=false;

    public void startClient(String HOSTNAME, int port ) throws UnknownHostException, IOException
    {
        //Create socket connection
    	socket = new Socket(HOSTNAME, port);

        //create printwriter for sending login to server
    	PrintWriter    output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        //prompt for user name
        System.out.println("Enter Your Username!");
        String username = sc.next();

        //send user name to server
        output.println(username);

        //prompt for Company Name
        System.out.println("Enter Your Company Name");
        String company = sc.next();


        //send Company to server
        output.println(company);
        output.flush();

        //create Buffered reader for reading response from server
        BufferedReader  read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read response from server
        String response = read.readLine();
       // System.out.println("This is the response: " + response);

      //  System.out.println(response);

        if(response.contains(username))
        {
        	validate=true;
        	System.out.println(response);

        }

        else
        {
        	System.out.println(response);
        	validate=false;
        }

        if(validate==true)
        {
        	chatting();
        }

    }

    public void chatting() throws IOException
    {
    	System.out.println("Client is ready to Chat");
	     read = new BufferedReader(new InputStreamReader(System.in));
	                              // sending to client (pwrite object)
	     OutputStream ostream = socket.getOutputStream();
	     PrintWriter pwrite = new PrintWriter(ostream, true);

	                              // receiving from server ( receiveRead  object)
	     InputStream istream = socket.getInputStream();
	     BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

	     String s;
	     Hash myhash=new Hash();
	     //  String receiveMessage, sendMessage;
	     while(true)
	     {
	    	 System.out.println("Client: ");
	    	 s=read.readLine();
				 // Encrypting the plain message with HASH function from HASH class
	    	 String initial_packet=Hash.PacketGeneration(s);
	    	 String onetimekeyofmain=Hash.keygeneration(initial_packet);
	    	 String asseemblemessage=Hash.AssemblingMessage(initial_packet,onetimekeyofmain);
				 //sending the Encrypted Message over the Socket for security reason.
				 pwrite.println(assemblemessage+"&"+onetimekeyofmain);

				 // reading Message recieved from Server but it is Encrypted
	    	 s=receiveRead.readLine();
				 // Decrypting the message
	    	 String[]str_split=s.split("&");
				String assemble=str_split[0];
				String onetimekey=str_split[1];
				String dissemblemessage=Hash.Packet_dissAssemble(assemble, onetimekey);
				String original_message=Hash.convertBinaryStringToString(dissemblemessage);
				//original_message from Server
	    	 System.out.println("Server: "+original_message+"\n");
				 pwrite.flush();
	      }
    }


    public static void main(String args[]){
        Client client = new Client();
        try {

        	 	Socket socket;
        	    int port = 2006;
        	    String HOSTNAME = "localhost";
        	    client.startClient(HOSTNAME,port);
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
