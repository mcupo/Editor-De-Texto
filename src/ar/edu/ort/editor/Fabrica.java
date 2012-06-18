package ar.edu.ort.editor;

import java.io.IOException;
import java.util.Properties;

import ar.edu.ort.archivos.ArchivoTexto;

/**
 * Fabrica singleteada que devuelve objetos de tipo {@link ArchivoEditable}
 *
 */

public class Fabrica
{
	private static Fabrica instance;
	private Properties props;
	
	private Fabrica()
	{
		props=new Properties();
		try
		{
			props.load(getClass().getResourceAsStream("/ar/edu/ort/archivos/editor.properties"));
		}
		catch(IOException ex){}
		props.put("txt",ArchivoTexto.class.getCanonicalName());
	}
	
	public static Fabrica instace()
	{
		if(instance==null) instance = new Fabrica();
		return instance;
	}
	
	public ArchivoEditable cargarEditor(String extension) throws Exception
	{
		//Obtengo el nombre de la clase buscandola en las properties
		String nombreClase=props.getProperty(extension);
		
		try
		{
			Class<?> clase;
			//Verifico que exista la clase
			clase=Class.forName(nombreClase);
			//Si existe creo una instancia de la misma
			Object objeto = clase.newInstance();
			return(ArchivoEditable) objeto;
		}
		catch(Exception ex)
		{
			//Retorno la excepcion para que sea tratada por el editor genérico
			throw ex;
		}	
	}
}