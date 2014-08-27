package dany.catsigner.libs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class Helper
{
	/**
	 * Return a map (pubkey, signer) with all known signers and their public keys
	 * @param url
	 * @return
	 */
	public static HashMap<String, String> getBoundKeys(String url)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scan = new Scanner(new URL(url).openStream());
			while (scan.hasNext())
			{
				String line = scan.next();
				String pubkey = line.split("=", 2)[1];
				String signer = line.split("=", 2)[0];
				map.put(pubkey, signer);
			}
			scan.close();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		return map;
	}
	
	public static void createAppdataFolder()
	{
		File appdata = new File(System.getenv("APPDATA") + "\\CatSigner");
		if (!appdata.exists() || !appdata.isDirectory())
		{
			appdata.mkdirs();
			System.out.println("AppData folder is created successfully!");
		}
		else
		{
			System.out.println("Skipping creation of AppData folder. Already exists.");
		}
	}
	
	public static void savePrivateKey(String key) throws Throwable
	{
		File file = new File(System.getenv("APPDATA") + "\\CatSigner\\SavedPrivateKey.data");
		if (file.exists())
		{
			file.delete();
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		out.write(DatatypeConverter.parseHexBinary(key));
		out.close();
		System.out.println("Saved private key");
	}
	
	public static String getSavedPrivateKey()
	{
		File file = new File(System.getenv("APPDATA") + "\\CatSigner\\SavedPrivateKey.data");
		if (file.exists())
		{
			try
			{
				byte[] privKeyBytes = Files.readAllBytes(file.toPath());
				System.out.println("Read saved private key");
				return DatatypeConverter.printHexBinary(privKeyBytes);
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				System.err.println("Unable to read saved private key");
				return "";
			}
		}
		else
		{
			System.out.println("No saved private key found");
			return "";
		}
	}
	
	/**
	 * 
	 * @param jarFile Not actually ".jar" file, but a file within a jar file
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static List<String> readLines(String jarFile) throws IOException
	{
		ArrayList<String> list = new ArrayList<String>();
		InputStream jarFileIn = ClassLoader.getSystemResourceAsStream(jarFile);
		BufferedReader buf = new BufferedReader(new InputStreamReader(jarFileIn));
		String line;
		while ((line = buf.readLine()) != null)
		{
			list.add(line);
		}
		buf.close();
		return list;
	}
}