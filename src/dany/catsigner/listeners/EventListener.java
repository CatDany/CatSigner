package dany.catsigner.listeners;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

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
	
	public static void buttonBindKey(ActionEvent e)
	{
		int input = JOptionPane.showConfirmDialog(new JFrame(), Lang.INFO_BIND_KEY_YES_NO, Lang.BUTTON_BIND_KEY_GO, JOptionPane.YES_NO_OPTION);
		if (input == JOptionPane.YES_OPTION)
		{
			String pubKey =	JOptionPane.showInputDialog(Lang.INFO_BIND_KEY_ENTER_PUBLIC_KEY);
			String nick   = JOptionPane.showInputDialog(Lang.INFO_BIND_KEY_ENTER_YOUR_NAME);
			if (pubKey != null && !pubKey.isEmpty() && nick != null && !nick.isEmpty())
			{
				int result = Helper.addOwnerToDatabase(nick, pubKey);
				String msg = null;
				int msgType = JOptionPane.ERROR_MESSAGE;
				switch (result)
				{
				case 0:
					msg = Lang.INFO_BIND_KEY_SUCCESS;
					msgType = JOptionPane.INFORMATION_MESSAGE;
					break;
				case 1:
					msg = Lang.INFO_BIND_KEY_WRONG_OWNER;
					msgType = JOptionPane.WARNING_MESSAGE;
					break;
				case 2:
					msg = Lang.INFO_BIND_KEY_WRONG_PUBLIC_KEY;
					msgType = JOptionPane.WARNING_MESSAGE;
					break;
				case 3:
					msg = Lang.INFO_BIND_KEY_KEY_ALREADY_EXISTS;
					msgType = JOptionPane.WARNING_MESSAGE;
					break;
				case -1:
					msg = Lang.INFO_BIND_KEY_UNKNOWN_ERROR;
					msgType = JOptionPane.ERROR_MESSAGE;
					break;
				}
				JOptionPane.showMessageDialog(new JFrame(), msg, Lang.BUTTON_BIND_KEY_GO, msgType);
			}
		}
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
			API.signAndZip(new File(Main.panelSign.fieldSaveTo.getText()), new File(Main.panelSign.fieldFile.getText()), Main.panelSign.fieldPrivateKey.getText());
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
			String pubKey = Main.panelVerify.fieldPublicKey.getText();
			if (!pubKey.matches("^[0-9A-F]{16,}$"))
			{
				JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_PUBLIC_KEY_INVALID, "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			boolean verified = API.verifyZippedFile(new File(Main.panelVerify.fieldFile.getText()), pubKey);
			if (verified)
			{
				String pubkey = Main.panelVerify.fieldPublicKey.getText();
				String signer = Helper.getOwner(pubkey);
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
		int result = cho.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File file = cho.getSelectedFile();
			Main.panelSign.fieldFile.setText(file.getAbsolutePath());
		}
	}
	
	public static void buttonFileToVerify(ActionEvent e)
	{
		JFileChooser cho = new JFileChooser();
		int result = cho.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File file = cho.getSelectedFile();
			Main.panelVerify.fieldFile.setText(file.getAbsolutePath());
		}
	}
	
	public static void buttonFileSaveTo(ActionEvent e)
	{
		FileFilter zipFilter = new FileFilter()
		{
			@Override
			public String getDescription()
			{
				return "ZIP (*.zip)";
			}
			
			@Override
			public boolean accept(File f)
			{
				return f.getPath().endsWith(".zip");
			}
		};
		JFileChooser cho = new JFileChooser();
		cho.setFileFilter(zipFilter);
		int result = cho.showSaveDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File file = cho.getSelectedFile();
			if (!zipFilter.accept(file))
			{
				file = new File(file.getPath() + ".zip");
			}
			Main.panelSign.fieldSaveTo.setText(file.getAbsolutePath());
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
	
	public static void buttonHelpSaveTo(ActionEvent e)
	{
		JOptionPane.showMessageDialog(new JFrame(), Lang.INFO_SAVE_TO, Lang.LABEL_SAVE_TO, JOptionPane.INFORMATION_MESSAGE);
	}
}