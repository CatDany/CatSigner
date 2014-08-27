package dany.catsigner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

public class CatKeyGen
{
	public PrivateKey privKey;
	public PublicKey pubKey;
	
	public CatKeyGen() throws Throwable
	{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(1024, random);
		
		KeyPair pair = keyGen.generateKeyPair();
		this.privKey = pair.getPrivate();
		this.pubKey = pair.getPublic();
	}
	
	public String hexPrivateKey() throws Throwable
	{
		return DatatypeConverter.printHexBinary(privKey.getEncoded());
	}
	
	public String hexPublicKey() throws Throwable
	{
		return DatatypeConverter.printHexBinary(pubKey.getEncoded());
	}
	
	public static byte[] getBytes(String hex)
	{
		return DatatypeConverter.parseHexBinary(hex);
	}
}