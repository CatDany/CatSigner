package dany.catsigner;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dany.catsigner.gui.PanelKeyGen;
import dany.catsigner.gui.PanelSign;
import dany.catsigner.gui.PanelVerify;
import dany.catsigner.libs.Helper;
import dany.catsigner.listeners.EventListener;

public class Main implements ActionListener
{
	// FIXME Version!
	public static final String VERSION = "Beta 1.0 Build 2 (%s)";
	
	public static final Main instance = new Main();
	
	public static final ImageIcon iconSave = new ImageIcon(ClassLoader.getSystemResource("resources/icon_save.png"));
	
	public static JFrame frame;
	public static JButton selectKeyGen;
	public static JButton selectSign;
	public static JButton selectVerify;
	public static PanelKeyGen panelKeyGen;
	public static PanelSign panelSign;
	public static PanelVerify panelVerify;
	public static JLabel labelVersion;
	
	public static Font fontTitle = new Font("Georgia", Font.PLAIN, 30);
	public static Font fontDefault = new Font("Arial", Font.PLAIN, 18);
	
	public static void main(String[] args)
	{
		try
		{
			Lang.init(args[0]);
			start(args);
		}
		catch (Throwable t)
		{
			int yesorno = JOptionPane.showConfirmDialog(new JFrame(), String.format(Lang.INFO_ERROR, t.toString()), "", JOptionPane.ERROR_MESSAGE);
			t.printStackTrace();
			if (yesorno == JOptionPane.YES_OPTION)
			{
				System.exit(-1);
			}
		}
	}
	
	public static void start(String[] args) throws Throwable
	{
		Helper.createAppdataFolder();
		
		frame = new JFrame();
		frame.setTitle("CatSigner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setSize(450 + 6, 450 + 38);
		frame.setResizable(false);
		
		labelVersion = new JLabel(String.format(VERSION, args[0]));
		labelVersion.setBounds(0, frame.getHeight() - 50, frame.getWidth(), 20);
		labelVersion.setFont(fontDefault);
		frame.add(labelVersion);
		
		selectKeyGen = new JButton(Lang.BUTTON_GENERATE_KEYS);
		selectKeyGen.setBounds(0, 0, 150, 50);
		selectKeyGen.setActionCommand("selectKeyGen");
		selectKeyGen.addActionListener(instance);
		frame.add(selectKeyGen);
		
		selectSign = new JButton(Lang.BUTTON_SIGN_FILES);
		selectSign.setBounds(150, 0, 150, 50);
		selectSign.setActionCommand("selectSign");
		selectSign.addActionListener(instance);
		frame.add(selectSign);
		
		selectVerify = new JButton(Lang.BUTTON_VERIFY_FILES);
		selectVerify.setBounds(300, 0, 150, 50);
		selectVerify.setActionCommand("selectVerify");
		selectVerify.addActionListener(instance);
		frame.add(selectVerify);
		
		panelKeyGen = new PanelKeyGen();
		panelKeyGen.setBounds(0, 50, 450, 400);
		panelKeyGen.setVisible(false);
		frame.add(panelKeyGen);
		
		panelSign = new PanelSign();
		panelSign.setBounds(0, 50, 450, 400);
		panelSign.setVisible(false);
		frame.add(panelSign);
		
		panelVerify = new PanelVerify();
		panelVerify.setBounds(0, 50, 450, 400);
		panelVerify.setVisible(false);
		frame.add(panelVerify);
		
		frame.setVisible(true);
		switchScreen(panelKeyGen);
	}
	
	public static void switchScreen(JPanel panel)
	{
		if (panel instanceof PanelKeyGen)
		{
			selectKeyGen.setEnabled(false);
			selectSign.setEnabled(true);
			selectVerify.setEnabled(true);
			
			panelKeyGen.setVisible(true);
			panelSign.setVisible(false);
			panelVerify.setVisible(false);
		}
		else if (panel instanceof PanelSign)
		{
			selectKeyGen.setEnabled(true);
			selectSign.setEnabled(false);
			selectVerify.setEnabled(true);
			
			panelKeyGen.setVisible(false);
			panelSign.setVisible(true);
			panelVerify.setVisible(false);
		}
		else if (panel instanceof PanelVerify)
		{
			selectKeyGen.setEnabled(true);
			selectSign.setEnabled(true);
			selectVerify.setEnabled(false);
			
			panelKeyGen.setVisible(false);
			panelSign.setVisible(false);
			panelVerify.setVisible(true);
		}
		frame.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Method m = EventListener.class.getMethod(e.getActionCommand(), ActionEvent.class);
			m.invoke(null, e);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}