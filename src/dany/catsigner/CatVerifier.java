package dany.catsigner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

public class CatVerifier
{
	private final byte[] publicKey;
	
	public CatVerifier(String publicKey)
	{
		this.publicKey = DatatypeConverter.parseHexBinary(publicKey);
	}
	
	public CatVerifier(byte[] publicKey)
	{
		this.publicKey = publicKey;
	}
	
	public boolean verify(File file, byte[] signedBytes) throws Throwable
	{
		KeyFactory keys = KeyFactory.getInstance("DSA", "SUN");
		PublicKey pub = keys.generatePublic(new X509EncodedKeySpec(publicKey));
		
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initVerify(pub);
		
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in);
		byte[] buffer = new byte[1024];
		int len;
		while (bin.available() != 0)
		{
			len = bin.read(buffer);
			dsa.update(buffer, 0, len);
		}
		bin.close();
		
		return dsa.verify(signedBytes);
	}
	
	public boolean verify(File file, String signedHex) throws Throwable
	{
		byte[] signedBytes = DatatypeConverter.parseHexBinary(signedHex);
		return verify(file, signedBytes);
	}
}