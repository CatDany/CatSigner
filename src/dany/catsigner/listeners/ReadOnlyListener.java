package dany.catsigner.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ReadOnlyListener implements KeyListener
{
	public static final ReadOnlyListener instance = new ReadOnlyListener();
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (!(e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()))
		{
			e.consume();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		e.consume();
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		e.consume();
	}
}