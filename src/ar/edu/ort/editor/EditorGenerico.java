package ar.edu.ort.editor;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorGenerico extends JFrame implements Observer
{
	private static final long serialVersionUID = 1L;
	private static final int anchoButton = 60;
	private static final int altoButton = 35;

	public EditorGenerico()
	{
		super("Editor");
		crearControles();
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub	
	}
	
	public void crearControles()
	{
		crearVentana();
		crearPanel();
	}
	
	public void crearVentana()
	{
		setBounds(0, 0, 800, 600);
		getContentPane().setLayout(new BorderLayout());
	}
	
	public void crearPanel()
	{
		Button btn;
		JPanel panel = new JPanel();
		getContentPane().add(panel,BorderLayout.NORTH);
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(0,40));
		
		btn = new Button("Abrir");
		panel.add(btn);
		btn.setName("Abrir");
		btn.setPreferredSize(new Dimension(anchoButton,altoButton));
		
		btn = new Button("Guardar");
		panel.add(btn);
		btn.setName("Guardar");
		btn.setPreferredSize(new Dimension(anchoButton,altoButton));
		
		btn = new Button("Cerrar");
		panel.add(btn);
		btn.setName("Cerrar");
		btn.setPreferredSize(new Dimension(anchoButton,altoButton));
	 }
}