package ar.edu.ort.editor;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EditorGenerico extends JFrame implements Observer
{
	private static final long serialVersionUID = 1L;
	private static final int anchoButton = 60;
	private static final int altoButton = 35;
	private static final String ERROR_DE_ACCESO = "Error al acceder al archivo: ";
	private static final String ERROR_DE_ESCRITURA = "Error al escribir el archivo: ";
	private static final String ERROR_DE_EXTENSION_NO_VALIDA = "Extensión no válida";
	private Button bAbrir;
	private Button bCerrar;
	private Button bGuardar;
	private ArchivoEditable archivo;
	private JFileChooser fChooser = new JFileChooser();

	public EditorGenerico()
	{
		super("Editor");
		crearControles();
	}
	
	@Override
	public void update(Observable who, Object what)
	{
		ArchivoEditable archivo = (ArchivoEditable) who;
		if(archivo.isModified())
		{
			bGuardar.setEnabled(true);
		}
		else
		{
			bGuardar.setEnabled(false);
		}
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
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				try
				{
					//Si no hay archivo salgo
					if(archivo==null) System.exit(0);
					//Si el archivo fue modificado pregunto si desea guardar los cambios
					if(archivo.isModified())
					{
						int n = JOptionPane.showConfirmDialog(EditorGenerico.this, "¿Desea guardar los cambios?","Confirme la salida", JOptionPane.YES_NO_CANCEL_OPTION);
						if(n==JOptionPane.YES_OPTION)
						{
							archivo.save();
							archivo.close();
							getContentPane().remove(archivo.getPanel());
							System.exit(0);
						}
						else if(n==JOptionPane.NO_OPTION)
						{
							archivo.close();
							//Remuevo el panel
							getContentPane().remove(archivo.getPanel());
							System.exit(0);
						}
						//Si cancela no hago nada
						else
						{
							setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}
					}
					else
					{
						archivo.close();
						//Remuevo el panel
						getContentPane().remove(archivo.getPanel());
						System.exit(0);
					}
				}
				catch(FileNotFoundException ex)
				{
					JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ACCESO + ex.getMessage());
				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ESCRITURA + ex.getMessage());
				}
			}
		});
	}
	
	public void crearPanel()
	{
		//Solo arranca activo el boton abrir
		JPanel panel = new JPanel();
		getContentPane().add(panel,BorderLayout.NORTH);
		panel.setBackground(Color.GRAY);
		panel.setPreferredSize(new Dimension(0,40));
		
		bAbrir = new Button("Abrir");
		panel.add(bAbrir);
		bAbrir.setName("Abrir");
		bAbrir.setPreferredSize(new Dimension(anchoButton,altoButton));
		bAbrir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				//Verifico si hay un archivo
				if(archivo!=null)
				{
					try
					{
						//Si el archivo fue modificado pregunto si desea guardar los cambios
						if(archivo.isModified())
						{
							int n = JOptionPane.showConfirmDialog(EditorGenerico.this, "¿Desea guardar los cambios?","Confirme la salida", JOptionPane.YES_NO_CANCEL_OPTION);
							if(n==JOptionPane.YES_OPTION)
							{
								archivo.save();
								archivo.close();
								getContentPane().remove(archivo.getPanel());
							}
							else if(n==JOptionPane.NO_OPTION)
							{
								archivo.close();
								//Remuevo el panel
								getContentPane().remove(archivo.getPanel());
							}
							else
							{
								//Por cancelar no hago más nada
								return;
							}
						}
						else
						{
							archivo.close();
							//Remuevo el panel
							getContentPane().remove(archivo.getPanel());
						}
					}
					catch(FileNotFoundException ex)
					{
						getContentPane().remove(archivo.getPanel());
						JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ACCESO + ex.getMessage());
					}
					catch (IOException ex)
					{
						JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ESCRITURA + ex.getMessage());
					}
				}
				
				int returnVal = fChooser.showOpenDialog(EditorGenerico.this);
	            
	            if (returnVal == JFileChooser.APPROVE_OPTION)
	            {
	                File file = fChooser.getSelectedFile();
	                String extension=file.getName();
	                extension=(extension.substring(extension.lastIndexOf("."))).replace(".", "");
	                
	                try
	                {
						archivo=Fabrica.instace().cargarEditor(extension);
						//TODO validar extension
						archivo.addObserver(EditorGenerico.this);
	                	archivo.open(file.getAbsolutePath());
	                	getContentPane().add(archivo.getPanel(),BorderLayout.CENTER);
	                	SwingUtilities.updateComponentTreeUI(EditorGenerico.this);
	                	//Habilito el boton de cierre
	                	bCerrar.setEnabled(true);
					}
	                catch (FileNotFoundException e)
					{					
						JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ACCESO + e.getMessage());
					}
					catch (IOException e)
					{
						JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ESCRITURA + e.getMessage());
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(EditorGenerico.this, ERROR_DE_EXTENSION_NO_VALIDA);
					}
	            }
			}
		});
		
		bGuardar = new Button("Guardar");
		panel.add(bGuardar);
		bGuardar.setName("Guardar");
		bGuardar.setPreferredSize(new Dimension(anchoButton,altoButton));
		bGuardar.setEnabled(false);
		bGuardar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					archivo.save();
				}
				catch(FileNotFoundException e)
				{
					JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ACCESO + e.getMessage());
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ESCRITURA + e.getMessage());
				}
			}
		});
		
		bCerrar = new Button("Cerrar");
		panel.add(bCerrar);
		bCerrar.setName("Cerrar");
		bCerrar.setPreferredSize(new Dimension(anchoButton,altoButton));
		bCerrar.setEnabled(false);
		bCerrar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					//Si el archivo fue modificado pregunto si desea guardar los cambios
					if(archivo.isModified())
					{
						int n = JOptionPane.showConfirmDialog(EditorGenerico.this, "¿Desea guardar los cambios?","Confirme la salida", JOptionPane.YES_NO_CANCEL_OPTION);
						if(n==JOptionPane.YES_OPTION)
						{
							archivo.save();
							archivo.close();
							getContentPane().remove(archivo.getPanel());
						}
						else if(n==JOptionPane.NO_OPTION)
						{
							archivo.close();
							//Remuevo el panel
							getContentPane().remove(archivo.getPanel());
						}
					}
					else
					{
						archivo.close();
						//Remuevo el panel
						getContentPane().remove(archivo.getPanel());
					}
				}
				catch(FileNotFoundException ex)
				{
					JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ACCESO + ex.getMessage());
				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(EditorGenerico.this,ERROR_DE_ESCRITURA + ex.getMessage());
				}
			}
		});
	 }
}