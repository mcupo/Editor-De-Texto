package ar.edu.ort.archivos;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.CharBuffer;

import javax.swing.JTextArea;

import ar.edu.ort.editor.ArchivoEditable;

public class ArchivoTexto extends ArchivoEditable
{
	private JTextArea control = new JTextArea();
	private File f;
	private FileOutputStream outFile;

	public ArchivoTexto()
	{
		control.setPreferredSize(new Dimension(780,510));
		//control.append("hola\nchau!");
		getPanel().add(control);
	}
	
	@Override
	public void open(String filename) throws FileNotFoundException, IOException
	{
		this.filename=filename;
		f = new File(filename);
		if (!f.exists())
		{
			System.out.println("Archivo no encontrado");
		}
		else
		{
			try
			{
				 BufferedReader br = new BufferedReader(new FileReader(f));
				 String linea;
				 
				 while ((linea = br.readLine()) != null)
					  control.append(linea+"\n");
				 //br.close();
			}
			catch (IOException e)
			{
				System.out.println("Error al acceder al archivo: " + e.getMessage());
			}
		}
	}

	@Override
	public void save() throws IOException
	{
	     try
	     {
	    	 outFile= new FileOutputStream(new File(filename));	            
	         outFile.write(control.getText().getBytes());
	     }
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
		control.getText();
	}

	@Override
	public void close() throws IOException
	{
		if (outFile!=null)
			outFile.close();
		control.setText("");
	}
		
	public JTextArea getControl()
	{
		return control;
	}
}
