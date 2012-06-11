package ar.edu.ort.editor;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.ort.archivos.ArchivoTexto;

public class EditorGenerico extends JFrame implements Observer
{
	private static final long serialVersionUID = 1L;
	private static final int anchoButton = 60;
	private static final int altoButton = 35;
	private ArchivoEditable archivo = new ArchivoTexto();
	private JFileChooser fChooser = new JFileChooser();

	public EditorGenerico()
	{
		super("Editor");
		archivo.addObserver(this);
		crearControles();
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		/**
		 *  TODO
		 *  Necesito recibir una notificacion del archivo para saber que botones activar	
		 */
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
		panel.setBackground(Color.GRAY);
		panel.setPreferredSize(new Dimension(0,40));
		
		btn = new Button("Abrir");
		panel.add(btn);
		btn.setName("Abrir");
		btn.setPreferredSize(new Dimension(anchoButton,altoButton));
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
	            int returnVal = fChooser.showOpenDialog(archivo.getPanel());
	            
	            if (returnVal == JFileChooser.APPROVE_OPTION)
	            {
	                File file = fChooser.getSelectedFile();
	                try
	                {
						archivo.open(file.getAbsolutePath());
					}
	                catch (FileNotFoundException e)
					{					
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
	            }
			}
		});
		
		btn = new Button("Guardar");
		panel.add(btn);
		btn.setName("Guardar");
		btn.setPreferredSize(new Dimension(anchoButton,altoButton));
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					archivo.save();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btn = new Button("Cerrar");
		panel.add(btn);
		btn.setName("Cerrar");
		btn.setPreferredSize(new Dimension(anchoButton,altoButton));
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int n = JOptionPane.showConfirmDialog(EditorGenerico.this, "¿Desea guardar los cambios?","Confirme la salida", JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION) archivo.save();
					archivo.close();
					
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		getContentPane().add(archivo.getPanel(),BorderLayout.SOUTH);
	 }
}