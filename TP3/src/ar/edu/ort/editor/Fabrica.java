package ar.edu.ort.editor;

import java.util.Properties;

import ar.edu.ort.archivos.ArchivoTexto;

/**
 * Fabrica singleteada que devuelve objetos de tipo ArchivoEditable
 *
 */

public class Fabrica
{
	private static Fabrica instance;
	private Properties props;
	
	private Fabrica()
	{
		props=new Properties();
		props.put("txt",ArchivoTexto.class.getCanonicalName());
	}
	
	public static Fabrica instace()
	{
		if(instance==null) instance = new Fabrica();
		return instance;
	}
	
	public ArchivoEditable cargarEditor(String extension)
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
			ex.printStackTrace();
		}	
		return null;
	}
	
	public final Properties getProperties()
	{
		return props;
	}
}