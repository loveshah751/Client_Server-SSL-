package us.singhlovepreet.RSA;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA
{
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private static int bitlength ;
    private Random     r;

    public RSA(int bitlength)
    {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }
    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the Bit size like 512 o1r 1024 or 256");
        bitlength=scanner.nextInt();
        RSA rsa = new RSA(bitlength);
        // DataInputStream in = new DataInputStream(System.in);
        // String teststring;
        BigInteger publickey=rsa.e;
        BigInteger privateKey=rsa.d;
        BigInteger N=rsa.N;
       //System.out.println("Value of na is "+new BigInteger(Na));
        System.out.println("private Key ==> "+rsa.d);
        System.out.println("------------------------------------------------");
       System.out.println("Public Key ==> "+rsa.e);
       System.out.println("------------------------------------------------");
       System.out.println("N ==> "+rsa.e);
       System.out.println("------------------------------------------------");
        System.out.println("Enter the plain text:");
        // teststring = in.readLine();
        String teststring = scanner.next();
        //System.out.println("Big ");
       
        System.out.println("String in Bytes: "
                + bytesToString(teststring.getBytes()));
        // encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes(),publickey,N);
        System.out.println("Encrypting String: " + encrypted);
        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted,privateKey,N);
        //System.out.println(decrypted);
        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
        scanner.close();
    }

    private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }

    // Encrypt message
    public byte[] encrypt(byte[] message, BigInteger e, BigInteger N)
    {
//    	BigInteger e=new BigInteger(e1);
//    	BigInteger N=new BigInteger(N1);
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message,BigInteger d, BigInteger N)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
}

