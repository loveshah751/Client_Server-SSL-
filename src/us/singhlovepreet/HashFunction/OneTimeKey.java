package us.singhlovepreet.HashFunction;

import java.util.Random;

public class OneTimeKey 
{
	
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
	

}
