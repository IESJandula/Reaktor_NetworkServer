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
	 * Metodo que recibe el contenido del comando ipconfig y lo parsea a un mapa de <String,List<String>>
	 * @param content contenido del comando
	 * @return mapa con la informacion de cada adaptador
	 * @throws NetworkException
	 */
	public Map<String,List<String>> parseIpConfig(String content) throws NetworkException
	{
		//Declaracion del mapa
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		//Cargado de datos del comando
		//Separamos el contenido por \n
		String [] splitContenido = content.split("\n");
		
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
	
}
