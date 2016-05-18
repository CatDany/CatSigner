package dany.catsigner.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dany.catsigner.Lang;
import dany.catsigner.Main;
import dany.catsigner.listeners.ReadOnlyListener;
import dany.catsigner.listeners.SelectOnFocusListener;

public class PanelVerify extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public final JLabel labelTitle;
	public final JLabel labelPublicKey;
	public final JLabel labelFile;
	public final JTextField fieldPublicKey;
	public final JTextField fieldFile;
	public final JButton buttonFile;
	public final JButton buttonHelpPublic;
	public final JButton buttonHelpFile;
	public final JButton buttonVerify;
	
	public PanelVerify()
	{
		super();
		setLayout(null);
		
		int wid = Main.frame.getWidth();
		int hei = Main.frame.getHeight();
		
		this.labelTitle = new JLabel(Lang.BUTTON_VERIFY_FILES);
		labelTitle.setFont(Main.fontTitle);
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		labelTitle.setBounds(0, 0, Main.frame.getWidth(), 40);
		add(labelTitle);
		
		this.labelPublicKey = new JLabel(Lang.LABEL_PUBLIC_KEY);
		labelPublicKey.setBounds(wid / 2 - 120, 78, 240, 20);
		labelPublicKey.setHorizontalAlignment(JLabel.CENTER);
		labelPublicKey.setFont(Main.fontDefault);
		add(labelPublicKey);
		
		this.fieldPublicKey = new JTextField();
		fieldPublicKey.setBounds(wid / 2 - 120, 98, 240, 22);
		add(fieldPublicKey);
		
		this.labelFile = new JLabel(Lang.LABEL_FILE_TO_VERIFY);
		labelFile.setFont(Main.fontDefault);
		labelFile.setHorizontalAlignment(JLabel.CENTER);
		labelFile.setBounds(wid / 2 - 120, 120, 240, 20);
		add(labelFile);
		
		this.fieldFile = new JTextField();
		fieldFile.setBounds(wid / 2 - 120, 140, 240, 22);
		fieldFile.addKeyListener(ReadOnlyListener.instance);
		fieldFile.addFocusListener(SelectOnFocusListener.instance);
		add(fieldFile);
		
		this.buttonVerify = new JButton(Lang.BUTTON_VERIFY_GO);
		buttonVerify.setBounds(wid / 2 - 100, 168, 200, 24);
		buttonVerify.setActionCommand("buttonVerify");
		buttonVerify.addActionListener(Main.instance);
		add(buttonVerify);
		
		this.buttonHelpPublic = new JButton("?");
		buttonHelpPublic.setBounds(wid / 2 - 120, 78, 50, 20);
		buttonHelpPublic.setActionCommand("buttonHelpPublic");
		buttonHelpPublic.addActionListener(Main.instance);
		add(buttonHelpPublic);
		
		this.buttonHelpFile = new JButton("?");
		buttonHelpFile.setBounds(wid / 2 - 120, 120, 50, 20);
		buttonHelpFile.setActionCommand("buttonHelpFileToVerify");
		buttonHelpFile.addActionListener(Main.instance);
		add(buttonHelpFile);
		
		this.buttonFile = new JButton("...");
		buttonFile.setBounds(wid / 2 + 70, 120, 50, 20);
		buttonFile.setActionCommand("buttonFileToVerify");
		buttonFile.addActionListener(Main.instance);
		add(buttonFile);
	}
}