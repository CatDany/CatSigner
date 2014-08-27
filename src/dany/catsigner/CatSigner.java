package dany.catsigner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

public class CatSigner
{
	private final byte[] privateKey;
	
	public CatSigner(String privateKey)
	{
		this.privateKey = DatatypeConverter.parseHexBinary(privateKey);
	}
	
	public CatSigner(byte[] privateKey)
	{
		this.privateKey = privateKey;
	}
	
	public byte[] getSignedBytes(File file) throws Throwable
	{
		KeyFactory keys = KeyFactory.getInstance("DSA", "SUN");
		PrivateKey priv = keys.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initSign(priv);
		
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = bin.read(buffer)) >= 0)
		{
			dsa.update(buffer, 0, len);;
		}
		bin.close();
		return dsa.sign();
	}
	
	public String getSignedHex(File file) throws Throwable
	{
		byte[] signedBytes = getSignedBytes(file);
		return DatatypeConverter.printHexBinary(signedBytes);
	}
}