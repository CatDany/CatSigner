package dany.catsigner.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dany.catsigner.Lang;
import dany.catsigner.Main;
import dany.catsigner.listeners.ReadOnlyListener;
import dany.catsigner.listeners.SelectOnFocusListener;

public class PanelKeyGen extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public final JLabel labelTitle;
	public final JLabel labelPrivateKey;
	public final JLabel labelPublicKey;
	public final JTextField fieldPrivateKey;
	public final JTextField fieldPublicKey;
	public final JButton buttonGenerate;
	public final JButton buttonHelpPrivate;
	public final JButton buttonHelpPublic;
	
	public PanelKeyGen()
	{
		super();
		setLayout(null);
		
		int wid = Main.frame.getWidth();
		int hei = Main.frame.getHeight();
		
		this.labelTitle = new JLabel(Lang.BUTTON_GENERATE_KEYS);
		labelTitle.setFont(Main.fontTitle);
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		labelTitle.setBounds(0, 0, wid, 40);
		add(labelTitle);
		
		this.labelPrivateKey = new JLabel(Lang.LABEL_PRIVATE_KEY);
		labelPrivateKey.setFont(Main.fontDefault);
		labelPrivateKey.setHorizontalAlignment(JLabel.CENTER);
		labelPrivateKey.setBounds(wid / 2 - 120, 120, 240, 20);
		add(labelPrivateKey);
		
		this.fieldPrivateKey = new JTextField();
		fieldPrivateKey.setBounds(wid / 2 - 120, 140, 240, 22);
		fieldPrivateKey.addKeyListener(ReadOnlyListener.instance);
		fieldPrivateKey.addFocusListener(SelectOnFocusListener.instance);
		add(fieldPrivateKey);
		
		this.labelPublicKey = new JLabel(Lang.LABEL_PUBLIC_KEY);
		labelPublicKey.setFont(Main.fontDefault);
		labelPublicKey.setHorizontalAlignment(JLabel.CENTER);
		labelPublicKey.setBounds(wid / 2 - 120, 162, 240, 20);
		add(labelPublicKey);
		
		this.fieldPublicKey = new JTextField();
		fieldPublicKey.setBounds(wid / 2 - 120, 182, 240, 22);
		fieldPublicKey.addKeyListener(ReadOnlyListener.instance);
		fieldPublicKey.addFocusListener(SelectOnFocusListener.instance);
		add(fieldPublicKey);
		
		this.buttonGenerate = new JButton(Lang.BUTTON_GENERATE_GO);
		buttonGenerate.setBounds(wid / 2 - 100, 210, 200, 24);
		buttonGenerate.setActionCommand("buttonGenerate");
		buttonGenerate.addActionListener(Main.instance);
		add(buttonGenerate);
		
		this.buttonHelpPrivate = new JButton("?");
		buttonHelpPrivate.setBounds(wid / 2 - 120, 120, 50, 20);
		buttonHelpPrivate.setActionCommand("buttonHelpPrivate");
		buttonHelpPrivate.addActionListener(Main.instance);
		add(buttonHelpPrivate);
		
		this.buttonHelpPublic = new JButton("?");
		buttonHelpPublic.setBounds(wid / 2 - 120, 162, 50, 20);
		buttonHelpPublic.setActionCommand("buttonHelpPublic");
		buttonHelpPublic.addActionListener(Main.instance);
		add(buttonHelpPublic);
	}
}