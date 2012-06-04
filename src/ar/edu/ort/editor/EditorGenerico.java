package ar.edu.ort.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditorGenerico extends JFrame implements Observer
{
	private static final long serialVersionUID = 1L;

	public EditorGenerico()
	{
		super("Editor");
		setBounds(0, 0, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		getContentPane().add(panel,BorderLayout.NORTH);
		panel.setBackground(Color.BLUE);
		panel.setPreferredSize(new Dimension(0,40));
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub	
	}
}