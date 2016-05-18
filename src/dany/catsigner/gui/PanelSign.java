package dany.catsigner.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dany.catsigner.Lang;
import dany.catsigner.Main;
import dany.catsigner.libs.Helper;
import dany.catsigner.listeners.ReadOnlyListener;
import dany.catsigner.listeners.SelectOnFocusListener;

public class PanelSign extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public final JLabel labelTitle;
	public final JLabel labelPrivateKey;
	public final JLabel labelFile;
	public final JLabel labelSignature;
	public final JLabel labelSaveTo;
	public final JTextField fieldPrivateKey;
	public final JTextField fieldFile;
	public final JTextField fieldSignature;
	public final JTextField fieldSaveTo;
	public final JButton buttonHelpPrivate;
	public final JButton buttonSavePrivateKey;
	public final JButton buttonHelpFile;
	public final JButton buttonFile;
	public final JButton buttonSign;
	public final JButton buttonHelpSaveTo;
	public final JButton buttonFileSaveTo;
	
	public PanelSign()
	{
		super();
		setLayout(null);
		
		int wid = Main.frame.getWidth();
		int hei = Main.frame.getHeight();
		
		this.labelTitle = new JLabel(Lang.BUTTON_SIGN_FILES);
		labelTitle.setFont(Main.fontTitle);
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		labelTitle.setBounds(0, 0, Main.frame.getWidth(), 40);
		add(labelTitle);
		
		this.labelPrivateKey = new JLabel(Lang.LABEL_PRIVATE_KEY);
		labelPrivateKey.setBounds(wid / 2 - 120, 78, 240, 20);
		labelPrivateKey.setHorizontalAlignment(JLabel.CENTER);
		labelPrivateKey.setFont(Main.fontDefault);
		add(labelPrivateKey);
		
		this.fieldPrivateKey = new JTextField();
		fieldPrivateKey.setBounds(wid / 2 - 120, 98, 240, 22);
		fieldPrivateKey.setText(Helper.getSavedPrivateKey());
		add(fieldPrivateKey);
		
		this.labelFile = new JLabel(Lang.LABEL_FILE_TO_SIGN);
		labelFile.setFont(Main.fontDefault);
		labelFile.setHorizontalAlignment(JLabel.CENTER);
		labelFile.setBounds(wid / 2 - 120, 120, 240, 20);
		add(labelFile);
		
		this.fieldFile = new JTextField();
		fieldFile.setBounds(wid / 2 - 120, 142, 240, 22);
		fieldFile.addKeyListener(ReadOnlyListener.instance);
		fieldFile.addFocusListener(SelectOnFocusListener.instance);
		add(fieldFile);
		
		this.labelSaveTo = new JLabel(Lang.LABEL_SAVE_TO);
		labelSaveTo.setFont(Main.fontDefault);
		labelSaveTo.setHorizontalAlignment(JLabel.CENTER);
		labelSaveTo.setBounds(wid / 2 - 120, 162, 240, 20);
		add(labelSaveTo);
		
		this.fieldSaveTo = new JTextField();
		fieldSaveTo.setBounds(wid / 2 - 120, 184, 240, 22);
		fieldSaveTo.addKeyListener(ReadOnlyListener.instance);
		fieldSaveTo.addFocusListener(SelectOnFocusListener.instance);
		add(fieldSaveTo);
		
		this.buttonSign = new JButton(Lang.BUTTON_SIGN_GO);
		buttonSign.setBounds(wid / 2 - 100, 210, 200, 24);
		buttonSign.setActionCommand("buttonSign");
		buttonSign.addActionListener(Main.instance);
		add(buttonSign);
		
		this.labelSignature = new JLabel(Lang.LABEL_SIGNATURE);
		labelSignature.setFont(Main.fontDefault);
		labelSignature.setHorizontalAlignment(JLabel.CENTER);
		labelSignature.setBounds(wid / 2 - 120, 240, 240, 20);
		add(labelSignature);
		
		this.fieldSignature = new JTextField();
		fieldSignature.setBounds(wid / 2 - 120, 260, 240, 22);
		fieldSignature.addKeyListener(ReadOnlyListener.instance);
		fieldSignature.addFocusListener(SelectOnFocusListener.instance);
		add(fieldSignature);
		
		this.buttonHelpPrivate = new JButton("?");
		buttonHelpPrivate.setBounds(wid / 2 - 120, 78, 50, 20);
		buttonHelpPrivate.setActionCommand("buttonHelpPrivate");
		buttonHelpPrivate.addActionListener(Main.instance);
		add(buttonHelpPrivate);
		
		this.buttonSavePrivateKey = new JButton(Main.iconSave);
		buttonSavePrivateKey.setBounds(wid / 2 + 70, 78, 50, 20);
		buttonSavePrivateKey.setActionCommand("buttonSavePrivateKey");
		buttonSavePrivateKey.addActionListener(Main.instance);
		add(buttonSavePrivateKey);
		
		this.buttonHelpFile = new JButton("?");
		buttonHelpFile.setBounds(wid / 2 - 120, 120, 50, 20);
		buttonHelpFile.setActionCommand("buttonHelpFileToSign");
		buttonHelpFile.addActionListener(Main.instance);
		add(buttonHelpFile);
		
		this.buttonFile = new JButton("...");
		buttonFile.setBounds(wid / 2 + 70, 120, 50, 20);
		buttonFile.setActionCommand("buttonFileToSign");
		buttonFile.addActionListener(Main.instance);
		add(buttonFile);
		
		this.buttonHelpSaveTo = new JButton("?");
		buttonHelpSaveTo.setBounds(wid / 2 - 120, 164, 50, 20);
		buttonHelpSaveTo.setActionCommand("buttonHelpSaveTo");
		buttonHelpSaveTo.addActionListener(Main.instance);
		add(buttonHelpSaveTo);
		
		this.buttonFileSaveTo = new JButton("...");
		buttonFileSaveTo.setBounds(wid / 2 + 70, 164, 50, 20);
		buttonFileSaveTo.setActionCommand("buttonFileSaveTo");
		buttonFileSaveTo.addActionListener(Main.instance);
		add(buttonFileSaveTo);
	}
}
