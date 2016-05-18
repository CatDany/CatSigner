package dany.catsigner;

import java.util.List;

import dany.catsigner.libs.Helper;

public class Lang
{
	public static String BUTTON_GENERATE_KEYS;
	public static String BUTTON_SIGN_FILES;
	public static String BUTTON_VERIFY_FILES;
	public static String BUTTON_BIND_KEY_GO;
	public static String BUTTON_GENERATE_GO;	
	public static String BUTTON_SIGN_GO;
	public static String BUTTON_VERIFY_GO;
	public static String LABEL_PRIVATE_KEY;
	public static String LABEL_PUBLIC_KEY;
	public static String LABEL_FILE_TO_SIGN;
	public static String LABEL_FILE_TO_VERIFY;
	public static String LABEL_SAVE_TO;
	public static String LABEL_SIGNATURE;
	public static String INFO_PRIVATE_KEY;
	public static String INFO_PUBLIC_KEY;
	public static String INFO_FILE_TO_SIGN;
	public static String INFO_FILE_TO_VERIFY;
	public static String INFO_SIGNATURE;
	public static String INFO_FILE_VERIFIED_YES;
	public static String INFO_FILE_VERIFIED_NO;
	public static String INFO_UNKNOWN_SIGNER;
	public static String INFO_UNABLE_TO_SAVE_PRIVATE_KEY;
	public static String INFO_SAVED_PRIVATE_KEY;
	public static String INFO_ERROR;
	public static String INFO_SAVE_TO;
	public static String INFO_PUBLIC_KEY_INVALID;
	public static String INFO_BIND_KEY_YES_NO;
	public static String INFO_BIND_KEY_ENTER_PUBLIC_KEY;
	public static String INFO_BIND_KEY_ENTER_YOUR_NAME;
	public static String INFO_BIND_KEY_SUCCESS;
	public static String INFO_BIND_KEY_WRONG_OWNER;
	public static String INFO_BIND_KEY_WRONG_PUBLIC_KEY;
	public static String INFO_BIND_KEY_KEY_ALREADY_EXISTS;
	public static String INFO_BIND_KEY_UNKNOWN_ERROR;
	
	public static void init(String locale) throws Throwable
	{
		System.out.println("Started initializing Localization!");
		List<String> list = Helper.readLines("lang/lang_" + locale + ".txt");
		for (String i : list)
		{
			String key = i.split("=", 2)[0];
			String value = i.split("=", 2)[1];
			
			Lang.class.getField(key).set(null, value.replace("  ", "\n"));
			System.out.println("Localization: " + key + "=" + value);
		}
		System.out.println("Localization initialized!");
	}
}