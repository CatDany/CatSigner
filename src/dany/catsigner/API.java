package dany.catsigner;

import java.io.File;

public class API
{
	/**
	 * Generate DSA keys in HEX format
	 * @return String[]: privKey, pubKey
	 * @throws Throwable
	 */
	public static String[] generateKeys() throws Throwable
	{
		CatKeyGen keyGen = new CatKeyGen();
		String privKey = keyGen.hexPrivateKey();
		String pubKey = keyGen.hexPublicKey();
		return new String[] {privKey, pubKey};
	}
	
	/**
	 * Sign a file using SHA1withDSA
	 * @param file File to sign
	 * @param privKey Private Key in HEX format
	 * @return
	 * @throws Throwable
	 */
	public static String sign(File file, String privKey) throws Throwable
	{
		return new CatSigner(privKey).getSignedHex(file);
	}
	
	/**
	 * Verify a file using SHA1withDSA
	 * @param file File to verify
	 * @param pubKey Public Key in HEX format
	 * @param signature Signature in HEX format
	 * @return <code>true</code> if file verified, otherwise <code>false</code>
	 * @throws Throwable
	 */
	public static boolean verify(File file, String pubKey, String signature) throws Throwable
	{
		return new CatVerifier(pubKey).verify(file, signature);
	}
}