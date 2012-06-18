package ar.edu.ort.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    private JPanel panel = new JPanel();;
    protected String filename;

    public ArchivoEditable()
    {
    	panel.setPreferredSize(new Dimension(0, 520));
    	panel.setBackground(Color.WHITE);
    	panel.setLayout(new BorderLayout());
    }
    
    public boolean isModified()
    {
    	return modified;
    }
    
    protected void setModified(boolean value)
    {
    	modified=value;
		setChanged();
		notifyObservers();
    }
    
    public JPanel getPanel()
    {
    	return panel;
    }

    public abstract void open(String filename) throws FileNotFoundException, IOException;
    
    public abstract void save() throws FileNotFoundException, IOException;
    
    public abstract void close() throws IOException;
}