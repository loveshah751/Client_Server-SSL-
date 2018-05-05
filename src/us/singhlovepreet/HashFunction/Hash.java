package us.singhlovepreet.HashFunction;

import java.util.Random;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

public class Hash
{
	static Scanner sc=new Scanner(System.in);
	
	//Method to convert Input String into binary 
	public static String[] stringTobinary(String str)
	{
		if(str.length()>0)
		{
			byte[] bytes=str.getBytes();
			String[] final_str=new String[bytes.length];
			for(int i=0;i<bytes.length;i++)
			{
				final_str[i]=Integer.toBinaryString(0x100 + bytes[i]).substring(1);
			}
			return final_str;
		}
		return null;
	}
	
	// To convert Binary to Decimal
	public static int BinaryToDecimal(String binarynumber)
	{
		int decimal=0;
		int length=binarynumber.length();
		if(length>0)
		{
			String substring=binarynumber.substring(1);
			int digit= Character.getNumericValue(binarynumber.charAt(0));
			decimal= digit *(int) Math.pow(2, length-1) + BinaryToDecimal(substring);
		}
		return decimal;
	}
	
	
	//Method To convert Decimal to Binary
	public static String DecimaltoBinary(int decimal)
	{
		int result;
		if(decimal > 0 || decimal < 0)
		{
			result=decimal % 2;
			return (DecimaltoBinary(decimal/2)+""+result);
		}
		return "";
	}
	
	// To make Binary string of length 8
	public static String Binary_Length_8(String binary)
	{
		String helper="";
		if(8-binary.length()>0)
		{
			int f_length=8-binary.length();
			for(int i=0; i<f_length;i++)
			{
				helper+="0";
			}
			binary=helper.concat(binary);
			return binary;
		}
		return "";
	}
	
	// Addition of Checksum result
		public static String addBinary(String a, String b) 
		{
		    if(a==null || a.length()==0)
		        return b;
		    if(b==null || b.length()==0)
		        return a;
		 
		    int pa = a.length()-1;
		    int pb = b.length()-1;
		 
		    int flag = 0;
		    StringBuilder sb = new StringBuilder();
		    while(pa >= 0 || pb >=0){
		        int va = 0;
		        int vb = 0;
		 
		        if(pa >= 0){
		            va = a.charAt(pa)=='0'? 0 : 1;    
		            pa--;
		        }
		        if(pb >= 0){
		            vb = b.charAt(pb)=='0'? 0: 1;
		            pb--;
		        }
		 
		        int sum = va + vb + flag;
		        if(sum >= 2){
		            sb.append(String.valueOf(sum-2));
		            flag = 1;
		        }else{
		            flag = 0;
		            sb.append(String.valueOf(sum));
		        }
		    }
		 
		    if(flag == 1){
		        sb.append("1");
		    }
		 
		    String reversed = sb.reverse().toString();
		    return reversed;
		}
		
	
	//Calculating the Checksum of Packet and Pattern
	
	
	public static int Packet_checksum(String[] data_bytes,int Pattern)
	{
		String temp="00000000";
		String cal_checksum;
		int checksum_res;
		int value;
		for(int i=0;i<data_bytes.length;i++)
		{
			// Performing Bitwise And Operators on data_bytes[i] and Pattern
			value=BinaryToDecimal(data_bytes[i])&Pattern;
			cal_checksum=DecimaltoBinary(value);
			temp=addBinary(temp, cal_checksum);
		}
		
		checksum_res=BinaryToDecimal(temp);
		return checksum_res;
		
	}
	
		// generating one time key
	  public static byte[] newKey(int paramInt)
	  {
	    return newKey(new Random(), paramInt);
	  }

	  public static byte[] newKey(Random paramRandom, int paramInt)
	  {
	    byte[] arrayOfByte = new byte[paramInt];
	    paramRandom.nextBytes(arrayOfByte);
	    return arrayOfByte;
	  }
	  
	  // Delete the element from  the array
	  
	  public static String[] Delete_Array(String str)
	  {
		  String[] temp=str.split(" ");
		  String[] help_array=new String[temp.length-1];
		  for(int i=0;i<temp.length-1;i++)
		  {
			  help_array[i]=temp[i+1];
			  
		  }
		  return help_array;
	  }
	  
	  public static String Packet_dissAssemble(String assemble_packet,String onetimekey)
	  {
		  	String[] key=Delete_Array(onetimekey);
			String[] pack=Delete_Array(assemble_packet);
			String dissemble_packet="";
		  for(int i=0;i<pack.length;i++)
			{
			
				int onetime=BinaryToDecimal(key[i]);
				int d_pack1=BinaryToDecimal(pack[i]);
				int xor_res=d_pack1^onetime;
				//System.out.println("XOR "+xor_res);
				String temp=DecimaltoBinary(xor_res);
				if(temp.length()!=8)
				{
					temp=Binary_Length_8(temp);
				}
				
				dissemble_packet +=" ";
				dissemble_packet+=temp;
			}
			
			return dissemble_packet;
		  
	 }
	  
	  //  Converting binary String(0001112233) into String (Hello World!)
	  public static String convertBinaryStringToString(String dissemablePacket)
	  {
		  
		  String[] origin=Delete_Array(dissemablePacket);
		  String string="";
		  for(int i=0;i<origin.length;i++)
		  {
			  string+=origin[i];
			  string+=" ";
		  }
		  
		    StringBuilder sb = new StringBuilder();
		    char[] chars = string.toCharArray();

		    //for each character
		    for (int j = 0; j < chars.length; j+=9) {
		        int idx = 0;
		        int sum = 0;
		        //for each bit in reverse
		        for (int i = 7; i>= 0; i--) {
		            if (chars[i+j] == '1') {
		                sum += 1 << idx;
		            }
		            idx++;
		        }
		        sb.append(Character.toChars(sum));
		    }
		    return sb.toString();
		}
	  
	  //key generation
	  
	  public static String keygeneration(String packet)
	  {
		  
			int one_time_length= (packet.length()+1)/9;
			byte[] one_time_key_array= newKey(one_time_length);
	
			String one_time_key="";
			for(int i=0;i<one_time_key_array.length;i++)
			{
				if(one_time_key_array[i]<0)
				{
					String temp=DecimaltoBinary(one_time_key_array[i]);
					String[] tem=temp.split("-");
					temp=tem[1];
					if(temp.length()!=8)
					{
						temp=Binary_Length_8(temp);
					}
					one_time_key+=" ";
					one_time_key+=temp;
				}
				
				else
				{
					String temp=DecimaltoBinary(one_time_key_array[i]);
					if(temp.length()!=8)
					{
						temp=Binary_Length_8(temp);
					}
					one_time_key+=" ";
					one_time_key+=temp;
				}
				
			}
		  
		  return one_time_key;
	  }
	  
	  
	  // initial Packet generation
	  	
	  public static String PacketGeneration(String data)
	  {
		  String[] data_byte=stringTobinary(data);
			int ndatabytes=data_byte.length;
			if(ndatabytes>255)
			{
				throw new RuntimeErrorException(null, "Maximum size of Message Exceeded!");
			}
			String n=DecimaltoBinary(data_byte.length);
				if(n.length()!=8)
				{
					n=Binary_Length_8(n);
				}
			String Packet=n;
			
			//packet Array for passing as argument to Checksum method to calculate bitwise And operation 
			// between Data_bytes and Pattern and in this Array I neglect the value of n which is not need for the operation.
			String[] packet= new String[ndatabytes];
			
			for(int i=0;i<data_byte.length;i++)
			{
				Packet+=" ";
				
				Packet+=data_byte[i];
				
				packet[i]=data_byte[i];	
			}
			
			int pattern_min=1;
			int pattern_max=128;
			Random random=new Random();
			byte[] a = new byte[4];
			int Pattern= pattern_min+random.nextInt(pattern_max);
			int first_value=Packet_checksum(packet, Pattern);
				
			int odd_min=2;
			int odd_max=128;
			
				int odd=odd_min+random.nextInt(odd_max);
				if(odd%2==0)
				{
					odd+=1;
				}
				int multiply_res=first_value*odd;
				//System.out.println("Enter the value for ncheckbytes");
				int checkbytes_min=1;
				int checkbytes_max=9;
				int ncheckbytes=checkbytes_min+random.nextInt(checkbytes_max);
				
				//System.out.println("My checkbytes is "+ncheckbytes);
				double modules=Math.pow(2, 8*ncheckbytes);
				int final_result=(int) (multiply_res%modules);
				String hash_value=DecimaltoBinary(final_result);
				if(hash_value.length()!=8)
				{
					hash_value=Binary_Length_8(hash_value);
				}
				//System.out.println("Hash Value is "+hash_value);
				Packet= Packet.concat(" ").concat(hash_value);
				return Packet;
	  }
	  
	  
	  // message generation
	  public static String AssemblingMessage(String message, String key)
	  {
				String one_time_key=key;
				
				
				String[] onetimekey=Delete_Array(one_time_key);
				String final_packet="";
				String[] packetarray=message.split(" ");
				for(int i=0;i<packetarray.length;i++)
				{
				
					int onetime=BinaryToDecimal(onetimekey[i]);
					int pack=BinaryToDecimal(packetarray[i]);
					int xor_res=pack^onetime;
					String temp=DecimaltoBinary(xor_res);
					if(temp.length()!=8)
					{
						temp=Binary_Length_8(temp);
					}
					
					final_packet+=" ";
					final_packet+=temp;
				}
				
				return final_packet;
				
	  }
	  
	
	 // Main method
	public static void main(String args[])
	{
		
		System.out.println("Welcome to Data Transfer.");
		System.out.println("Please Enter the String you want to send!");
		String data=sc.nextLine();
		if(data.length()==0)
		{
			throw new RuntimeException("Please Enter the Data before you send");
		}
		
		String initial_packet=PacketGeneration(data);
		System.out.println("Initial Packet of main "+initial_packet);
		String onetimekeyofmain=keygeneration(initial_packet);
		
		String assemblemessage=AssemblingMessage(initial_packet,onetimekeyofmain);
		System.out.println("Assembled Message is "+assemblemessage);
		
		System.out.println("One time key"+onetimekeyofmain);
		
		String dissemblemessage=Packet_dissAssemble(assemblemessage, onetimekeyofmain);
		
		System.out.println("Disassembled Message is "+dissemblemessage);
		
		String original_message=convertBinaryStringToString(dissemblemessage);
		
		System.out.println("Original message is "+original_message);
		
	}
	
	

}
