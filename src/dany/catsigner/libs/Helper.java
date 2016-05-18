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
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import dany.catsigner.Lang;

public class Helper
{
	public static String getDatabaseResponse(String owner, String pubKey)
	{
		// php script was written by a person who doesn't know English very well
		// kay = key, huh
		String response = "";
		String url = "http://cs.hoppix.ru/add.php?user=%s&go=YepItsTotallyFine&kay=%s";
		try
		{
			Scanner scan = new Scanner(new URL(String.format(url, owner, pubKey)).openStream());
			while (scan.hasNext())
			{
				response += scan.next() + " ";
			}
			scan.close();
			System.out.println("[DEBUG] Database Response: " + response);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		response = response.replace("<br/>", "");
		return response;
	}
	
	public static String getOwner(String pubKey)
	{
		String def = Lang.INFO_UNKNOWN_SIGNER;
		if (!pubKey.matches("^[0-9A-F]{16,}$"))
		{
			return def;
		}
		
		String response = getDatabaseResponse("", pubKey).substring(11);
		return response;
	}
	
	/**
	 * 
	 * @param owner
	 * @param pubKey
	 * @return
	 * 0 - Success<br>
	 * 1 - Wrong owner<br>
	 * 2 - Wrong Public Key<br>
	 * 3 - Key Already Exists<br>
	 * -1 - Unknown error
	 */
	public static int addOwnerToDatabase(String owner, String pubKey)
	{
		if (!owner.matches("^[0-9A-Za-z]{3,}$"))
		{
			return 1;
		}
		else if (!isPublicKeyValid(pubKey))
		{
			return 2;
		}
		String response = getDatabaseResponse(owner, pubKey);
		if (response.contains("KEY_ALREADY_EXISTS"))
		{
			return 3;
		}
		else if (response.contains("ADD_SUCCESS"))
		{
			return 0;
		}
		else
		{
			return -1;
		}
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
	
	public static boolean isPublicKeyValid(String pubKey)
	{
		try
		{
			KeyFactory keys = KeyFactory.getInstance("DSA", "SUN");
			keys.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseHexBinary(pubKey)));
			return true;
		}
		catch (Throwable t)
		{
			return false;
		}
	}
}