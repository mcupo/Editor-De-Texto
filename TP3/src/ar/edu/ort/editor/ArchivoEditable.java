package ar.edu.ort.editor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JPanel;

/**
 * Clase genérica con los metodos de un archivo editable
 * Es observada por
 */

public abstract class ArchivoEditable extends Observable
{
    private boolean modified;
    private JPanel panel;
    protected String filename;

    public boolean isModified()
    {
    	return true;
    }
    
    protected void setModified(boolean value)
    {
    	
    }
    
    public JPanel getPanel()
    {
    	return panel;
    }

    public void open(String filename) throws FileNotFoundException, IOException
    {
    	
    }
    
    public void save() throws IOException
    {
    	
    }
    
    public void close()
    {
    	
    }
}