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
    //private final String FILENAME = null;
   // Connect c = new Connect();
   
	String user_private_key="19013950108260553971635461842583727430316370061086869112063896340978533643976782906658381828970585792080159122328369886882934704530038925260207824362778526977762111667503445086285338892408546974847888987769397224654803335504871330966799033952074058694004628879087234558393255429197426375685653289497014119748665323594562619147623040113752254704091684357275138492093695738401388261936296970635292922674052284636983018278415784155560263354273422573760640817255163773962630879510805719039816294903911415990284236597840709414838757360114653445576359719249217196430187210796468879758430872017021121890825951155255530511237";
	 
	static String N="10570327118835298640449612774548292215644301232314142865661606709963209764841510286668984074134587521859845971377653319354709021651593747127274442014812141";
	
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
	    	 String initial_packet=Hash.PacketGeneration(s);
	    	 String onetimekeyofmain=Hash.keygeneration(initial_packet);
	    	 String assemblemessage=Hash.AssemblingMessage(initial_packet,onetimekeyofmain);
	    	// System.out.println("the value of s is "+s);
	    	 
	    	 pwrite.println(assemblemessage+"&"+onetimekeyofmain);
	    	 
	    	 s=receiveRead.readLine();
	    	 String[]str_split=s.split("&");
				String assemble=str_split[0];
				String onetimekey=str_split[1];
				String dissemblemessage=Hash.Packet_dissAssemble(assemble, onetimekey);
				String original_message=Hash.convertBinaryStringToString(dissemblemessage);
				
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