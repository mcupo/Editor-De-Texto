package ar.edu.ort.archivos;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import ar.edu.ort.editor.ArchivoEditable;

public class ArchivoTexto extends ArchivoEditable
{
	private TextArea control;
	private File f;
	private FileOutputStream outFile;

	public ArchivoTexto()
	{
		control = new TextArea();
		control.addTextListener(new TextListener()
		{
			@Override
			public void textValueChanged(TextEvent arg0)
			{
				//El archivo fue modificado
				setModified(true);
			}
		});
		getPanel().add(control, BorderLayout.CENTER);
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
				 br.close();
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
		setModified(false);
	}

	@Override
	public void close() throws IOException
	{
		if (outFile!=null)
			outFile.close();
		control.setText("");
	}
		
	public TextArea getControl()
	{
		return control;
	}
}
