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
	
	/**
	 * Zip signed file and its signature
	 * @see <code>API.sign(File, String)</code>
	 * @param zipFile Path to a zip file. It shouldn't be already created!
	 * @param file File to sign
	 * @param privKey Private Key in HEX format
	 * @throws Throwable
	 */
	public static void signAndZip(File zipFile, File file, String privKey) throws Throwable
	{
		String signature = sign(file, privKey);
		new CatZipper(zipFile)
			.setFileToZip(file)
			.writeSignature(signature)
			.zip();
	}
	
	/**
	 * Verify a zipped file
	 * @see <code>API.verify(File, String, String)</code>
	 * @param zipFile Zip-file to verify
	 * @param pubKey Public Key in HEX format
	 * @return <code>true</code> if file verified, otherwise <code>false</code>
	 * @throws Throwable
	 */
	public static boolean verifyZippedFile(File zipFile, String pubKey) throws Throwable
	{
		return new CatUnzipper(zipFile)
				.unzip()
				.verify(pubKey);
	}
}