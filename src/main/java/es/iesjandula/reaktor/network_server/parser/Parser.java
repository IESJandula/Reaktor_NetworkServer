package es.iesjandula.reaktor.network_server.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.reaktor.network_server.exception.NetworkException;

public class Parser 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	/**
	 * Pendiente implementacion del codigo
	 * @author Pablo Ruiz Canovas
	 */
	public Map<String,List<String>> parseIpConfig() throws NetworkException
	{
		//Declaracion del mapa
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		//Cargado de datos del comando
		String contenido = this.contentCommand();
		//Separamos el contenido por \n
		String [] splitContenido = contenido.split("\n");
		
		//Creacion de la lista de datos de cada adaptador 
		List<String> datosConf = new LinkedList<String>();
		//String identificador para identificar el adaptador
		String keySentencia = "";
		for(String sentencia:splitContenido)
		{
			/*
			 * Cada seccion del adaptador acaba siempre en : por ejemplo
			 * Adaptador de Ethernet Ethernet:
			 * puede haber casos en los que se produzca esto
			 * Sufijo DNS específico para la conexión. . :
			 * entonces se controla que no contenga el caracter "."
			 * y se guarda como clave en el mapa
			 * en caso contrario se controla que la key no sea nula para evitar las primeras lineas
			 * y se rellena la lista con la informacion del adaptador
			*/
			if(sentencia.trim().endsWith(":") && !sentencia.trim().contains("."))
			{
				keySentencia = sentencia;
				datosConf = new LinkedList<String>();
				map.put(keySentencia.trim(), datosConf);
			}
			else if(!keySentencia.isEmpty())
			{
				datosConf.add(sentencia.trim());
				map.put(keySentencia, datosConf);
			}
		}
		log.info("Parseo de informacion del comando ipconfig finalizado");
		return map;
		
	}
	
	/**
	 * Metodo que ejecuta el comando ipconfig y guarda todo el resultado de la ejecucion
	 * @return resultado de la ejecucion en formato string
	 * @throws NetworkException
	 */
	private String contentCommand() throws NetworkException
	{
		//Declaracion de variables
		String comando = "ipconfig";
		String contenido = "";
		BufferedReader reader = null;
		try
		{
			//Constructor del proceso en el que se llama a la cmd y se ejecuta el comando
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",comando);
			
			//Creacion del proceso
			Process proceso = builder.start();
			
			//Ejecucion del proceso
			reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
			
			//Lecutra y cargado de datos del proceso
			String linea = reader.readLine();
			while(linea!=null)
			{
				//Nos interesa guardar las lineas que contengan datos por lo que
				//Se omiten las vacias
				if(!linea.isEmpty())
				{
					contenido+=linea+"\n";
				}
				linea = reader.readLine();
			}
			
		}
		catch(IOException ex)
		{
			log.error("Error leyendo el resultado del comando",ex);
			throw new NetworkException(500,"Error al leer el resultado del comando ipconfig",ex);
		}
		finally
		{
			try
			{
				if(reader!=null)
				{
					reader.close();
				}	
			}
			catch(IOException ex)
			{
				log.error("Error cerrando el flujo de entrada",ex);
			}
		}
		return contenido;
	}
}
