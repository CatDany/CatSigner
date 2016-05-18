package dany.catsigner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class CatUnzipper
{
	private final File zipFile;
	private File file;
	private byte[] signature;
	private ArrayList<File> tmpFilesToDelete = new ArrayList<File>();
	
	public CatUnzipper(File zipFile)
	{
		this.zipFile = zipFile;
	}
	
	public CatUnzipper unzip() throws ZipException, IOException
	{
		ZipFile zip = new ZipFile(zipFile);
		File tmpFolder = new File(System.getenv("APPDATA") + "\\CatSigner");
		File tmpFilename = new File(tmpFolder, "FILENAME");
		File tmpSignature = new File(tmpFolder, "SIGNATURE");
		zip.extractFile("FILENAME", tmpFolder.getPath());
		zip.extractFile("SIGNATURE", tmpFolder.getPath());
		String fileName = Files.readFirstLine(tmpFilename, Charset.defaultCharset());
		File tmpFile = new File(tmpFolder, fileName);
		zip.extractFile(fileName, tmpFolder.getPath());
		file = new File(tmpFolder, fileName);
		signature = Files.toByteArray(tmpSignature);
		tmpFilesToDelete.add(tmpFilename);
		tmpFilesToDelete.add(tmpSignature);
		tmpFilesToDelete.add(tmpFile);
		return this;
	}
	
	public boolean verify(String pubKey) throws Throwable
	{
		boolean verified = API.verify(file, pubKey, DatatypeConverter.printHexBinary(signature));
		for (File i : tmpFilesToDelete)
		{
			i.delete();
		}
		return verified;
	}
	
	public boolean verify(byte[] pubKey) throws Throwable
	{
		return verify(DatatypeConverter.printHexBinary(pubKey));
	}
}