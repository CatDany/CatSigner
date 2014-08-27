package dany.catsigner.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class SelectOnFocusListener implements FocusListener
{
	public static final SelectOnFocusListener instance = new SelectOnFocusListener();
	
	@Override
	public void focusGained(FocusEvent e)
	{
		if (e.getComponent() instanceof JTextField)
		{
			JTextField field = (JTextField)e.getComponent();
			field.select(0, field.getText().length());
		}
	}
	
	@Override
	public void focusLost(FocusEvent e) {}
}
