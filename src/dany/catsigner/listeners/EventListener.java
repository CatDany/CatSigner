package dany.catsigner.listeners;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dany.catsigner.API;
import dany.catsigner.Lang;
import dany.catsigner.Main;
import dany.catsigner.libs.Helper;

public class EventListener
{
	public static void selectKeyGen(ActionEvent e)
	{
		Main.switchScreen(Main.panelKeyGen);
	}
	
	public static void selectSign(ActionEvent e)
	{
		Main.switchScreen(Main.panelSign);
	}
	
	public static void selectVerify(ActionEvent e)
	{
		Main.switchScreen(Main.panelVerify);
	}
	
	public static void buttonGenerate(ActionEvent e)
	{
		try
		{
			String[] keys = API.generateKeys();
			Main.panelKeyGen.fieldPrivateKey.setText(keys[0]);
			Main.panelKeyGen.fieldPublicKey.setText(keys[1]);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	public static void buttonSign(ActionEvent e)
	{
		try
		{
			String signature = API.sign(new File(Main.panelSign.fieldFile.getText()), Main.panelSign.fieldPrivateKey.getText());
			Main.panelSign.fieldSignature.setText(signature);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	public static void buttonVerify(ActionEvent e)
	{
		try
		{
			boolean verified = API.verify(new File(Main.panelVerify.fieldFile.getText()), Main.panelVerify.fieldPublicKey.getText(), Main.panelVerify.fieldSignature.getText());
			if (verified)
			{
				String pubkey = Main.panelVerify.fieldPublicKey.getText();
				String signer = Lang.INFO_UNKNOWN_SIGNER;
				String url = "http://cs.hoppix.ru/keys.txt";
				HashMap<String, String> map = Helper.getBoundKeys(url);
				if (map.containsKey(pubkey))
				{
					signer = map.get(pubkey);
				}
				JOptionPane.showMessageDialog(new JFrame(), String.format(Lang.INFO_FILE_VERIFIED_YES, signer), "", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_FILE_VERIFIED_NO, "", JOptionPane.WARNING_MESSAGE);
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	public static void buttonFileToSign(ActionEvent e)
	{
		JFileChooser cho = new JFileChooser();
		int result = cho.showDialog(new JFrame(), null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File file = cho.getSelectedFile();
			Main.panelSign.fieldFile.setText(file.getAbsolutePath());
		}
	}
	
	public static void buttonFileToVerify(ActionEvent e)
	{
		JFileChooser cho = new JFileChooser();
		int result = cho.showDialog(new JFrame(), null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File file = cho.getSelectedFile();
			Main.panelVerify.fieldFile.setText(file.getAbsolutePath());
		}
	}
	
	public static void buttonSavePrivateKey(ActionEvent e)
	{
		try
		{
			Helper.savePrivateKey(Main.panelSign.fieldPrivateKey.getText());
			JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_SAVED_PRIVATE_KEY, "", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Throwable t)
		{
			JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_UNABLE_TO_SAVE_PRIVATE_KEY, "", JOptionPane.ERROR_MESSAGE);
			t.printStackTrace();
		}
	}
	
	public static void buttonHelpPrivate(ActionEvent e)
	{
		JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_PRIVATE_KEY, Lang.LABEL_PRIVATE_KEY, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void buttonHelpPublic(ActionEvent e)
	{
		JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_PUBLIC_KEY, Lang.LABEL_PUBLIC_KEY, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void buttonHelpFileToSign(ActionEvent e)
	{
		JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_FILE_TO_SIGN, Lang.LABEL_FILE_TO_SIGN, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void buttonHelpFileToVerify(ActionEvent e)
	{
		JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_FILE_TO_VERIFY, Lang.LABEL_FILE_TO_VERIFY, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void buttonHelpSignature(ActionEvent e)
	{
		JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_SIGNATURE, Lang.LABEL_SIGNATURE, JOptionPane.INFORMATION_MESSAGE);
	}
}