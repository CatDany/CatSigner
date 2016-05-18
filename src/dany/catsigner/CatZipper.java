package dany.catsigner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.xml.bind.DatatypeConverter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class CatZipper
{
	private final File zipFile;
	private File file;
	private File tmpSignature;
	private File tmpFilename;
	
	public CatZipper(File zipFile)
	{
		this.zipFile = zipFile;
	}
	
	public CatZipper setFileToZip(File file) throws IOException
	{
		this.file = file;
		tmpFilename = new File(System.getenv("APPDATA") + "\\CatSigner\\FILENAME");
		Files.deleteIfExists(tmpFilename.toPath());
		tmpFilename.createNewFile();
		PrintWriter fnw = new PrintWriter(tmpFilename);
		fnw.append(file.getName());
		fnw.close();
		return this;
	}
	
	public CatZipper writeSignature(byte[] signature) throws IOException
	{
		tmpSignature = new File(System.getenv("APPDATA") + "\\CatSigner\\SIGNATURE");
		Files.deleteIfExists(tmpSignature.toPath());
		tmpSignature.createNewFile();
		FileOutputStream out = new FileOutputStream(tmpSignature);
		out.write(signature);
		out.close();
		return this;
	}
	
	public CatZipper writeSignature(String signature) throws IOException
	{
		writeSignature(DatatypeConverter.parseHexBinary(signature));
		return this;
	}
	
	public void zip() throws ZipException, IOException
	{
		ZipFile zip = new ZipFile(zipFile);
		ZipParameters params = new ZipParameters();
		params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		zip.addFile(file, params);
		zip.addFile(tmpSignature, params);
		zip.addFile(tmpFilename, params);
		tmpSignature.delete();
		tmpFilename.delete();
	}
}